package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class BaseTest {
  protected WebDriver driver;

  @BeforeMethod
  public void setup() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get("http://localhost:8081");
  }

  @AfterMethod
  public void teardown() {
    if (driver != null)
      driver.quit();
  }
}
