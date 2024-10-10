package com.alexiae.banking.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalRequest {

  private String email;
  private String firstName;
  private String lastName;
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate birthdate;

}
