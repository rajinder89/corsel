package dummy.core.webwidgets;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.SeleniumCore;


/**
 * TextField WebWidget Class.
 * This class contains all methods specific to TextField objects.
 * The TextField class is a wrapper class to allow centralized
 * control of common webwidget objects and methods. 
 */
public class TextField extends WebWidget {


	/**
	 * Constructor for text field object
	 * <p>
	 * @param String sID value of text field object
	 */
	public TextField(String sID) {
		super(sID,"TextField");

	}
	
	/**
	 * Constructor to override object name with sLabel variable
	 * <p>
	 * @param String sID value of text field object
	 * @param String sLabel value to use in log output for text field object
	 */
	public TextField(String sID, String sLabel) {
		super(sID,"TextField",sLabel);

	}
	

	/**
	 * Sets text into a text field type widget
	 * @param String sText value to type into the text field type widget
	 */
	public void setText(String sText) {
				
		try {
			if (SeleniumCore.getBrowser().exists(sLocator)){
			
				ATUReports.add("Enter Text: " + "\"" + sText + "\"" + " into " + sIdentifier + " " + sClassName, 
								sText,
								"Text should be entered", 
								"Text entered successfully", 
								LogAs.PASSED, 
								null);
			
				
				SeleniumCore.getBrowser().type(sLocator,sText);
			}

		} catch (Exception e) {
			
				ATUReports.add("Text entry " + "\"" + sText + "\"" +" Failed "+ " into " + sIdentifier + " " + sClassName + " with locator of " +  "\"" + sLocator + "\"", 
								sText,
								"Text should be entered", 
								"Text could not be entered into " + sIdentifier + " " + sClassName + e.getMessage(), 
								LogAs.FAILED, 
								new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	/**
	 * Checks if TextField isEditable
	 * @return true if TextField is editable, false if TextField is not editable
	 */
	public boolean isEditable() {
		try {
			if (SeleniumCore.getBrowser().exists(sLocator)){
							
				ATUReports.add("Verify Text field " + sIdentifier + " " + sClassName + " is editable?", 
						"",
						"", 
						"" + SeleniumCore.getBrowser().isEditable(sLocator), 
						LogAs.PASSED, 
						null);
				
				return SeleniumCore.getBrowser().isEditable(sLocator);
				
			}

		} 
		catch (Exception e) {
				
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
							"", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		
		return false;
	}

	/**
	 * Returns the text content from the calling textfield object.
	 * Logs an error if the textfield is not found.
	 * @return String returns text value of calling object
	 */
	public String getText() 
	{
		try {
				if (SeleniumCore.getBrowser().exists(sLocator))
				{
					return SeleniumCore.getBrowser().getText(sLocator);
				}
			} 
		catch (Exception e) 
		{					
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
							"", 
							e.getMessage(), 
							LogAs.FAILED, 
							new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		
		return "";
	}

	/**
	 * Gets XpathCount from text field type widget
	 * <p>
	 * @return String - returns XpathCount value of calling object
	 */
	public Number getXpathCount() 
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator))
				{
					return SeleniumCore.getBrowser().getXpathCount(sLocator);
				}

			} catch (Exception e) {
			
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
					"", 
					e.getMessage(), 
					LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	
		return null ;
	}


	/**
	 * Gets Attribute from text field type widget
	 * attribute can be "href", "id","value", "style", etc
	 * @return String - returns Attribute value of calling object
	 */
	public String getAttribute(String sAttribute) 
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator)){
				return SeleniumCore.getBrowser().getAttribute(sLocator + "~" + sAttribute);
			}
	
		} catch (Exception e) {
	
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
					"", 
					e.getMessage(), 
					LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		
		return "";
	}


	/**
	 * Gets value from text field type widget
	 * @return String returns Attribute value of calling object
	 */
	public String getValue() 
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator)){
				return SeleniumCore.getBrowser().getValue(sLocator);
			}
		} catch (Exception e) {

			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
					"", 
					e.getMessage(), 
					LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}

		return "";
	}


	/**
	 * Returns string array of window ids for each page on the system desktop
	 * @return string array of window ids for each page on the system desktop
	 */
	public String[] getAllWindowIds() {
		String[] windowIds=null;
		if (SeleniumCore.getBrowser().exists(sLocator)){
			windowIds=SeleniumCore.getBrowser().getAllWindowIds();
			return windowIds;
		}
		else
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			return windowIds;
		}
	}

	/**
	 * Returns string array of window names for each page on the system desktop
	 * @return string array of window names for each page on the system desktop
	 */
	public String[] getAllWindowNames() {
		String[] windownames=null;
		if (SeleniumCore.getBrowser().exists(sLocator)){
			windownames=SeleniumCore.getBrowser().getAllWindowNames();
			return windownames;
		}
		else
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			return windownames;
		}
	}

	/**
	 * silent click on text field. for text field selecting purposes
	 */
	public void click() 
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator)){
				SeleniumCore.getBrowser().click(sLocator);
			}

		} catch (Exception e) {
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
					"", 
					e.getMessage(), 
					LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}

	
	/**
	* Highlights the text field 
	* 
	*/		
	public void highlight()
	{
		try {
			if (SeleniumCore.getBrowser().exists(sLocator)){
				SeleniumCore.getBrowser().highlightElement(sLocator);
			}
			
		} catch (Exception e) {
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
					"", 
					e.getMessage(), 
					LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	
	/**
	* Scrolls the text field element into view
	* 
	*/		
	public void scrollElementIntoView() 
	{
		try {

			if (SeleniumCore.getBrowser().exists(sLocator)){
				SeleniumCore.getBrowser().scrollElementIntoView(sLocator);
			}

		} catch (Exception e) {

			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", 
					"", 
					e.getMessage(), 
					LogAs.FAILED, 
					new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	

}