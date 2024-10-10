package com.alexiae.banking.model.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OnboardingResponse {

  private String nextStep;
  private String token;

}
