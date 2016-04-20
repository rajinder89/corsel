package core.webwidgets;

import core.libs.Log;
import core.libs.SeleniumCore;


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
	 */
	public void check()
	{
		if (SeleniumCore.getBrowser().exists(sLocator))
		{
			Log.logScriptInfo("Check " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().check(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
	}

	/**
	 * Unchecks a checkbox
	 */
	public void uncheck()
	{
		if (SeleniumCore.getBrowser().exists(sLocator))
		{
			Log.logScriptInfo("Uncheck " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().uncheck(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
	}

	/**
	 * Gets whether a toggle-button (checkbox/radio) is checked.
	 * @return true if the checkbox is checked, false otherwise
	 */
	public boolean ischecked() {

			return SeleniumCore.getBrowser().ischecked(sLocator);

	}

	/**
	 * Gets the (whitespace-trimmed) value of an input field (or anything else
	 * with a value parameter). For check-box/radio elements, the value will be
	 * "on" or "off" depending on whether the element is checked or not.
	 * @Parameter: myLocator, an element locator
	 * @param myLocator 
	 */
	public String getValue() {
		
		return SeleniumCore.getBrowser().getValue(sLocator);
	}


	
	/**
	 * Clicks on calling object
	 */
	public void click() 
	{
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Click " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().click(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
	}
	
	/**
	* Highlights the check box field object
	*/		
	public void highlight() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().highlightElement(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
    }
	
	
	/**
	* Scrolls the Check Box element into view
	*/		
	public void scrollElementIntoView() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().scrollElementIntoView(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
    }

}