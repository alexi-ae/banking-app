package com.alexiae.banking.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtraInfoRequest {

  private String email;
  private boolean politicallyExposed;

}
