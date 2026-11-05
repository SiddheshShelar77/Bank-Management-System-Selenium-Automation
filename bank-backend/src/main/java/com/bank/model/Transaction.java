package com.bank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long accountId;
  private String type;
  private double amount;
  private String details;
  private LocalDateTime createdAt = LocalDateTime.now();

  public Transaction() {
  }

  public Transaction(Long accountId, String type, double amount, String details) {
    this.accountId = accountId;
    this.type = type;
    this.amount = amount;
    this.details = details;
  }

  public Long getId() {
    return id;
  }

  public Long getAccountId() {
    return accountId;
  }

  public String getType() {
    return type;
  }

  public double getAmount() {
    return amount;
  }

  public String getDetails() {
    return details;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
