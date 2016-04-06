package dummy.core.webwidgets;

import dummy.core.libs.Log;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: Link
 * @Description: The Link class is a wrapper class to allow centralized 
 * control of common web widget objects and methods 
 */
public class Link extends WebWidget {
	
	public Link(String sID) {
		super(sID,"Link");
		
	}

	
	/**
	 * Clicks on calling object
	 * @return n/a
	 */
	public void click() 
	{
		try {			
				SeleniumCore.getBrowser().exists(sLocator);
				Log.logScriptInfo("Click " + sIdentifier + " " + sClassName);
				SeleniumCore.driver.findElement(SeleniumCore.getBrowser().by(sLocator,bExactMatch)).click();
			}
		catch(Exception e)
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}
	
	
	/**
	 * Double clicks the right mouse button on an object
	 * @return n/a
	 */
	public void doubleClick() 
	{
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("DoubleClick " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().doubleClick(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +   "\"" + sLocator + "\"");
		}
	}
	
	
	/**
	* Highlights the link object
	* @return n/a
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
	* Scrolls the Link element into view
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