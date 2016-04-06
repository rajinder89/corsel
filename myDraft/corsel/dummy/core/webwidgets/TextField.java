package dummy.core.webwidgets;

import dummy.core.libs.Log;
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
	 * Sets text into a text field type widget
	 * @param String sText value to type into the text field type widget
	 */
	public void setText(String sText) {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Enter Text: " + "\"" + sText + "\"" + " into " + sIdentifier + " " + sClassName);
			
			
			SeleniumCore.getBrowser().type(sLocator,sText);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}
	
	/**
	 * Checks if TextField isEditable
	 * @return true if TextField is editable, false if TextField is not editable
	 */
	public boolean isEditable() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Text field " + sIdentifier + " " + sClassName + "is editable");
			return SeleniumCore.getBrowser().isEditable(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return false;
		}
		
	}

	/**
	 * Returns the text content from the calling textfield object.
	 * Logs an error if the textfield is not found.
	 * @return String returns text value of calling object
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
	 * Gets XpathCount from text field type widget
	 * <p>
	 * @return String - returns XpathCount value of calling object
	 */
	public Number getXpathCount() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			return SeleniumCore.getBrowser().getXpathCount(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return null ;
		}
	}


	/**
	 * Gets Attribute from text field type widget
	 * attribute can be "href", "id","value", "style", etc
	 * @return String - returns Attribute value of calling object
	 */
	public String getAttribute(String sAttribute) {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			return SeleniumCore.getBrowser().getAttribute(sLocator + "~" + sAttribute);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return "";
		}
	}


	/**
	 * Gets value from text field type widget
	 * @return String returns Attribute value of calling object
	 */
	public String getValue() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			return SeleniumCore.getBrowser().getValue(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return "";
		}
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
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
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
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return windownames;
		}
	}

	/**
	 * silent click on text field. for text field selecting purposes
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
	* Highlights the text field 
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
	* Scrolls the text field element into view
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