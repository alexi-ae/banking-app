package com.alexiae.banking.service;

import com.alexiae.banking.model.entity.Customer;

public interface CustomerService extends CRUD<Customer, Long> {

  Customer findByUsername(String email);
}
