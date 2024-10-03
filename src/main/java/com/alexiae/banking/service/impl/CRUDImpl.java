package com.alexiae.banking.service.impl;

import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.service.CRUD;
import java.util.List;

public abstract class CRUDImpl<T, ID> implements CRUD<T, ID> {

  protected abstract GenericRepository<T, ID> getRepository();

  @Override
  public T create(T obj) {
    return getRepository().save(obj);
  }

  @Override
  public T update(T obj) {
    return getRepository().save(obj);
  }

  @Override
  public List<T> list() {
    return getRepository().findAll();
  }

  @Override
  public T listById(ID id) {
    return getRepository().findById(id).orElse(null);
  }

  @Override
  public void delete(ID id) {
    getRepository().deleteById(id);
  }

}
