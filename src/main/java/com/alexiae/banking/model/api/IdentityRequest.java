package com.alexiae.banking.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentityRequest {

  private String email;
  private String documentCountry;
  private String documentType;
  private String documentNumber;
}
