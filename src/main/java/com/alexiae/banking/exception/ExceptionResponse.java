package com.alexiae.banking.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

  private LocalDateTime date;
  private String message;
  private String details;
}
