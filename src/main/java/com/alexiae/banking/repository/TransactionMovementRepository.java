package com.alexiae.banking.repository;

import com.alexiae.banking.model.entity.TransactionMovement;
import java.util.List;

public interface TransactionMovementRepository extends
    GenericRepository<TransactionMovement, Long> {

  List<TransactionMovement> findAllByAccountId(Long accountId);
}
