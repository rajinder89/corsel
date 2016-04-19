package dummy.core.libs;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dummy.core.webwidgets.Button;
import dummy.core.webwidgets.StaticText;

public class DatePicker { 
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static Button btnDatePicker() { 
		return new Button("id=ui-datepicker-div");
		
	}
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static Button btnNext() { 
		return new Button("//a[@title='Next']");
		
	}
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static Button btnPrevious() { 
		return new Button("//a[@title='Prev']");
		
	}
	
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static Button btnDepartureDate() { 
		return new Button("id=start_date_sec");
		
	}
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static Button btnReturnDate() { 
		return new Button("id=return_date_sec");
		
	}
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static StaticText stMonth() { 
		return new StaticText("className=ui-datepicker-month");
		
	}
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static StaticText stYear() { 
		return new StaticText("className=ui-datepicker-year");
		
	}
	

	WebDriver driver; 
	
	WebElement datePicker;
	List<WebElement> noOfColumns; 
	List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June","July", 
											"August", "September", "October", "November", "December"); 
	
	// Expected Date, Month and Year 
	int expMonth; 
	int expYear; 
	String expDate = null; 
	
	// Calendar Month and Year 
	String calMonth = null; 
	String calYear = null; 
	boolean dateNotFound;

	@BeforeClass 
	public void start()
	{ 
//		driver = new FirefoxDriver(); 
//		driver.manage().window().maximize(); 
//		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); 
		
		Browser.start();
	}

	@Test 
	public void pickExpDate() throws InterruptedException
	{
//		driver.get("http://www.makemytrip.com/flights"); 
		
		Browser.loadURL("http://www.makemytrip.com/flights");

		//Click on date text box to open date picker popup. 
		btnDepartureDate().click();
		
		dateNotFound = true; 
		//Set your expected date, month and year. 
		expDate = "18";
		expMonth= 12; 
		expYear = 2016; 

		//This loop will be executed continuously till dateNotFound Is true. 
		while(dateNotFound) 
		{ 
		  //Retrieve current selected month name from date picker popup. 
		  //calMonth = driver.findElement(By.className("ui-datepicker-month")).getText(); 
			calMonth = stMonth().getText();

			//Retrieve current selected year name from date picker popup. 
		//	calYear = driver.findElement(By.className("ui-datepicker-year")).getText(); 
			calYear = stYear().getText();
			
			//If current selected month and year are same as expected month and year then go Inside this condition. 
			if(monthList.indexOf(calMonth)+1 == expMonth && (expYear == Integer.parseInt(calYear)))
			{ 
				//Call selectDate function with date to select and set dateNotFound flag to false.
				selectDate(expDate); 
				
				dateNotFound = false; 
			} 

			//If current selected month and year are less than expected month and year then go Inside this condition. 
			else if(monthList.indexOf(calMonth)+1 < expMonth && (expYear == Integer.parseInt(calYear)) || expYear > Integer.parseInt(calYear)) 
			{ //Click on next button of date picker.
				btnNext().click();
			}
			//If current selected month and year are greater than expected month and year then go Inside this condition.
			else if(monthList.indexOf(calMonth)+1 > expMonth && (expYear == Integer.parseInt(calYear)) || expYear < Integer.parseInt(calYear)) 

			{ //Click on previous button of date picker. 
				btnPrevious().click();
			} 
		} Thread.sleep(3000);
	} 
	
	public void selectDate(String date) 
	{ 
		datePicker = driver.findElement(By.id("ui-datepicker-div")); 

		noOfColumns= datePicker.findElements(By.tagName("td")); 
		
		

		//Loop will rotate till expected date not found.
		for (WebElement cell: noOfColumns)
		{
			//Select the date from date picker when condition match.
			if (cell.getText().equals(date))
			{
				cell.findElement(By.linkText(date)).click(); 
				
				break; 
			} 
		} 
	}

}

