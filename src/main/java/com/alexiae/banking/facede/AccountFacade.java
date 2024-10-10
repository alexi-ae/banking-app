package com.alexiae.banking.facede;

import com.alexiae.banking.model.api.AccountResponse;
import com.alexiae.banking.model.entity.Account;
import com.alexiae.banking.model.entity.Customer;
import com.alexiae.banking.model.enums.AccountCurrency;
import com.alexiae.banking.model.enums.AccountStatus;
import com.alexiae.banking.model.enums.AccountType;
import com.alexiae.banking.service.AccountService;
import com.alexiae.banking.service.CustomerService;
import com.alexiae.banking.utils.AccountUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountFacade {

  @Autowired
  private AccountService accountService;

  @Autowired
  private CustomerService customerService;

  public void createAccount(Customer customer) {
    Account account = new Account();
    String numberAccount = generateUniqueAccountNumber();
    account.setNumber(numberAccount);
    account.setNumberCci(AccountUtils.generarNumeroCCI(numberAccount, "001"));
    account.setMain(Boolean.TRUE);
    account.setHolder(customer.getFirstName() + " " + customer.getLastName());
    account.setType(AccountType.AHORROS);
    account.setCurrency(AccountCurrency.SOLES);
    account.setCurrentBalance(BigDecimal.ZERO);
    account.setOpeningDate(new Date());
    account.setStatus(AccountStatus.ACTIVE);
    account.setDailyTransferLimit(BigDecimal.valueOf(2000.00));
    account.setCustomer(customer);
    accountService.create(account);
  }

  public String generateUniqueAccountNumber() {
    String numeroCuenta;
    do {
      numeroCuenta = AccountUtils.numberAccountGenerate();
    } while (accountService.existByAccountNumber(numeroCuenta));
    return numeroCuenta;
  }

  public List<AccountResponse> getAccounts(String email) {
    Customer customer = customerService.findByUsername(email);
    return customer.getAccounts().stream().map(
        account -> AccountResponse.builder().id(account.getId()).number(account.getNumber())
            .numberCci(account.getNumberCci()).isMain(account.isMain()).type(account.getType())
            .currency(account.getCurrency()).currentBalance(account.getCurrentBalance())
            .status(account.getStatus()).dailyTransferLimit(account.getDailyTransferLimit())
            .build()).toList();
  }
}
