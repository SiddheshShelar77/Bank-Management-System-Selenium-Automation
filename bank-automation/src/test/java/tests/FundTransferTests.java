
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class FundTransferTests extends BaseTest {

  private void login() {
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("admin123");
    driver.findElement(By.id("loginBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(d -> d.findElement(By.id("appPanel")).isDisplayed());
  }

  private void openTransfer() {
    driver.findElement(
        By.xpath("//button[text()='Fund transfer']")).click();
  }

  private void submitTransfer(String receiver, String amount) {
    driver.findElement(By.id("receiver")).sendKeys(receiver);
    driver.findElement(By.id("amount")).sendKeys(amount);
    driver.findElement(By.id("transferBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(d -> !d.findElement(By.id("appMessage"))
            .getText().trim().isEmpty());
  }

  @Test
  public void validTransfer() {
    login();
    openTransfer();

    submitTransfer("2", "100");

    String message = driver.findElement(By.id("appMessage")).getText();

    Assert.assertTrue(
        message.toLowerCase().contains("success"),
        "Valid transfer should succeed. Actual: " + message);
  }

  @Test
  public void invalidReceiverTransfer() {
    login();
    openTransfer();

    submitTransfer("999999999", "100");

    String message = driver.findElement(By.id("appMessage")).getText();

    Assert.assertFalse(
        message.toLowerCase().contains("success"),
        "Invalid receiver transfer should fail");
  }

  @Test
  public void insufficientBalanceTransfer() {
    login();
    openTransfer();

    submitTransfer("2", "999999999");

    String message = driver.findElement(By.id("appMessage")).getText();

    Assert.assertFalse(
        message.toLowerCase().contains("success"),
        "Insufficient balance transfer should fail");
  }

  @Test
  public void zeroTransfer() {
    login();
    openTransfer();

    submitTransfer("2", "0");

    String message = driver.findElement(By.id("appMessage")).getText();

    Assert.assertFalse(
        message.toLowerCase().contains("success"),
        "Zero transfer should fail");
  }

  @Test
  public void negativeTransfer() {
    login();
    openTransfer();

    submitTransfer("2", "-100");

    String message = driver.findElement(By.id("appMessage")).getText();

    Assert.assertFalse(
        message.toLowerCase().contains("success"),
        "Negative transfer should fail");
  }
}
