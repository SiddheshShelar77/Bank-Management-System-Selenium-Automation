
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DepositTests extends BaseTest {

  private void login() {
    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("admin123");
    driver.findElement(By.id("loginBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> d.findElement(By.id("appPanel")).isDisplayed());
  }

  private void openDeposit() {
    driver.findElement(
        By.xpath("//button[text()='Deposit']")).click();

    driver.findElement(By.id("amount"));
  }

  @Test
  public void validDeposit() {
    login();
    openDeposit();

    driver.findElement(By.id("amount")).sendKeys("500");
    driver.findElement(By.id("actionBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(d -> d.findElement(By.id("appMessage"))
        .getText().contains("successful"));

    String message = driver.findElement(By.id("appMessage")).getText();

    Assert.assertTrue(
        message.contains("successful"),
        "Valid deposit should succeed");
  }

  @Test
  public void zeroDeposit() {
    login();
    openDeposit();

    driver.findElement(By.id("amount")).sendKeys("0");
    driver.findElement(By.id("actionBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> !d.findElement(By.id("appMessage"))
        .getText().trim().isEmpty());

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Zero deposit should not succeed");
  }

  @Test
  public void negativeDeposit() {
    login();
    openDeposit();

    driver.findElement(By.id("amount")).sendKeys("-100");
    driver.findElement(By.id("actionBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> !d.findElement(By.id("appMessage"))
        .getText().trim().isEmpty());

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Negative deposit should not succeed");
  }

  @Test
  public void blankDeposit() {
    login();
    openDeposit();

    driver.findElement(By.id("actionBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> !d.findElement(By.id("appMessage"))
        .getText().trim().isEmpty());

    Assert.assertFalse(
        driver.findElement(By.id("appMessage"))
            .getText().contains("successful"),
        "Blank deposit should not succeed");
  }
}
