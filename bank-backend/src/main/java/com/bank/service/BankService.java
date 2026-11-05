package com.bank.service;

import com.bank.model.*;
import com.bank.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class BankService {
  private final AccountRepository accounts;
  private final TransactionRepository tx;

  public BankService(AccountRepository a, TransactionRepository t) {
    accounts = a;
    tx = t;
  }

  public Account register(Account a) {
    if (a.getUsername() == null || a.getUsername().isBlank() || a.getPassword() == null || a.getPassword().isBlank())
      throw new IllegalArgumentException("Username and password are required");
    if (accounts.existsByUsername(a.getUsername()))
      throw new IllegalArgumentException("Username already exists");
    if (a.getEmail() != null && accounts.existsByEmail(a.getEmail()))
      throw new IllegalArgumentException("Email already exists");
    a.setBalance(0);
    return accounts.save(a);
  }

  public Account login(String u, String p) {
    return accounts.findByUsernameAndPassword(u, p)
        .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
  }

  public Account get(Long id) {
    return accounts.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found"));
  }

  @Transactional
  public Account deposit(Long id, double amount) {
    valid(amount);
    Account a = get(id);
    a.setBalance(a.getBalance() + amount);
    accounts.save(a);
    tx.save(new Transaction(id, "DEPOSIT", amount, "Cash deposit"));
    return a;
  }

  @Transactional
  public Account withdraw(Long id, double amount) {
    valid(amount);
    Account a = get(id);
    if (a.getBalance() < amount)
      throw new IllegalArgumentException("Insufficient balance");
    a.setBalance(a.getBalance() - amount);
    accounts.save(a);
    tx.save(new Transaction(id, "WITHDRAW", amount, "Cash withdrawal"));
    return a;
  }

  @Transactional
  public void transfer(Long from, Long to, double amount) {
    valid(amount);
    if (from.equals(to))
      throw new IllegalArgumentException("Cannot transfer to same account");
    Account a = get(from), b = get(to);
    if (a.getBalance() < amount)
      throw new IllegalArgumentException("Insufficient balance");
    a.setBalance(a.getBalance() - amount);
    b.setBalance(b.getBalance() + amount);
    accounts.save(a);
    accounts.save(b);
    tx.save(new Transaction(from, "TRANSFER_OUT", amount, "To account " + to));
    tx.save(new Transaction(to, "TRANSFER_IN", amount, "From account " + from));
  }

  public List<Transaction> history(Long id) {
    get(id);
    return tx.findByAccountIdOrderByCreatedAtDesc(id);
  }

  private void valid(double amount) {
    if (!Double.isFinite(amount) || amount <= 0)
      throw new IllegalArgumentException("Amount must be greater than zero");
  }
}
