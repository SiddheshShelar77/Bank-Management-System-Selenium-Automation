package com.bank.controller;

import com.bank.model.*;
import com.bank.service.BankService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BankController {
  private final BankService service;

  public BankController(BankService s) {
    service = s;
  }

  @PostMapping("/register")
  public Account register(@RequestBody Account a) {
    return service.register(a);
  }

  @PostMapping("/login")
  public Map<String, Object> login(@RequestBody Map<String, String> m) {
    Account a = service.login(m.get("username"), m.get("password"));
    return Map.of("id", a.getId(), "name", a.getCustomerName(), "username", a.getUsername());
  }

  @GetMapping("/accounts/{id}")
  public Account account(@PathVariable Long id) {
    return service.get(id);
  }

  @PostMapping("/accounts/{id}/deposit")
  public Account deposit(@PathVariable Long id, @RequestBody Map<String, Double> m) {
    return service.deposit(id, m.getOrDefault("amount", 0d));
  }

  @PostMapping("/accounts/{id}/withdraw")
  public Account withdraw(@PathVariable Long id, @RequestBody Map<String, Double> m) {
    return service.withdraw(id, m.getOrDefault("amount", 0d));
  }

  @PostMapping("/transfer")
  public Map<String, String> transfer(@RequestBody Map<String, Double> m) {
    service.transfer(m.get("from").longValue(), m.get("to").longValue(), m.get("amount"));
    return Map.of("message", "Transfer successful");
  }

  @GetMapping("/accounts/{id}/transactions")
  public List<Transaction> history(@PathVariable Long id) {
    return service.history(id);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
  public Map<String, String> error(Exception e) {
    return Map.of("error", e.getMessage());
  }
}
