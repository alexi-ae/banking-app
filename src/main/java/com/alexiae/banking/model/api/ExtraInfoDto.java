package com.alexiae.banking.model.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ExtraInfoDto {

  private Long id;

  private boolean politicallyExposed;

}
