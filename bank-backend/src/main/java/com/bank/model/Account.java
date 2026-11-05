package com.bank.model;

import jakarta.persistence.*;

@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String customerName;
  private String email;
  private String mobile;
  private String username;
  private String password;
  private double balance;

  public Account() {
  }

  public Long getId() {
    return id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String v) {
    customerName = v;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String v) {
    email = v;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String v) {
    mobile = v;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String v) {
    username = v;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String v) {
    password = v;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double v) {
    balance = v;
  }
}
