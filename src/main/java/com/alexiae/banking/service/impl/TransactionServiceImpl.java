package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Transaction;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.repository.TransactionRepository;
import com.alexiae.banking.service.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends CRUDImpl<Transaction, Long> implements
    TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Override
  protected GenericRepository<Transaction, Long> getRepository() {
    return transactionRepository;
  }

  @Override
  public List<Transaction> getByAccountId(Long accountId) {
    return null;
  }
}
