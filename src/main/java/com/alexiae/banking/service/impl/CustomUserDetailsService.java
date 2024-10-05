package com.alexiae.banking.service.impl;

import com.alexiae.banking.model.dto.UserDto;
import com.alexiae.banking.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto user = userService.findByUsername(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
    List<GrantedAuthority> roles = new ArrayList<>();
    roles.add(new SimpleGrantedAuthority("USER"));
    PasswordEncoder bcrypt = new BCryptPasswordEncoder();
    return new User(user.getEmail(), bcrypt.encode(user.getPassword()), roles);
  }

}
