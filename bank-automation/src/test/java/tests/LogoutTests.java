
package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogoutTests extends BaseTest {

    private void login() {
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin123");
        driver.findElement(By.id("loginBtn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.findElement(By.id("appPanel")).isDisplayed());
    }

    @Test
    public void successfulLogout() {
        login();

        driver.findElement(By.id("logoutBtn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.findElement(By.id("loginPanel")).isDisplayed());

        Assert.assertTrue(
                driver.findElement(By.id("loginPanel")).isDisplayed(),
                "Login panel should appear after logout");
    }

    @Test
    public void dashboardHiddenAfterLogout() {
        login();

        driver.findElement(By.id("logoutBtn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> !d.findElement(By.id("appPanel")).isDisplayed());

        Assert.assertFalse(
                driver.findElement(By.id("appPanel")).isDisplayed(),
                "Dashboard should be hidden after logout");
    }

    @Test
    public void loginAgainAfterLogout() {
        login();

        // Logout
        driver.findElement(By.id("logoutBtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for login panel
        wait.until(d -> d.findElement(By.id("loginPanel")).isDisplayed());

        // Wait for login button and enter credentials again
        wait.until(d -> d.findElement(By.id("loginBtn")).isDisplayed());

        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin123");

        driver.findElement(By.id("loginBtn")).click();

        // Wait for dashboard after second login
        wait.until(d -> d.findElement(By.id("appPanel")).isDisplayed());

        Assert.assertTrue(
                driver.findElement(By.id("appPanel")).isDisplayed(),
                "User should be able to login again after logout");
    }

}
