
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class BalanceTests extends BaseTest {

  private void login() {
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("admin123");
    driver.findElement(By.id("loginBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(d -> d.findElement(By.id("appPanel")).isDisplayed());
  }

  private void openBalance() {
    driver.findElement(
        By.xpath("//button[text()='Balance enquiry']")).click();
  }

  private void checkBalance() {
    driver.findElement(By.id("balanceBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(d -> !d.findElement(By.id("balanceResult"))
            .getText().trim().isEmpty());
  }

  @Test
  public void validBalanceEnquiry() {
    login();
    openBalance();
    checkBalance();

    String result = driver.findElement(By.id("balanceResult")).getText();

    Assert.assertTrue(
        result.contains("Current balance: ₹"),
        "Balance should be displayed");
  }

  @Test
  public void balanceEnquiryAfterDeposit() {
    login();

    driver.findElement(By.xpath("//button[text()='Deposit']")).click();
    driver.findElement(By.id("amount")).sendKeys("500");
    driver.findElement(By.id("actionBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(d -> d.findElement(By.id("appMessage"))
            .getText().contains("successful"));

    openBalance();
    checkBalance();

    Assert.assertTrue(
        driver.findElement(By.id("balanceResult"))
            .getText().contains("Current balance: ₹"),
        "Updated balance should be displayed after deposit");
  }

  @Test
  public void balanceEnquiryAfterWithdrawal() {
    login();

    driver.findElement(By.xpath("//button[text()='Withdraw']")).click();
    driver.findElement(By.id("amount")).sendKeys("100");
    driver.findElement(By.id("actionBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(d -> d.findElement(By.id("appMessage"))
            .getText().contains("successful"));

    openBalance();
    checkBalance();

    Assert.assertTrue(
        driver.findElement(By.id("balanceResult"))
            .getText().contains("Current balance: ₹"),
        "Updated balance should be displayed after withdrawal");
  }
}
