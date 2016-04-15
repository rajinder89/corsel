package dummy.core.webwidgets;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: CheckBox
 * @Description: The CheckBox class is a wrapper class to allow centralized
 * control of common web widget objects and methods
 */

public class CheckBox extends WebWidget {


	public CheckBox(String sID) {
		super(sID,"CheckBox");

	}
	
	//constructor to override object name with sLabel variable
		public CheckBox(String sID, String sLabel) {
			super(sID,"CheckBox",sLabel);

		}

	/**
	 * Checks a checkbox
	 * @return n/a
	 */
	public void check()
	{
		try{
			if (SeleniumCore.getBrowser().exists(sLocator))
			{
				ATUReports.add("Check " + sIdentifier + " " + sClassName, LogAs.PASSED,null);
				SeleniumCore.getBrowser().check(sLocator);
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
	 * Unchecks a checkbox
	 * @return n/a
	 */
	public void uncheck()
	{
		try{
			if (SeleniumCore.getBrowser().exists(sLocator))
			{
				ATUReports.add("Uncheck " + sIdentifier + " " + sClassName, LogAs.PASSED,null);
				SeleniumCore.getBrowser().uncheck(sLocator);
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
	 * Gets whether a toggle-button (checkbox/radio) is checked.
	 * @return true if the checkbox is checked, false otherwise
	 */
	public boolean ischecked() 
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator))
			{
				ATUReports.add("Verify Check box " + sIdentifier + " " + sClassName + " is checked?", "","", 
								"" + SeleniumCore.getBrowser().ischecked(sLocator), 
								LogAs.PASSED, 
								null);

				return SeleniumCore.getBrowser().ischecked(sLocator);
			}

		} 
		catch (Exception e) {

			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", "", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
		}

		return false;

	}

	/**
	 * Gets the (whitespace-trimmed) value of an input field (or anything else
	 * with a value parameter). For check-box/radio elements, the value will be
	 * "on" or "off" depending on whether the element is checked or not.
	 * @Parameter: myLocator, an element locator
	 * @return n/a
	 * @param myLocator 
	 */
	public String getValue() {
		return SeleniumCore.getBrowser().getValue(sLocator);
	}


	
	/**
	 * Clicks on calling object
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
	* Highlights the check box field object
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
	* Scrolls the Check Box element into view
	* 
	*/		
	public void scrollElementIntoView() 
	{
		try{
			if (SeleniumCore.getBrowser().exists(sLocator)){
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