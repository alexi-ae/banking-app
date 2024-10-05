package com.alexiae.banking.model.dto;

import com.alexiae.banking.model.enums.Role;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

  private Long id;

  private String email;

  private String password;

  private Set<Role> authorities; // Roles are simplified for illustration

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
