package dummy.core.webwidgets;

import org.openqa.selenium.interactions.Actions;

import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.libs.SeleniumCore;


/**
 * @Class: WebWidget
 * @Description: The WebWidget class is a wrapper class to allow centralized
 * control of common web widget objects and methods
 */
public class WebWidget  {

	public String sLocator = "";
	public String sIdentifier = "";
	public String sClassName = "";
//	public int iIndexValue = 0;

	public String sObjName = "";
	public String sObjParent = "";
	public static boolean bExactMatch=false;




	//constructor
	public WebWidget(String sID, String sClassType) {
		sLocator = sID;
		sClassName = sClassType;

		try{
		String sTmpObjPar = new Throwable().fillInStackTrace().getStackTrace()[2].getClassName();
			//sun.reflect.Reflection.getCallerClass(2).getName();
		
	//	System.out.println("sTmpObjPar is: "+sTmpObjPar);
		
		int iParPos = sTmpObjPar.lastIndexOf(".");
		sObjParent = sTmpObjPar.substring(iParPos+1);

		String sTmpObj = new Throwable().fillInStackTrace().getStackTrace()[2].getMethodName();
		
	//	System.out.println("sTmpObj is: "+sTmpObj);
			//sun.reflect.Reflection.getCallerClass(2).getMethods()[0].toString();
		
		int iPos = sTmpObj.lastIndexOf(".");
		sObjName = sTmpObj.substring(iPos+1);

		sIdentifier = sObjParent + "." + sObjName;
	//	System.out.println("sIdentifier is: "+sIdentifier);
		
		
		}
		catch(Exception e){
			Log.errorHandler("Error occurred with definition of object", e);
		}
	}

	
	/**
	 * Gets text from text field type widget
	 * @return String - returns text value of calling object
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
	 * clicks on calling object
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
	 * Clicks a widget found using exact match for sLocator string passed in
	 * @Parameter: String sLocator - string used to locate the widget
	 * @return n/a
	 * @param sLocator
	 */
	public void clickExactMatch()
	{
		
		try{
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Click " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().clickExactMatch(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
		}
		catch(Exception e){
			Log.errorHandler("Could not find exact match for " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"",e);
		}
		
		
	}
	

	/**
	 * Clicks on calling object
	 * @return n/a
	 */
	public void clickAt() {
		
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Click " + sIdentifier + " " + sClassName);
			SeleniumCore.getBrowser().clickAt(sLocator, "");
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}
	




	/**
	 * double clicks the right mouse button on an object
	 * @return n/a
	 */
	public void doubleClick() {
		
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
	 * Simulates a user hovering a mouse over the specified element.
	 * @return n/a
	 */
	public void hover() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().focus(sLocator);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}



	/**
	 * Sets focus on an object
	 * if the element is an input field, move the cursor to that field.
	 * @return n/a
	 */
	public void focus() {
		
		if (SeleniumCore.getBrowser().exists(sLocator)){
			
			Actions action = new Actions(SeleniumCore.driver);
			action.moveToElement(SeleniumCore.driver.findElement(SeleniumCore.getBrowser().by(sLocator))).perform();
				
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}

	/**
	 * Returns true if an object is found, false if object is not found
	 * @return n/a
	 */
	public boolean exists()
	{
		waitForExistence(Log.giAutomationLongTO);
		return SeleniumCore.getBrowser().isElementPresent(sLocator);
	}

	/**
	 * @method exists(int iWait)
	 * @param iWait - specify the amount of time to wait for existence using Log.giAutomation time variables.
	 */
	public boolean exists(int iWait)
	{
		waitForExistence(iWait);
		return SeleniumCore.getBrowser().isElementPresent(sLocator);
	}

	/**
	 * Waits for the calling element to appear on the page.
	 * @return true if the element is present, false otherwise
	 * @param iWait
	 */
	public boolean waitForExistence(int iWait) {

//		Platform.sleep(Log.giAutomationPause2TO);
		boolean isElementPresent = false;
		for(int iteration = 0; iteration < iWait; iteration++)
		{
			try{
				isElementPresent = SeleniumCore.getBrowser().isElementPresent(sLocator);
				if(isElementPresent)
				{
					break;
				}
			} catch (Exception e){}
			Platform.sleep(Log.giAutomationPause1TO);
		}

		return isElementPresent;
	}
	/**
	 * @method waitForVisibility(int iWait)
	 * Waits for the calling element to be visible on the page.
	 * @return true if the element is visible, false otherwise
	 * @param iWait
	 */
	public boolean waitForVisibility(int iWait) {

		Platform.sleep(Log.giAutomationPause1TO);
		boolean isVisible = false;
		for(int iteration = 0; iteration < iWait; iteration++)
		{
			try{
				isVisible = SeleniumCore.getBrowser().isVisible(sLocator);
				if(isVisible)
				{
					break;
				}
			} catch (Exception e){}
			Platform.sleep(Log.giAutomationPause1TO);
		}

		return isVisible;
	}

	/**
	 * Determines if the specified element is visible.
	 * This method will fail if the element is not present.
	 * @return true if the specified element is visible, false otherwise
	 */
	public boolean isVisible() {

		return SeleniumCore.getBrowser().isVisible(sLocator);
	}
	

	
	/**
	 * Logs whether element is visible or not depending on the bVisible option passed
	 * @Parameter: bVisible, the boolean value
	 * @return n/a
	 */
	public void verifyVisible(boolean bVisible) {
		if(bVisible){
			if (isVisible()){
			Log.logScriptInfo("Verify " + sIdentifier + " is Visible");
	
			} else	{
				Log.errorHandler(sIdentifier + " is NOT Visible");
			}
		} else {
			if (isVisible()){
				Log.errorHandler(sIdentifier + " is Visible");
				} 
			else{
				Log.logScriptInfo("Verify " + sIdentifier + " is NOT Visible");
				}
		}
	}
	
	
	/**
	 * Logs whether element is visible
	 * @return n/a
	 */
	public void verifyVisible() {
			if (isVisible()){
			Log.logScriptInfo("Verify " + sIdentifier + " is Visible");
	
			} else	{
				Log.errorHandler(sIdentifier + " is NOT Visible");
			}
		
	}
	
	
	/**
	 * Determines if the specified element is present.
	 * This method will fail if the element is not present.
	 * @return true if the specified element is present, false otherwise
	 */
	public boolean isElementPresent() {
		return SeleniumCore.getBrowser().isElementPresent(sLocator);

	}
	
	
	/**
	 * Simulates a user hovering a mouse over the specified element.
	 * @return n/a
	 */
	public void mouseOver() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
		SeleniumCore.getBrowser().mouseOver(sLocator);
		// mouseOver not working for most apps!  switching to focus() instead...
		//SeleniumCore.getBrowser().mouseOver(sLocator);

		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
	}

	
	
	/**
	 * Determines if the specified element is present.
	 * This method will fail if the element is not present.
	 * @return true if the specified element is present, false otherwise
	 */
	public boolean verifyElementIsPresent() {

		Boolean bIspresent = null;

		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Verified object " + sIdentifier + " " + sClassName + " is present");
			bIspresent= SeleniumCore.getBrowser().isElementPresent(sLocator);
		}
		else
		{
			bIspresent= false;
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
		}
		return bIspresent;

	}
	

	/**
	 * Sets text into a text field type widget
	 * @Parameter: String sText - value to type into the text field type widget
	 * @return n/a
	 * @param sText
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
	 * Returns specified Attriibute as String example use: String sHref = lnkMyObject().getAttribute("href");
	 * @param String sAttribute - the name of the object's attribute to return i.e. "href", "title", "id", "name"
	 * @return String - returns attribute of calling object as a String
	 */
	public String getAttribute(String sAttribute) {
		try{
		if (SeleniumCore.getBrowser().exists(sLocator)){
			return SeleniumCore.getBrowser().getAttribute(sLocator + "~" + sAttribute);
		}
		else
		{
			Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");
			return "";
		}
		}
		catch(Exception e)
		{
			//Log.errorHandler("Could not get " + "\"" + "@" + sAttribute + "\"" + " attribute for object " + sIdentifier);
			return ""; 
		}
	}
        
    /**
	 * Drag and Drop specified elements.
	 * @param sMyLocatorTo - locator to drop object into
	 */
	public void dragAndDrop(String sMyLocatorTo) {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Drag from " + sIdentifier + " to " + sMyLocatorTo);
			SeleniumCore.getBrowser().dragAndDrop(sLocator, sMyLocatorTo);
		}
	}
	
	
	/**
	 * Drag and Drop specified elements.
	 * @param objTo - object to drop object into
	 */
	public void dragAndDrop(WebWidget objTo) {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			Log.logScriptInfo("Drag from " + sIdentifier + " to " + objTo.sIdentifier);
			SeleniumCore.getBrowser().dragAndDrop(sLocator,objTo.sLocator);
		}
	}
	
	/**
	 * Counts how many nodes match the specified xpath
	 * @Return int - returns the number of nodes that match the specified xpath
     * used to count number of rows in table
	 */
	public int countXpaths() {
		return SeleniumCore.getBrowser().getXpathCount(sLocator).intValue();
	}
	
	
	/**
	 * @method:  isDisabled() 
	 * @description:  This method will return true if the object is disabled and false if it is not.
	 * @return:  boolean
	 */
	public boolean isDisabled(){
		boolean bIsDisabled = false;
		try{
		if (SeleniumCore.getBrowser().exists(sLocator)){
			String disabled = "";
			disabled = SeleniumCore.getBrowser().getAttribute(sLocator + "@disabled");
			if (disabled!=null){
				bIsDisabled = disabled.equals("true") || disabled.equals("");
			}
		}
		else
		{
			// More consistent to consider an unexisting item as disabled
            bIsDisabled = true;
			//Log.errorHandler("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"");

		}
		}
		catch(Exception e)
		{
            // As we are checking for null above don't know in which condition we might get here might be better to consider the item as disabled in this case
			bIsDisabled = false;
			//Log.errorHandler("Could not get " + "\"" + "@" + sAttribute + "\"" + " attribute for object " + sIdentifier);
		}		
		
		return bIsDisabled;
	}
	
	
	
	/**
	 * @method:  isEnabled()
	 * @description:  This method will return true if the object is enabled and false if it is not.
	 * @return:  boolean
	 */
	public boolean isEnabled(){
		return !isDisabled();
	}
	
	//***************************************************************************************************************************//
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
	
	
	/**
	 * @Function:scrollToBottom()
	 * @Description: This function scrolls the browser page vertically to the bottom of webpage
	 * @return void - no return value
	 */
	public static void scrollToBottom() {
		
		Log.logScriptInfo("Scroll Browser to the bottom of webpage");
		SeleniumCore.getBrowser().scrollToBottom();
	}
	
	
	/**
	 * @Function:scrollToTop()
	 * @Description: This function scrolls the browser page vertically to the top of webpage
	 * @return void - no return value
	 */
	public static void scrollToTop() {
		
		Log.logScriptInfo("Scroll Browser to the top of webpage");
		SeleniumCore.getBrowser().scrollToTop();
	}
	
	
	/**
	 * @Function:scrollingByCoordinatesofAPage(int x, int y)
	 * @Description: This function scrolls the browser page based on the co-ordinates specified
	 * @param x number of pixels to scroll by, along the x-axis (horizontal).
	 * 		 Positive values will scroll to the left, while negative values will scroll to the right 
	 * @param y number of pixels to scroll by, along the y-axis (vertical).
	 * 		 Positive values will scroll down, while negative values scroll up
	 */
	public static void scrollingByCoordinatesofAPage(int x, int y) {
		
		Log.logScriptInfo("Scroll Browser Horizontally by "+ x + "pixels and Vertically by " + y +" pixels");
		SeleniumCore.getBrowser().scrollingByCoordinatesofAPage(x, y);
	}	
	
	
	
	
}