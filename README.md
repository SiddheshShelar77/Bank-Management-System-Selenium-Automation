# Bank Management System + Selenium Automation

A practical/demo starter project with:
- Spring Boot REST backend (Java 21, Spring Boot 3.5.x)
- MySQL + Spring Data JPA
- Static HTML/CSS/JS frontend served by Spring Boot
- Separate Selenium + TestNG automation Maven project

## Prerequisites
- JDK 21
- Maven (or use IDE Maven wrapper/import)
- MySQL 8
- Chrome browser

## Setup
1. Create database:
   ```sql
   CREATE DATABASE bank_db;
   ```
2. Configure MySQL credentials using environment variables `DB_USERNAME` and `DB_PASSWORD`, or edit `application.properties` locally.
3. Run backend from `bank-backend`:
   ```bash
   mvn spring-boot:run
   ```
4. Open `http://localhost:8081`.
5. Register a user through the UI first, then log in with that account.

## Selenium
1. Start backend first.
2. From `bank-automation` run:
   ```bash
   mvn test
   ```
Selenium 4.25+ uses Selenium Manager to resolve ChromeDriver automatically.

## Notes
- This is an educational demo, not production banking software.
- Passwords are stored in plain text in this minimal starter; do not use real credentials or deploy publicly.
- All financial operations are demo-only.
