package com.alexiae.banking.model.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
