package com.alexiae.banking.model.api;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TransactionRequest {

  private String originAccount;
  private String destinationAccount;
  private BigDecimal amount;
  private String description;

}
