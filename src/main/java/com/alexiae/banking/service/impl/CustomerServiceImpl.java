package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Customer;
import com.alexiae.banking.repository.CustomerRepository;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends CRUDImpl<Customer, Long> implements CustomerService {

  @Autowired
  private CustomerRepository repository;

  @Override
  protected GenericRepository<Customer, Long> getRepository() {
    return repository;
  }

  @Override
  public Customer findByUsername(String email) {
    return repository.findCustomerByUserEmail(email);
  }
}
