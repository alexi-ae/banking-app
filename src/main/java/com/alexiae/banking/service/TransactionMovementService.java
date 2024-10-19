package com.alexiae.banking.service;

import com.alexiae.banking.model.entity.TransactionMovement;
import java.util.List;

public interface TransactionMovementService extends CRUD<TransactionMovement, Long> {

  List<TransactionMovement> getByAccountId(Long accountId);
}
