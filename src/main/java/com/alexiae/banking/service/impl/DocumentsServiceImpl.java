package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Files;
import com.alexiae.banking.repository.DocumentsRepository;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentsServiceImpl extends CRUDImpl<Files, Long> implements DocumentsService {

  @Autowired
  private DocumentsRepository repository;

  @Override
  protected GenericRepository<Files, Long> getRepository() {
    return repository;
  }
}
