package com.alexiae.banking.service;

import com.alexiae.banking.model.entity.Transaction;
import java.util.List;

public interface TransactionService extends CRUD<Transaction, Long> {

  List<Transaction> getByAccountId(Long accountId);
}
