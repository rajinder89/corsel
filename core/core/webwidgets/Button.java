package core.webwidgets;

import core.libs.Log;
import core.libs.SeleniumCore;


/**
 * @Class: Button
 * @Description: The Button class is a wrapper class to allow centralized
 * control of common web widget objects and methods
 */

public class Button extends WebWidget {


	public Button(String sID) {
		super(sID,"PushButton");

	}
	
	//constructor to override object name with sLabel variable
	public Button(String sID, String sLabel) {
		super(sID,"Button",sLabel);

	}

	/**
	 * Clicks on calling object
	 * @return n/a
	 */
	public void click() 
	{
		if (SeleniumCore.getBrowser().exists(sLocator))
		{
			Log.logScriptInfo("Click " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().click(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
	}


	/**
	 * Double clicks the right mouse button on an object
	 * @return n/a
	 */
	public void doubleClick() 
	{
		if (SeleniumCore.getBrowser().exists(sLocator))
		{
			Log.logScriptInfo("DoubleClick " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().doubleClick(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +   Log.sDQ + sLocator + Log.sDQ);
		}
	}
	
	
	/**
	 * Submit the specified form
	 * @return n/a
	 */
	public void submit() 
	{
		if (SeleniumCore.getBrowser().exists(sLocator))
		{
			Log.logScriptInfo("Submit " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().submit(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  Log.sDQ + sLocator + Log.sDQ);
		}
	}

	
	/**
	* Highlights the Button
	* 
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
	* Scrolls the Button into view
	* 
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