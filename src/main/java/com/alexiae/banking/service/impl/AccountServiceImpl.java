package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Account;
import com.alexiae.banking.repository.AccountRepository;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends CRUDImpl<Account, Long> implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  protected GenericRepository<Account, Long> getRepository() {
    return accountRepository;
  }

  @Override
  public boolean existByAccountNumber(String accountNumber) {
    return accountRepository.existsByNumber(accountNumber);
  }

  @Override
  public Account getByAccountNumber(String accountNumber) {
    return accountRepository.getAccountByNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
  }

  @Override
  public boolean existsByIdAndCustomerId(Long accountId, Long customerId) {
    return accountRepository.existsByIdAndCustomerId(accountId, customerId);
  }
}
