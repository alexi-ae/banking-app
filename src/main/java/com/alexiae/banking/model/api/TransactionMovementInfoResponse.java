package com.alexiae.banking.model.api;

import com.alexiae.banking.model.enums.MovementType;
import com.alexiae.banking.model.enums.TransactionStatus;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TransactionMovementInfoResponse {

  private String operationCode;
  private BigDecimal amount;
  private String description;
  private TransactionStatus status;
  private MovementType type;
  private Date creationDate;

}
