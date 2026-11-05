
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.UUID;

public class AccountCreationTests extends BaseTest {

  @Test
  public void validAccountCreation() {

    driver.findElement(By.className("secondary")).click();

    String username = "user" + UUID.randomUUID()
        .toString().substring(0, 8);

    driver.findElement(By.id("rname"))
        .sendKeys("Test Customer");

    driver.findElement(By.id("remail"))
        .sendKeys(username + "@test.com");

    driver.findElement(By.id("rmobile"))
        .sendKeys("9876543210");

    driver.findElement(By.id("ruser"))
        .sendKeys(username);

    driver.findElement(By.id("rpass"))
        .sendKeys("Test@123");

    driver.findElement(By.id("registerBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    wait.until(d -> !d.findElement(By.id("registerMessage"))
        .getText().trim().isEmpty());

    String message = driver.findElement(By.id("registerMessage")).getText();

    Assert.assertTrue(
        message.contains("Account created"),
        "Account creation should succeed. Actual: " + message);
  }

  @Test
  public void emptyRegistration() {

    driver.findElement(By.className("secondary")).click();

    driver.findElement(By.id("registerBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> !d.findElement(By.id("registerMessage"))
        .getText().trim().isEmpty());

    String message = driver.findElement(By.id("registerMessage")).getText();

    Assert.assertFalse(
        message.trim().isEmpty(),
        "Validation message should appear");
  }
}
