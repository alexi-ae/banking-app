package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.ExtraInfo;
import com.alexiae.banking.repository.ExtraInfoRepository;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.service.ExtraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtraInfoServiceImpl extends CRUDImpl<ExtraInfo, Long> implements ExtraInfoService {

  @Autowired
  private ExtraInfoRepository repository;

  @Override
  protected GenericRepository<ExtraInfo, Long> getRepository() {
    return repository;
  }
}
