package example;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class NewTest {
	
	 private WebDriver driver;  
	 
  @Test
  public void f() {
	  System.out.println("Test");
	  
	  driver.get("http://www.guru99.com/selenium-tutorial.html");  
      String title = driver.getTitle(); 
      System.out.println(title);
      Assert.assertTrue(title.contains("Free Selenium Tutorials")); 
  }
  @BeforeTest
  public void beforeTest() {
	  System.out.println("Before");
	  driver = new FirefoxDriver();
	  
	  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("After");
	  driver.quit(); 
  }

}
