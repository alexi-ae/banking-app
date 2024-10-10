package com.alexiae.banking.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ContactDto {

  private Long id;

  private String countryCode;

  private String callingCode;

  private String phoneNumber;

  private boolean isPhoneValidated;

}
