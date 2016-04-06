package dummy.core.webwidgets;

import dummy.core.libs.Log;
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
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}
	
	/**
	* Highlights the image object
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
	* Scrolls the Image element into view
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