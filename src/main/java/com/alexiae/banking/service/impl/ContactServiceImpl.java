package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.entity.Contact;
import com.alexiae.banking.repository.ContactRepository;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends CRUDImpl<Contact, Long> implements ContactService {

  @Autowired
  private ContactRepository contactRepository;

  @Override
  protected GenericRepository<Contact, Long> getRepository() {
    return contactRepository;
  }
}
