package com.alexiae.banking.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {

  private String email;
  private String countryCode;
  private String callingCode;
  private String phoneNumber;

}
