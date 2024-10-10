package com.alexiae.banking.controller;

import com.alexiae.banking.model.api.JwtRequest;
import com.alexiae.banking.model.api.JwtResponse;
import com.alexiae.banking.facede.AuthFacade;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthFacade authFacade;

  @PostMapping("/login")
  public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws Exception {
    return authFacade.auth(authenticationRequest);
  }

}
