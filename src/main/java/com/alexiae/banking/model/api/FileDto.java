package com.alexiae.banking.model.api;

import com.alexiae.banking.model.enums.FileType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileDto {

  private Long id;

  private FileType type;

  private String imagePath;

}
