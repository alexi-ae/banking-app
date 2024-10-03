package com.alexiae.banking.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// order 0
@Getter
@Setter
@RequiredArgsConstructor
public class JwtRequest {

  private String username;
  private String password;
}
