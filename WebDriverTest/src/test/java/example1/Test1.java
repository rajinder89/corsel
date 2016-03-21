package example1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Test1 {
	
	public WebDriver driver;
  
	@BeforeTest
	  public void beforeTest() {
		  		  
		  driver = new FirefoxDriver();
		  Reporter.log("Starting the Firefox Browser...");
	  }
	
	@Test (priority = 0)
	  public void testGoogle() {
			
			driver.get("http://www.google.com/");
			Reporter.log("Opening Google ...");
						
			System.out.println("Test 1");
			Reporter.log(driver.getTitle());
	  }
  
	
	@Test (priority = 1)
	  public void testYahoo() {
			
			driver.get("http://www.yahoo.com/");
			Reporter.log("Opening Yahoo ...");
						
			System.out.println("Test 2");
			Reporter.log(driver.getTitle());
	  }
	

  @AfterTest
  public void afterTest() {
	  
	  driver.quit();
	  Reporter.log("Closing the Browser...");
  }

}
