package com.alexiae.banking.facede;

import com.alexiae.banking.model.api.CustomerInfoResponse;
import com.alexiae.banking.model.api.ExtraInfoDto;
import com.alexiae.banking.model.api.FileDto;
import com.alexiae.banking.model.dto.ContactDto;
import com.alexiae.banking.model.dto.DocumentDto;
import com.alexiae.banking.model.entity.Contact;
import com.alexiae.banking.model.entity.Customer;
import com.alexiae.banking.model.entity.Document;
import com.alexiae.banking.model.entity.ExtraInfo;
import com.alexiae.banking.model.entity.Files;
import com.alexiae.banking.model.enums.FileType;
import com.alexiae.banking.service.CustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerFacade {

  @Autowired
  private CustomerService customerService;

  public CustomerInfoResponse customerInfo(String email) {
    Customer customer = customerService.findByUsername(email);

    return CustomerInfoResponse.builder().id(customer.getId()).firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .birthdate(customer.getBirthdate())
        .nextState(customer.getNextState())
        .status(customer.getStatus()).contact(toContactDto(customer.getContact()))
        .document(toDocument(customer.getDocument())).files(toFiles(customer.getFiles()))
        .extraInfo(toExtraInfo(customer.getExtraInfo())).build();
  }

  private ExtraInfoDto toExtraInfo(ExtraInfo extraInfo) {
    return ExtraInfoDto.builder().id(extraInfo.getId())
        .politicallyExposed(extraInfo.isPoliticallyExposed()).build();
  }

  private List<FileDto> toFiles(List<Files> files) {
    return files.stream()
        .filter(x -> FileType.IDENTIFICATION.equals(x.getType()))
        .map(
            x -> FileDto.builder()
                .id(x.getId()).type(x.getType()).imagePath(x.getImagePath())
                .build())
        .toList();
  }

  private DocumentDto toDocument(Document document) {
    return DocumentDto.builder().id(document.getId()).type(document.getType())
        .country(document.getCountry()).number(document.getNumber()).build();
  }

  private ContactDto toContactDto(Contact contact) {
    return ContactDto.builder().id(contact.getId()).countryCode(contact.getCountryCode())
        .callingCode(contact.getCallingCode()).phoneNumber(contact.getPhoneNumber())
        .isPhoneValidated(contact.isPhoneValidated()).build();
  }
}
