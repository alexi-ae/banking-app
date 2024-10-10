package com.alexiae.banking.repository;

import com.alexiae.banking.model.entity.Account;

public interface AccountRepository extends GenericRepository<Account, Long> {

  boolean existsByNumber(String accountNumber);
}
