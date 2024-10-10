package com.alexiae.banking.model.api;

import com.alexiae.banking.model.dto.ContactDto;
import com.alexiae.banking.model.dto.DocumentDto;
import com.alexiae.banking.model.dto.ExtraInfoDto;
import com.alexiae.banking.model.dto.FileDto;
import com.alexiae.banking.model.enums.CustomerStatus;
import com.alexiae.banking.model.enums.OnboardingStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerInfoResponse {

  private Long id;
  private OnboardingStatus nextState;
  private String firstName;
  private String lastName;
  private LocalDate birthdate;
  private CustomerStatus status;
  private ContactDto contact;
  private DocumentDto document;
  private List<FileDto> files;
  private ExtraInfoDto extraInfo;

}
