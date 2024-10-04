package com.alexiae.banking.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "extra_info")
public class ExtraInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String familyData; // Datos de la familia
  private boolean politicallyExposed;

  @OneToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

}
