package com.alexiae.banking.service;

import com.alexiae.banking.model.dto.UserDto;
import com.alexiae.banking.model.entity.User;

public interface UserService extends CRUD<User, Long> {

  UserDto findByUsername(String username);
}
