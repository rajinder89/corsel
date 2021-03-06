package dummy.core.libs;

import java.util.Iterator;
import java.util.List;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


/**
 * Browser Class
 * The Browser class contains general Web Browser functions
 * @author 
 */
public class Browser {

	/**
	 * Launches the default browser
	 * @param n/a
	 * @return n/a
	 */
	public static void start() {
		SeleniumCore.getBrowser().startBrowser();
		Platform.sleep(Log.giAutomationShortTO);

		ATUReports.add("Start Browser \"" + SeleniumCore.getBrowser().getDisplayName() + "\" Version \"" + SeleniumCore.getBrowser().getDisplayVersion() + "\" using WEBDRIVER", LogAs.PASSED, null);
	}

	/**
	 * Loads the specified page/URL in browser
	 * @Parameter: String urlToLoad - URL to load in the current browser
	 * @return n/a
	 * @param urlToLoad
	 */
	public static void loadURL(String urlToLoad) {

		ATUReports.add("Open page: " + "\"" + urlToLoad + "\"", LogAs.PASSED, null);
		SeleniumCore.getBrowser().loadURL(urlToLoad);
	}

	/**
	 * Loads specified page/URL in browser within a specifed time
	 * @Parameter: String urlToLoad - URL to load in the current browser
	 * @param int iWait - time in seconds to wait for page to load
	 * @return n/a
	 * @param urlToLoad
	 */
	public static void loadURL(String urlToLoad, int iWait) {
	
		ATUReports.add("Open page: " + "\"" + urlToLoad + "\"", LogAs.PASSED, null);
		SeleniumCore.getBrowser().loadURL(urlToLoad,iWait);
	}


	/**
	 * Closes the current browser window
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static void close() {
	
		ATUReports.add("Close browser", LogAs.PASSED, null);
		SeleniumCore.getBrowser().close();
	}


	/**
	 * Makes the current browser go back using the "back" button
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static void goBack() {
	
		ATUReports.add("Click Browser Back button", LogAs.PASSED, null);
		SeleniumCore.getBrowser().goBack();
	}

	/**
	 * Maximizes the current browser window - may not work with all browsers
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static void maximize() {
		SeleniumCore.getBrowser().windowMaximize();
	}

	/**
	 * Sets the current browser window to have focus
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static void focus()
	{
		SeleniumCore.getBrowser().windowFocus();
	}


	/**
	 * Checks if link exists on page
	 * @Parameter: sLinkCaption - The text caption of the link you want to check the existence of
	 * @return true if link exists, false is link does not exist
	 * @param sLinkCaption
	 */
	public static boolean linkExists(String sLinkCaption)
	{
		if (SeleniumCore.getBrowser().isElementPresent("link=" + sLinkCaption)){
			return true;
		}else
		{
			return false;
		}
	}


	/**
	 * Checks if link exists on page
	 * @Parameter: sLinkCaption - The text caption of the link you want to check the existence of
	 * @return true if link exists, false is link does not exist
	 * @param sLinkCaption
	 * @param : iWait time out for that link to appear
	 */
	public static boolean linkExists(String sLinkCaption, int iWait)
	{

		if(!SeleniumCore.getBrowser().isElementPresent("link=" + sLinkCaption)){
			Platform.sleep(iWait);
		}
		if (SeleniumCore.getBrowser().isElementPresent("link=" + sLinkCaption)){
			return true;
		}else {
			return false;
		}
	}


	/**
	 * Checks if specified text content exists on page
	 * @Parameter: sTextContent - The text content you want to check the existence of
	 * @return true if text exists, false if text does not exist
	 * @param sTextContent
	 */
	public static boolean textExists(String sTextContent)
	{
		int iSecond=0;
		while(!SeleniumCore.getBrowser().isTextPresent(sTextContent)){
			Platform.sleep(Log.giAutomationPause2TO);
			if(iSecond++ > 10) break; // do not wait more than 30 seconds
		}
		if (SeleniumCore.getBrowser().isTextPresent(sTextContent)){
			return true;
		}else
		{
			return false;
		}
	}

	/**
	 * checks if specified text content exists on page within a specified time
	 * @Parameter: sTextContent - The text content you want to check the existence of
	 * @return true if text exists, false if text does not exist
	 */
	public static boolean textExists(String sTextContent, int iWait)
	{
		int iSecond=0;
		while(!SeleniumCore.getBrowser().isTextPresent(sTextContent)){
			Platform.sleep(Log.giAutomationPause2TO);
			if(iSecond++ > iWait) break; 
		}
		if (SeleniumCore.getBrowser().isTextPresent(sTextContent)){
			return true;
		}else
		{
			return false;
		}
	}
	/**
	 * Checks and logs if specified text content exists on page within a specified time
	 * @Parameter: sTextContent - The text content you want to check and log the existence of
	 * @return boolean - true if text is present on UI, false if text is not present or exception occurs.
	 */
	public static boolean validateTextExists(String sTextContent, int iWait)
	{
		try{

			if(textExists(sTextContent,iWait)){
				ATUReports.add(sTextContent +" is present", LogAs.PASSED,null);
				return true;
			
			} else {
				
				ATUReports.add(sTextContent +" does not exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				return false;
			}
		} catch (Exception e) {

			ATUReports.add("Error validating Text"+sTextContent, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		
			return false;
		}

	}

	/**
	 * Checks and logs if specified text content exists on page within a specified time
	 * @Parameter: sTextContent - The text content you want to check and log the existence of
	 * @return void
	 * @param iWait
	 * @param bExpected
	 */
	public static void validateTextExists(String sTextContent, int iWait, boolean bExpected)
	{
		try{
			if(bExpected){
				if(textExists(sTextContent,iWait)){
					
					ATUReports.add(sTextContent + " is present, as expected.", LogAs.PASSED, null);
				} else {
					
					ATUReports.add(sTextContent + " does not exist.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					
				}
			} else {
				if(!textExists(sTextContent,iWait)){
					ATUReports.add(sTextContent +" is not present, as expected.", LogAs.PASSED, null);
				} else {
					ATUReports.add(sTextContent + " is present in error.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
				}
			}
		} catch (Exception e) {
			
			ATUReports.add("Error validating Text "+sTextContent, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			String sException = e.getMessage();
			ATUReports.add(sException, LogAs.FAILED, null);
		}

	}

	/**
	 * @Description: Checks if specified text content exists or does not exist on page within a specified time, 
	 * and logs the result.

	 * @Parameter: lsTextContent - The list containing text content you want to validate the existence of, and log result
	 * List may include 1 or more values, for example ("Customer Added."), ("Account is required.", "Date is required.")
	 * @Param: iWait - Wait Time for validating the text
	 * @Param: bExpected - pass true if text is expected to be present on UI; 
	 * pass false if text is not expected to be present on UI
	 * @Return: void  
	 */
	public static void validateTextExists(List<String> lsTextContent, int iWait, boolean bExpected)
	{
		try{
			if (lsTextContent.isEmpty())
				throw new Exception ("The list passed into validateTextExists(String, int, boolean) is empty.");

			Iterator<String> iterator = lsTextContent.iterator();
			while (iterator.hasNext()) {
				validateTextExists(iterator.next(), iWait, bExpected);
			}

		} catch (Exception e) {

			ATUReports.add("Error validating Text in validateTextExists(String, int, boolean): ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			String sException = e.getMessage();
			ATUReports.add(sException, LogAs.FAILED, null);
		}

	}

	/**
	 * Checks and logs if specified link content exists on page within a specified time
	 * @Parameter: sLinkCaption - The link you want to check and log the existence of
	 * @return void
	 * @param sLinkCaption
	 * @param iWait
	 * @param bExists
	 */
	public static void validateLinkExists(String sLinkCaption, int iWait,boolean bExists)
	{
		try{
			if(bExists){
				if(linkExists(sLinkCaption,iWait)){
					ATUReports.add("Link "+sLinkCaption +" is present", LogAs.PASSED, null);
				} else {

					ATUReports.add("Link "+sLinkCaption + " does not exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					
				}
			} else {
				if(!linkExists(sLinkCaption,iWait)){
					ATUReports.add("Link "+sLinkCaption +" is not present", LogAs.PASSED, null);
				} else {

					ATUReports.add("Link "+sLinkCaption + " does not exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					
				}
			}
		} catch (Exception e) {

			String sException = e.getMessage();
			ATUReports.add(sException, LogAs.FAILED, null);

		}

	}
	
	/**
	 * Checks and logs if specified link content exists on page within a specified time
	 * @Parameter: sLinkCaption - The link you want to check and log the existence of
	 * @return void
	 * @param sLinkCaption
	 */
	public static void validateLinkExists(String sLinkCaption, int iWait)
	{
		try{

			if(linkExists(sLinkCaption,iWait)){
					ATUReports.add("Link "+sLinkCaption +" is present", LogAs.PASSED, null);
			} else {

				ATUReports.add("Link "+sLinkCaption + " does not exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} catch (Exception e) {

			String sException = e.getMessage();
			ATUReports.add(sException, LogAs.FAILED, null);
		}

	}


	/**
	 * Handles Message boxes
	 * @Parameter: sCaption - The text caption within the message box
	 * @Parameter: sAction - The action or button to click in the message box. i.e. "OK", "Cancel", "Yes", "No", etc.
	 * @return true if message box exists, false is message box does not exist
	 * @param sCaption
	 * @param sAction
	 */
	public static boolean handleMessageBox(String sCaption,String sAction)
	{

		try{

			if (sAction.equalsIgnoreCase("OK")||sAction.equalsIgnoreCase("YES") || sAction.equalsIgnoreCase("SAVE") || sAction.equalsIgnoreCase("OPEN")){
				SeleniumCore.getBrowser().chooseOkOnNextConfirmation();
				return true;
			}
			else{
				SeleniumCore.getBrowser().chooseCancelOnNextConfirmation();
				return true;
			}

		}
		catch(Exception e){
			return false;
		}


	}


	/**
	 * Selects a frame within the current window. (You may invoke this command
	 * multiple times to select nested frames.) To select the parent frame, use
	 * "relative=parent" as a locator; to select the top frame, use
	 * "relative=top". You can also select a frame by its 0-based index number;
	 * select the first frame with "index=0", or the third frame with "index=2".
	 * You may also use a DOM expression to identify the frame you want
	 * directly, like this: dom=frames["main"].frames["subframe"]
	 * @Parameter: myLocator, an element locator identifying a frame or iframe
	 * @return n/a
	 * @param myLocator
	 */
	public static void  selectFrame(String myLocator) {
		SeleniumCore.getBrowser().selectFrame(myLocator);
	}



	/**
	 * Gets all window ids
	 * @Parameter: n/a
	 * @return String[] - returns ids for all windows
	 */
	public static String[] getAllWindowIds() {
		return SeleniumCore.getBrowser().getAllWindowIds();
	}


	/**
	 * Gets all the window names
	 * @Parameter: n/a
	 * @return String[] - returns all window names
	 */
	public static String[] getAllWindowNames() {
		return SeleniumCore.getBrowser().getAllWindowNames();
	}



	/**
	 * Selects  the new window
	 * @param String windowID - ID of window to select
	 * @return n/a
	 */
	public static void  selectWindow(String windowID) {
		SeleniumCore.getBrowser().selectWindow(windowID);
	}

	/**
	 * Selects the specified pop up window
	 * @param String windowID
	 * @param Strin
	 * @return n/a
	 */
	public static void waitForPopUp(String windowID, String timeout) {
		SeleniumCore.getBrowser().waitForPopUp(windowID, timeout);
	}

	/**
	 * Waits for the current page to finish loading
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static boolean waitForFrame(String frameName)
	{
		return SeleniumCore.getBrowser().waitForFrame(frameName);
	}

	/**
	 *Get get All Window Titles
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static  String[] getAllWindowTitles()
	{
		return SeleniumCore.getBrowser().getAllWindowTitles();
	}

	 /**
	 * Get Alerts
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static  String getAlert()
	{
		return SeleniumCore.getBrowser().getAlert();
	}


	 /**
	 * Refresh the page
	 * @Parameter: n/a
	 */
	public static  void refresh()
	{
		SeleniumCore.getBrowser().refresh();
	}
	
	
	/**
	 * Refresh the page
	 * @Parameter: n/a
	 */
	public static  void navigateTo(String sURL)
	{
		SeleniumCore.getBrowser().navigateTo(sURL);
		ATUReports.add("Navigating to URL: " + sURL + " ", LogAs.PASSED,null);
	}



	/**
	 * Delete cookie
	 * @Parameter: n/a
	 */
	public static  void deleteCookie(String arg0, String arg1)
	{
		SeleniumCore.getBrowser().deleteCookie(arg0, arg1);
	}



	/**
	 * Delete All Visible Cookies
	 * @Parameter: n/a
	 */
	public static  void deleteAllVisibleCookies()
	{
		SeleniumCore.getBrowser().deleteAllVisibleCookies();
	}



	/**
	 * Get cookie
	 * @Parameter: n/a
	 */
	public static  String  getCookie()
	{
		return SeleniumCore.getBrowser().getCookie();
	}

	/**
	 *Get Confirmation
	 * @Parameter: n/a
	 * @return n/a
	 */
	public static  String getConfirmation()
	{
		return SeleniumCore.getBrowser().getConfirmation();
	}

	/**
	 *Choose OK on Next Confirmation
	 *@Parameter: n/a
	 */
	public static void chooseOkOnNextConfirmation()
	{
		SeleniumCore.getBrowser().chooseOkOnNextConfirmation();
	}


	/**
	 *Choose Cancel on Next Confirmation
	 *@Parameter: n/a
	 */
	public static void chooseCancelOnNextConfirmation()
	{
		SeleniumCore.getBrowser().chooseCancelOnNextConfirmation();
	}



	/**
	 * Gets entire text content of active web page and returns as string
	 * @return String - returns all web page text content as string
	 */
	public static String getPageText() {
		return SeleniumCore.getBrowser().getBodyText();

	}

	/**
	 * Clicks on dynamically generated object based on specified caption
	 * @return n/a
	 * @param sCaption - caption of a dynamically created object
	 */
	public static void click(String sCaption) {
		String ObjToClick = sCaption;
		try {
			try {
					ObjToClick = "link="+ObjToClick;
					SeleniumCore.getBrowser().click(ObjToClick);
				}
			catch (Exception e0){
				try{
					if (!sCaption.contains("[contains")) {
						ObjToClick = "//a[contains(text() '" + sCaption + "')]";
					}
					SeleniumCore.getBrowser().click(ObjToClick);
				} catch (Exception e1) {
					// if the first try doesn't work, go for an exact match...
					try {
						SeleniumCore.getBrowser().click("//a[text()='" + sCaption + "']");

					} catch (Exception e2) {
						try {
							// if all else fails, just click the string...
							SeleniumCore.getBrowser().click(sCaption);
						} catch (Exception e3) {
														
							ATUReports.add("Error occurred locating dynamic object: " + "\"" + sCaption + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
							
							String sException = e3.getMessage();
							ATUReports.add(sException, LogAs.FAILED, null);
							return;
						}
					}
				}
			}		
		}
		catch(Exception e4){}

		ATUReports.add("Click " + "\"" + sCaption + "\"" + " object", LogAs.PASSED,null);

	}


	/**
	 * Dynamically set text of specified Html Text Field TestObject <p>
	 * @param 	String sTF - html TextField TestObject identifier
	 * @param 	String s - text to enter into text field.
	 * @return
	 */
	public static void setText(String sTF, String sText)
	{
		try {
			ATUReports.add("Enter Text: " + "\"" + sText + "\"" + " into " + sTF + " TextField", LogAs.PASSED,null);
			SeleniumCore.getBrowser().setText(sTF, sText);
		}
		catch (Exception e) {
						
			ATUReports.add("Error occurred locating dynamic TextField: " + "\"" + sTF + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			String sException = e.getMessage();
			ATUReports.add(sException, LogAs.FAILED, null);
		}
	}

	/**
	 * Waits for browser to be in ready state <p>
	 * @param int iWait - max amount of time, in seconds, to wait for ready state
	 * @return boolean true if browser is ready and false if it times out waiting for ready state
	 */
	public static void waitForReady(int iWait){

		for (int x = 0; x< iWait;x++){	 
			try{
				Platform.sleep(Log.giAutomationPause1TO);
				if (SeleniumCore.getBrowser().isReady()){
					return;
				}
			}
			catch(Exception e){	
			}
		}
	}	

	/**
	 * Waits up to 60 seconds (default) for browser to be ready 
	 */
	public static void waitForReady(){
		waitForReady(Log.giAutomationLongTO);
	}


	/**
	 * Waits for browser to be in ready state
	 * @param int iWait - max amount of time, in seconds, to wait for ready state
	 * @param String sDesc - log description info for wait
	 * @return boolean true if browser is ready and false if it times out waiting for ready state
	 */
	public static void waitForReady(int iWait,String sDesc){
		waitForReady(iWait);
		ATUReports.add(sDesc, LogAs.PASSED, null);
	}
	
	/**
	 * @Function: switchToFrame() 
	 * @Description: This function switch control to overlay frame for Webdriver
	 * @return void - no return value
	 */
	public static void switchToFrame(String sOverlayContainerFrame) throws Exception {		

		Platform.sleep(Log.giAutomationPause2TO);

		SeleniumCore.driver.switchTo().frame(sOverlayContainerFrame);
		
		Platform.sleep(Log.giAutomationShortTO);

	}
	/**
	 * @Function: switchToDefaultContent() 
	 * @Description: This function switch control to Webpage form overlay frame for Webdriver
	 * @return void - no return value
	 */
	public static void switchToDefaultContent() throws Exception {		

		Platform.sleep(Log.giAutomationPause2TO);	

		SeleniumCore.driver.switchTo().defaultContent();
		
		Platform.sleep(Log.giAutomationPause2TO);

	}
	
	
	/**
	 * @Function:scrollToBottom()
	 * @Description: This function scrolls the browser page vertically to the bottom of webpage
	 * @return void - no return value
	 */
	public static void scrollToBottom() {
		
	//	Log.logScriptInfo("Scroll Browser to the bottom of webpage");
		ATUReports.add("Scroll Browser to the bottom of webpage", LogAs.PASSED, null);
		SeleniumCore.getBrowser().scrollToBottom();
	}
	
	
	/**
	 * @Function:scrollToTop()
	 * @Description: This function scrolls the browser page vertically to the top of webpage
	 * @return void - no return value
	 */
	public static void scrollToTop() {
		
	//	Log.logScriptInfo("Scroll Browser to the top of webpage");
		ATUReports.add("Scroll Browser to the top of webpage", LogAs.PASSED, null);
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
		
	//	Log.logScriptInfo("Scroll Browser Horizontally by "+ x + "pixels and Vertically by " + y +" pixels");
		ATUReports.add("Scroll Browser Horizontally by "+ x + "pixels and Vertically by " + y +" pixels", LogAs.PASSED, null);
		SeleniumCore.getBrowser().scrollingByCoordinatesofAPage(x, y);
	}
	
	
	
	
	
	
	
	

}
