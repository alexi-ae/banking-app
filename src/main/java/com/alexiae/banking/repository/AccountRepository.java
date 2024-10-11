package com.alexiae.banking.repository;

import com.alexiae.banking.model.entity.Account;
import java.util.Optional;

public interface AccountRepository extends GenericRepository<Account, Long> {

  boolean existsByNumber(String accountNumber);

  Optional<Account> getAccountByNumber(String accountNumber);
}
