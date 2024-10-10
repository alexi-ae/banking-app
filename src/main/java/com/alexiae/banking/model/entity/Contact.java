package com.alexiae.banking.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contact")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "country_code")
  private String countryCode;

  @Column(name = "calling_code")
  private String callingCode;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "phone_validated")
  private boolean isPhoneValidated;
}
