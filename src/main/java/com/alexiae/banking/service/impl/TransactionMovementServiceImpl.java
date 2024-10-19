package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.TransactionMovement;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.repository.TransactionMovementRepository;
import com.alexiae.banking.service.TransactionMovementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionMovementServiceImpl extends CRUDImpl<TransactionMovement, Long> implements
    TransactionMovementService {

  @Autowired
  private TransactionMovementRepository transactionMovementRepository;

  @Override
  protected GenericRepository<TransactionMovement, Long> getRepository() {
    return transactionMovementRepository;
  }

  @Override
  public List<TransactionMovement> getByAccountId(Long accountId) {
    return transactionMovementRepository.findAllByAccountId(accountId);
  }
}
