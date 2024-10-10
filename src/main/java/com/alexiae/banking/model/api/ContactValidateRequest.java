package com.alexiae.banking.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactValidateRequest {

  private String email;
  private String code;
}
