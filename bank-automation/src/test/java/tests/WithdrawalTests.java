
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class WithdrawalTests extends BaseTest {

  private void login() {
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("admin123");
    driver.findElement(By.id("loginBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(d -> d.findElement(By.id("appPanel")).isDisplayed());
  }

  private void openWithdraw() {
    driver.findElement(By.xpath("//button[text()='Withdraw']")).click();
    driver.findElement(By.id("amount"));
  }

  private void submitWithdrawal(String amount) {
    driver.findElement(By.id("amount")).sendKeys(amount);
    driver.findElement(By.id("actionBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(d -> !d.findElement(By.id("appMessage"))
            .getText().trim().isEmpty());
  }

  @Test
  public void validWithdrawal() {
    login();
    openWithdraw();
    submitWithdrawal("100");

    Assert.assertTrue(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Valid withdrawal should succeed");
  }

  @Test
  public void zeroWithdrawal() {
    login();
    openWithdraw();
    submitWithdrawal("0");

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Zero withdrawal should fail");
  }

  @Test
  public void negativeWithdrawal() {
    login();
    openWithdraw();
    submitWithdrawal("-100");

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Negative withdrawal should fail");
  }

  @Test
  public void insufficientBalanceWithdrawal() {
    login();
    openWithdraw();
    submitWithdrawal("999999999");

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Insufficient balance withdrawal should fail");
  }

  @Test
  public void blankWithdrawal() {
    login();
    openWithdraw();

    driver.findElement(By.id("actionBtn")).click();

    new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(d -> !d.findElement(By.id("appMessage"))
            .getText().trim().isEmpty());

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Blank withdrawal should fail");
  }
}
