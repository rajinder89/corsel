package dummy.core.webwidgets;

import dummy.core.libs.Log;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: RadioButton
 * @Description: The RadioButton class is a wrapper class to allow centralized
 * control of common web widget objects and methods
 */
public class RadioButton extends WebWidget {

	public RadioButton(String sID) {
		super(sID,"RadioButton");

	}

	/**
	 * Clicks on calling object
	 * @return n/a
	 */
	public void click() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Click " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().click(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}

	/**
	 * Gets the (whitespace-trimmed) value of an input field (or anything else
	 * with a value parameter). For check-box/radio elements, the value will be
	 * "on" or "off" depending on whether the element is checked or not.
	 * @Parameter: myLocator, an element locator
	 * @return n/a
	 */
	public String getValue(String myLocator) {
		return SeleniumCore.getBrowser().getValue(myLocator);
	}
	
	
	/**
	 * Gets whether a toggle-button (checkbox/radio) is checked.
	 * @return true if the checkbox is checked, false otherwise
s	 */
	public boolean ischecked() {
		return SeleniumCore.getBrowser().ischecked(sLocator);

	}

	
	/**
	* Highlights the radio button object
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
	* Scrolls the Radio Button into view
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