package com.alexiae.banking.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {

  private String email;
  private String password;
}
