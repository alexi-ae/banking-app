package com.alexiae.banking.controller;

import com.alexiae.banking.facede.OnboardingFacade;
import com.alexiae.banking.model.api.ContactRequest;
import com.alexiae.banking.model.api.ContactValidateRequest;
import com.alexiae.banking.model.api.CreateRequest;
import com.alexiae.banking.model.api.ExtraInfoRequest;
import com.alexiae.banking.model.api.IdentityRequest;
import com.alexiae.banking.model.api.OnboardingResponse;
import com.alexiae.banking.model.api.PersonalRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/ob")
public class OnboardingController {

  @Autowired
  private OnboardingFacade onboardingFacade;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@RequestBody CreateRequest request) {
    onboardingFacade.register(request);
  }

  @PostMapping("/contact")
  public OnboardingResponse contact(@RequestBody ContactRequest request,
      @RequestAttribute("email") String email) {
    request.setEmail(email);
    return onboardingFacade.contact(request);
  }

  @PostMapping("/contact-validate")
  public OnboardingResponse contactValidate(@RequestBody ContactValidateRequest request,
      @RequestAttribute("email") String email) {
    request.setEmail(email);
    return onboardingFacade.contactValidate(request);
  }

  @PostMapping("/personal-info")
  public OnboardingResponse personalInfo(@RequestBody PersonalRequest request,
      @RequestAttribute("email") String email) {
    request.setEmail(email);
    return onboardingFacade.personalInfo(request);
  }

  @PostMapping("/identity-info")
  public OnboardingResponse identityInfo(@RequestBody IdentityRequest request,
      @RequestAttribute("email") String email) {
    request.setEmail(email);
    return onboardingFacade.identityInfo(request);
  }

  @PostMapping("/upload-document")
  public OnboardingResponse uploadDocument(@RequestAttribute("email") String email,
      @RequestPart(value = "file") MultipartFile document) {
    return onboardingFacade.uploadDocument(email, document);
  }

  @PostMapping("/extra-info")
  public OnboardingResponse extraInfo(@RequestBody ExtraInfoRequest request,
      @RequestAttribute("email") String email) {
    request.setEmail(email);
    return onboardingFacade.extraInfo(request);
  }

  @PostMapping("/processing-info")
  public OnboardingResponse processingInfo(@RequestAttribute("email") String email) {
    return onboardingFacade.processingInfo(email);
  }
}
