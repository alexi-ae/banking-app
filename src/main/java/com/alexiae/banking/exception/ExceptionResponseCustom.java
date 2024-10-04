package com.alexiae.banking.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponseCustom {

  private LocalDateTime date;
  private String httpStatus;
  private String message;
  private String code;
  private String backendMesage;
}
