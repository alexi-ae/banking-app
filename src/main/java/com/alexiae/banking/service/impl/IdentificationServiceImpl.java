package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Document;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.repository.IdentificationRepository;
import com.alexiae.banking.service.IdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentificationServiceImpl extends CRUDImpl<Document, Long> implements
    IdentificationService {

  @Autowired
  private IdentificationRepository repository;

  @Override
  protected GenericRepository<Document, Long> getRepository() {
    return repository;
  }
}
