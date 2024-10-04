package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Security;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.repository.SecurityRepository;
import com.alexiae.banking.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl extends CRUDImpl<Security, Long> implements SecurityService {

  @Autowired
  private SecurityRepository repository;

  @Override
  protected GenericRepository<Security, Long> getRepository() {
    return repository;
  }
}
