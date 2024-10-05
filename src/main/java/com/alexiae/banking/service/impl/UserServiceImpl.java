package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.dto.UserDto;
import com.alexiae.banking.model.entity.User;
import com.alexiae.banking.repository.GenericRepository;
import com.alexiae.banking.repository.UserRepository;
import com.alexiae.banking.service.UserService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CRUDImpl<User, Long> implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  protected GenericRepository<User, Long> getRepository() {
    return userRepository;
  }

  @Override
  public UserDto findByUsername(String username) {
    return toDto(userRepository.findByEmail(username).orElse(null));
  }

  private UserDto toDto(User user) {
    if (Objects.isNull(user)) {
      return null;
    }
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .authorities(user.getAuthorities())
        .build();
  }
}
