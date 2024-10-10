package com.alexiae.banking.facede;

import com.alexiae.banking.config.security.JwtService;
import com.alexiae.banking.exception.UserDuplicateException;
import com.alexiae.banking.model.api.ContactRequest;
import com.alexiae.banking.model.api.ContactValidateRequest;
import com.alexiae.banking.model.api.CreateRequest;
import com.alexiae.banking.model.api.ExtraInfoRequest;
import com.alexiae.banking.model.api.IdentityRequest;
import com.alexiae.banking.model.api.OnboardingResponse;
import com.alexiae.banking.model.api.PersonalRequest;
import com.alexiae.banking.model.dto.UserDto;
import com.alexiae.banking.model.entity.Contact;
import com.alexiae.banking.model.entity.Customer;
import com.alexiae.banking.model.entity.Document;
import com.alexiae.banking.model.entity.ExtraInfo;
import com.alexiae.banking.model.entity.Files;
import com.alexiae.banking.model.entity.User;
import com.alexiae.banking.model.enums.CustomerStatus;
import com.alexiae.banking.model.enums.FileType;
import com.alexiae.banking.model.enums.OnboardingStatus;
import com.alexiae.banking.model.enums.Role;
import com.alexiae.banking.service.ContactService;
import com.alexiae.banking.service.CustomerService;
import com.alexiae.banking.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OnboardingFacade {

  @Autowired
  private UserService userService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private ContactService contactService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  public void register(CreateRequest request) {
    UserDto userDto = userService.findByUsername(request.getEmail());
    if (Objects.nonNull(userDto)) {
      throw new UserDuplicateException("Usuario ya existe!");
    }

    User user = userService.create(toUser(request));
    Customer customer = new Customer();
    customer.setStatus(CustomerStatus.PENDING);
    customer.setUser(user);
    customer.setNextState(OnboardingStatus.CONTACT);
    customerService.create(customer);
  }

  private User toUser(CreateRequest request) {
    User user = new User();
    user.setEmail(request.getEmail());
    user.setAuthorities(Set.of(Role.ROLE_USER));
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    return user;
  }

  public OnboardingResponse contact(ContactRequest request) {
    Customer customer = customerService.findByUsername(request.getEmail());
    customer.setContact(toContact(request, customer));
    customer.setNextState(OnboardingStatus.CONTACT_VALIDATE);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }

  private Contact toContact(ContactRequest request, Customer customer) {
    Contact contact =
        Objects.nonNull(customer.getContact()) ? customer.getContact() : new Contact();
    contact.setCountryCode(request.getCountryCode());
    contact.setCallingCode(request.getCallingCode());
    contact.setPhoneNumber(request.getPhoneNumber());
    return contact;
  }

  @SneakyThrows
  public OnboardingResponse contactValidate(ContactValidateRequest request) {
    Customer customer = customerService.findByUsername(request.getEmail());
    if (Objects.isNull(customer.getContact())) {
      throw new Exception("Incomplete contact data");
    }
    if (validatePhoneNumber(customer.getContact(), request.getCode())) {
      customer.getContact().setPhoneValidated(Boolean.TRUE);
    }
    customer.setNextState(OnboardingStatus.PERSONAL_INFO);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }

  private boolean validatePhoneNumber(Contact contact, String code) {
    System.out.println("Validate contact");
    System.out.println("calling code: " + contact.getCallingCode());
    System.out.println("phone: " + contact.getPhoneNumber());
    System.out.println("code: " + code);
    return true;
  }

  public OnboardingResponse personalInfo(PersonalRequest request) {
    Customer customer = customerService.findByUsername(request.getEmail());
    customer.setFirstName(request.getFirstName());
    customer.setLastName(request.getLastName());
    customer.setBirthdate(request.getBirthdate());
    customer.setNextState(OnboardingStatus.IDENTITY_INFO);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }

  public OnboardingResponse identityInfo(IdentityRequest request) {
    Customer customer = customerService.findByUsername(request.getEmail());
    customer.setDocument(toDocument(request, customer));
    customer.setNextState(OnboardingStatus.UPLOAD_DOCUMENT);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }

  private Document toDocument(IdentityRequest request, Customer customer) {
    Document document =
        Objects.nonNull(customer.getDocument()) ? customer.getDocument() : new Document();
    document.setCountry(request.getDocumentCountry());
    document.setType(request.getDocumentType());
    document.setNumber(request.getDocumentNumber());
    return document;
  }

  public OnboardingResponse uploadDocument(String email, MultipartFile document) {
    Customer customer = customerService.findByUsername(email);
    String urlUpload = generateUrlFromDocument(document);
    customer.setFiles(toFiles(urlUpload, customer));
    customer.setNextState(OnboardingStatus.EXTRA_INFO);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }

  private List<Files> toFiles(String urlUpload, Customer customer) {
    List<Files> files =
        Objects.nonNull(customer.getFiles()) ? customer.getFiles() : new ArrayList<>();
    Files entity = new Files();
    entity.setType(FileType.IDENTIFICATION);
    entity.setImagePath(urlUpload);
    entity.setCustomer(customer);
    files.add(entity);
    return files;
  }

  private String generateUrlFromDocument(MultipartFile document) {
    UUID uuid = UUID.randomUUID();
    String uuidText = uuid.toString();
    return "https:cloud-file/directory-/" + uuidText;
  }

  public OnboardingResponse extraInfo(ExtraInfoRequest request) {
    Customer customer = customerService.findByUsername(request.getEmail());
    customer.setExtraInfo(toExtraInfo(request, customer));
    customer.setNextState(OnboardingStatus.PROCESSING);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }

  private ExtraInfo toExtraInfo(ExtraInfoRequest request, Customer customer) {
    ExtraInfo extraInfo =
        Objects.nonNull(customer.getExtraInfo()) ? customer.getExtraInfo() : new ExtraInfo();
    extraInfo.setPoliticallyExposed(request.isPoliticallyExposed());
    return extraInfo;
  }

  public OnboardingResponse processingInfo(String email) {
    Customer customer = customerService.findByUsername(email);
    customer.setNextState(OnboardingStatus.HOME);
    customer.setStatus(CustomerStatus.APPROVED);
    customerService.update(customer);
    return OnboardingResponse.builder().nextStep(customer.getNextState().name()).build();
  }
}
