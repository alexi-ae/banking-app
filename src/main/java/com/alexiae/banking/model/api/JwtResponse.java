package com.alexiae.banking.model.api;

import com.alexiae.banking.model.enums.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

  private String jwttoken;

  private String nextStep;

  public JwtResponse(String token, OnboardingStatus nextState) {
  }
}
