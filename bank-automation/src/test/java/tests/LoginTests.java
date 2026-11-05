
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests extends BaseTest {

  @Test
  public void validLogin() {

    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("admin123");
    driver.findElement(By.id("loginBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> d.findElement(By.id("appPanel"))
        .isDisplayed());

    Assert.assertTrue(
        driver.findElement(By.id("welcome"))
            .getText().contains("Welcome"),
        "Successful login should display Welcome message");
  }

  @Test
  public void invalidPassword() {

    driver.findElement(By.id("username")).sendKeys("admin");
    driver.findElement(By.id("password")).sendKeys("wrong");
    driver.findElement(By.id("loginBtn")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    wait.until(d -> !d.findElement(By.id("loginMessage"))
        .getText().trim().isEmpty());

    Assert.assertTrue(
        driver.findElement(By.id("loginPanel"))
            .isDisplayed(),
        "Login panel should remain visible after failed login");

    Assert.assertFalse(
        driver.findElement(By.id("appPanel"))
            .isDisplayed(),
        "Application dashboard should not open after failed login");

    Assert.assertTrue(
        driver.findElement(By.id("loginMessage"))
            .getText().trim().length() > 0,
        "An error message should be displayed");
  }
}
