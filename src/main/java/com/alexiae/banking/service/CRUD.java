package com.alexiae.banking.service;

import java.util.List;
import lombok.SneakyThrows;

public interface CRUD<T, V> {

  @SneakyThrows
  T create(T obj);

  @SneakyThrows
  T update(T obj);

  @SneakyThrows
  List<T> list();

  @SneakyThrows
  T listById(V id);

  @SneakyThrows
  void delete(V id);
}
