package com.alexiae.banking.model.entity;

import com.alexiae.banking.model.enums.CustomerStatus;
import com.alexiae.banking.model.enums.OnboardingStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private CustomerStatus status;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Contact contact;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Document document;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Files> files;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private ExtraInfo extraInfo;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Account> accounts;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
}
