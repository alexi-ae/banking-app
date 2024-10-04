package com.alexiae.banking.model.entity;

import com.alexiae.banking.model.enums.OnboardingStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "next_state")
  private OnboardingStatus nextState;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "birthdate")
  private LocalDate birthdate;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
  private Contact contact;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
  private Document document;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
  private Files files;

  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
  private ExtraInfo extraInfo;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
