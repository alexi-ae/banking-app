package com.alexiae.banking.service;

import com.alexiae.banking.model.entity.Account;

public interface AccountService extends CRUD<Account, Long> {

  boolean existByAccountNumber(String numeroCuenta);

  Account getByAccountNumber(String accountNumber);

  boolean existsByIdAndCustomerId(Long accountId, Long customerId);
}
