package example1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Test2 {
	
	public WebDriver driver;
  
	@BeforeTest
	  public void beforeTest() {
		  		  
		  driver = new FirefoxDriver();
		  Reporter.log("Starting the Firefox Browser...");
	  }
	

	@Test(enabled= false)
	  public void testFacebook() {
			
			driver.get("http://www.facebook.com/");
			Reporter.log("Opening Facebook ...");
						
			System.out.println("Test 3");
			Reporter.log(driver.getTitle());
	  }
	
		@Test
	  public void testLinkedIn() {
			
			driver.get("https://in.linkedin.com/");
			Reporter.log("Opening LinkedIn ...");
						
			System.out.println("Test 4");
			Reporter.log(driver.getTitle());
	  }
  

  @AfterTest
  public void afterTest() {
	  
	  driver.quit();
	  Reporter.log("Closing the Browser...");
  }

}
