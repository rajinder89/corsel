package dummy.core.webwidgets;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: StaticText
 * @Description: The StaticText class is a wrapper class to allow centralized
 * control of common web widget objects and methods
 */

public class StaticText extends WebWidget {

	
	public StaticText(String sID) {
		super(sID,"TextField");

	}
	
	// constructor to override object name with sLabel variable
	public StaticText(String sID, String sLabel) {
		super(sID, "TextField", sLabel);
	}


	/**
	 * Silent click on text field for text field selecting purposes
	 * @return n/a
	 */
	public void click() 
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator))
			{
				ATUReports.add("Click " + sIdentifier + " " + sClassName, LogAs.PASSED, null);
				SeleniumCore.getBrowser().click(sLocator);
			} 
		}
		catch (Exception e) 
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", "", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	

	/**
	 * Determines if the specified Text is visible.
	 * This method will fail if the Text is not present.
	 * @return true if the specified Text is visible, false otherwise
	 */
	public boolean isTextPresent() 
	{
		Boolean bIstextPresent = null;
		try{

			if(SeleniumCore.getBrowser().isTextPresent(sLocator))
			{
				bIstextPresent=true;
				ATUReports.add("Verify Text Present", "","", 
								"" + SeleniumCore.getBrowser().isTextPresent(sLocator), 
								LogAs.PASSED, 
								null);
			}
		}
		catch (Exception e) 
		{			
			bIstextPresent=false;
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", "", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
		}

		return bIstextPresent;

	}
	

	/**
	 * Determines if the specified Text is visible.
	 * This method will log the details if the Text is not present.
	 * @return true if the specified Text is visible, false otherwise
	 */
	public boolean isTextPresentIgnoreError() {

		Boolean bIstextPresent = null;
		if(SeleniumCore.getBrowser().isTextPresent(sLocator))
		{
			bIstextPresent=true;
		}
		else
		{
			bIstextPresent=false;
		}
		return bIstextPresent;

	}
	
	
	/**
	* Highlights the static text field object
	* 
	*/		
	public void highlight() 
	{
		try{
			if (SeleniumCore.getBrowser().exists(sLocator)){
				SeleniumCore.getBrowser().highlightElement(sLocator);
			}
		}
		catch (Exception e) 
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", "", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}

	/**
	* Scrolls the Static text element into view
	* 
	*/		
	public void scrollElementIntoView() 
	{
		try{
			if (SeleniumCore.getBrowser().exists(sLocator))
			{
				SeleniumCore.getBrowser().scrollElementIntoView(sLocator);
			}
		}
		catch (Exception e) 
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", "", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
		}
    }


}