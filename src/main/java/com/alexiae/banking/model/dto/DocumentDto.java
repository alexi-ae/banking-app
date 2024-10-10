package com.alexiae.banking.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DocumentDto {

  private Long id;

  private String type;

  private String country;

  private String number;

}
