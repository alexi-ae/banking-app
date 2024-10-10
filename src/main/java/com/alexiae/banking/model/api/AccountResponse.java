package com.alexiae.banking.model.api;

import com.alexiae.banking.model.enums.AccountCurrency;
import com.alexiae.banking.model.enums.AccountStatus;
import com.alexiae.banking.model.enums.AccountType;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccountResponse {

  private Long id;
  private String number;
  private String numberCci;
  private boolean isMain;
  private AccountType type;
  private AccountCurrency currency;
  private BigDecimal currentBalance;
  private AccountStatus status;
  private BigDecimal dailyTransferLimit;

}
