package dummy.core.webwidgets;

import dummy.core.libs.Log;
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


	/**
	 * Silent click on text field for text field selecting purposes
	 * @return n/a
	 */
	public void click() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().click(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}

	/**
	 * Determines if the specified Text is visible.
	 * This method will fail if the Text is not present.
	 * @return true if the specified Text is visible, false otherwise
	 */
	public boolean isTextPresent() {

		Boolean bIstextPresent = null;
		if(SeleniumCore.getBrowser().isTextPresent(sLocator)){
			bIstextPresent=true;
		}
		else
		{
			bIstextPresent=false;
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
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
		if(SeleniumCore.getBrowser().isTextPresent(sLocator)){
			bIstextPresent=true;
		}
		else
		{
			bIstextPresent=false;
			Log.logScriptInfo("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
		return bIstextPresent;

	}
	
	
	/**
	* Highlights the static text field object
	* 
	*/		
	public void highlight() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().highlightElement(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
    }

	/**
	* Scrolls the Static text element into view
	* 
	*/		
	public void scrollElementIntoView() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().scrollElementIntoView(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
    }


}