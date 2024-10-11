package com.alexiae.banking.model.entity;

import com.alexiae.banking.model.enums.AccountCurrency;
import com.alexiae.banking.model.enums.AccountStatus;
import com.alexiae.banking.model.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "number", nullable = false, unique = true)
  private String number;

  @Column(name = "number_cci", nullable = false, unique = true)
  private String numberCci;

  @Column(name = "is_main")
  private boolean isMain;

  @Column(name = "holder", nullable = false)
  private String holder;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private AccountType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "currency", nullable = false)
  private AccountCurrency currency;

  @Column(name = "current_balance", nullable = false)
  private BigDecimal currentBalance;

  @Column(name = "opening_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date openingDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private AccountStatus status;

  @Column(name = "daily_transfer_limit")
  private BigDecimal dailyTransferLimit;

  @Column(name = "creation_date", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationDate;

  @Column(name = "update_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateDate;


  @PrePersist
  protected void onCreate() {
    this.creationDate = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updateDate = new Date();
  }

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;
}
