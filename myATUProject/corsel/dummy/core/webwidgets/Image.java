package dummy.core.webwidgets;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: Image
 * @Description: The Image class is a wrapper class to allow centralized 
 * control of common web widget objects and methods 
 */
public class Image extends WebWidget {
	

	public Image(String sID) {
		super(sID,"Image");
		
	}

	//constructor to override object name with sLabel variable
	public Image(String sID, String sLabel) {
		super(sID,"Image",sLabel);

	}

	/**
	 * Clicks on calling object
	 * @return n/a
	 */
	public void click() 
	{
		try{
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
	* Highlights the image object
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
	* Scrolls the Image element into view
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