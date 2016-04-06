package dummy.core.webwidgets;

import dummy.core.libs.Log;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: Label
 * @Description: The Label class is a wrapper class to allow centralized
 * control of common web widget objects and methods
 */
public class Label extends WebWidget {


	//constructors
	public Label(String sID) {
		super(sID,"Label");

	}
	
	/**
	 * Gets text from label type widget
	 * @return String - returns label text of calling object
	 */
	public String getText() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			return SeleniumCore.getBrowser().getText(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return "";
		}
	}
	
	/**
	* Highlights the Label object
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
	* Scrolls the Label into view
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

	