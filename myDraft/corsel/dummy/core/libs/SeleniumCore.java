package dummy.core.libs;

import java.util.List;
import java.util.Set;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import dummy.core.webwidgets.WebWidget;


/**
 * The SeleniumCore class contains core selenium commands and functions
 * that are wrapped in other core library classes and should be called from other core libraries.
 */
public class SeleniumCore {


	public static WebDriver driver;
	
	private static String defaultBrowser = "*firefox";
	private static String browser_safari = "*safari";
	private static String browser_firefox = "*firefox";
	private static String browser_ie = "*iexplore";
	private static String browser_chrome = "*googlechrome";
	private static String currentBrowser = "";
	private static String userAgent = "";
	

	/**
	 * returns the current browser name
	 * @return String Current Browser Name
	 */
	public String getCurrentBrowserName()
	{
		return currentBrowser;
	}

	/**
	 * sets the browser used by Automation
	 * @param String browserName browser name to be used
	 */
	public static void setCurrentBrowser(String browserName)
	{
		if(browserName.toLowerCase().contains("safari")){currentBrowser = browser_safari;}
		if(browserName.toLowerCase().contains("explorer")){currentBrowser = browser_ie;}
		if(browserName.toLowerCase().contains("firefox")){currentBrowser = browser_firefox;}
		if(browserName.toLowerCase().contains("chrome")){currentBrowser = browser_chrome;}
		defaultBrowser = currentBrowser;
	}




	/**
	 * static method used to return an instance of the seleniumHelper class
	 * @return seleniumHelper instance
	 */
	public static seleniumHelper getBrowser()
	{	
		return new seleniumHelper();
	}

	/**
	 * wraps many Selenium methods with logging<p>
	 * Please use the getBrowser() instance...<p>
	 */
	public static class seleniumHelper{
		
	
		/**
		 * The following group of methods are browser information methods
		 */


		/**
		 * returns a text name for the current browser
		 * @return String Browser Display Name
		 */
		public String getDisplayName()
		{
			String myBrowserName = "";
			if(currentBrowser.equals(browser_ie)){myBrowserName = "Internet Explorer";}
			if(currentBrowser.equals(browser_safari)){myBrowserName = "Safari";}
			if(currentBrowser.equals(browser_firefox)){myBrowserName = "Firefox";}
			if(currentBrowser.equals(browser_chrome)){myBrowserName = "Google Chrome";}
			return myBrowserName;
		}

		/**
		 * returns version number for the current browser as string
		 * @return String Browser version name
		 */
		public String getDisplayVersion()
		{
			String browserVersion = "Unknown";
			
			try{
				if(currentBrowser.equals(browser_ie)){browserVersion = userAgent.split("MSIE ")[1].split("; Windows")[0];}
				if(currentBrowser.equals(browser_safari)){browserVersion = userAgent.split("Safari/")[1].split(" Safari")[0];}
				if(currentBrowser.equals(browser_firefox)){browserVersion = userAgent.split("Firefox/")[1];}
				if(currentBrowser.equals(browser_chrome)){browserVersion = userAgent.split("Chrome/")[1].split(" Safari")[0];}
			} catch (Exception e){}
			
			return browserVersion;
		}

		
		//******************************************************************************
		//The following methods are browser control methods start / shutdown / loadURL
		//******************************************************************************
		 

		/**
		 * launches the browser passed in as an argument, starts the Selenium server if needed
		 * @param String browserName browser to start
		 */
		public void start(String browserName)
		{			
			if(browserName.toLowerCase().equals("default"))
			{
				if(Platform.isWindows())
				{
					browserName = Platform.gsMozillaFirefox;
				}
		}


			SeleniumCore.setCurrentBrowser(browserName);
			
			
			if(browserName.indexOf("chrome") != -1)
				{
					ChromeOptions options = new ChromeOptions();
					options.addArguments("silent=true");
					options.addArguments("verbose=false");
					options.addArguments("start-maximized");
					System.setProperty("webdriver.chrome.driver", Log.gsAutomationAutoPath + "chromedriver.exe");
					driver = new ChromeDriver(options);
					
				} 
				else if(browserName.indexOf("ie") != -1){
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
					System.setProperty("webdriver.ie.driver", Log.gsAutomationAutoPath + "IEDriverServer.exe");
					driver = new InternetExplorerDriver(capabilities);

				
				}
				else if (browserName.contains("safari")) {
					driver = new SafariDriver();
				}
	
				else {//firefox is default
					try
					{
						if(Log.gsAutomationBrowserProfile != null)
						{
							FirefoxProfile profile = new ProfilesIni().getProfile(Log.gsAutomationBrowserProfile);
							driver = new FirefoxDriver(profile);
							Log.logScriptInfo("The Firefox Browser has been configured using the following user profile file: " + profile);
						}
						else
						{
							driver = new FirefoxDriver();
						}
					}catch (Exception e) {
						Log.errorHandler("Error loading browser profile file found in gsAutomationBrowserProfile property in the Automation.properties file", e);
						driver = new FirefoxDriver();
					}
					

				}
				
				
				driver.manage().window().maximize();
				

				userAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
			
				
			}
			
	
		/**
		 * launches the default browser, starts the Selenium server if needed
		 */
		public void startBrowser()
		{
			start(defaultBrowser);
		}
		
		
		/**
		 * launches specified page in browser and waits 60 seconds for page to load
		 * @param String urlToLoad URL to load in the current browser
		 */
		public void loadURL(String urlToLoad)
		{
			loadURL(urlToLoad,Log.giAutomationLongTO);
		}
		

		/**
		 * launches specified page in browser
		 * @param String urlToLoad URL to load in the current browser
		 * @param int iWait time to wait for page to load in seconds
		 */
		public void loadURL(String urlToLoad, int iWait)
		{
		try {	
			
			int nCount = 0;
			
			driver.get(urlToLoad);
			Platform.sleep(iWait);


			Platform.sleep(Log.giAutomationPause2TO);

			while ((driver.getTitle().isEmpty()) && (nCount < 10)) {
				nCount++;
				Platform.sleep(Log.giAutomationPause2TO);
			}

		}
		catch (Exception e2) {
				Log.errorHandler("Error Loading URL: " + urlToLoad, e2);
		}
	}

		/**
		 * closes the current browser window
		 */
		public void close() {
			
			try{
			
				//driver.close();
				driver.quit();
			
			}
			catch(Exception e){}
		}

		/**
		 * Makes the current browser go back using the "back" button
		 */
		public void goBack() {
		
				driver.navigate().back();	
			}
			


		/**
		 * maximizes the current browser window - may not work with all browsers
		 */
		public void windowMaximize() {
		
			driver.manage().window().maximize();	
		}

		/**
		 * Sets the current browser window to have focus
		 */
		public void windowFocus()
		{
	
				((JavascriptExecutor) driver).executeScript("window.focus;");	
		
		}


		//**********************************************************************
		//The following methods are browser action methods - click / type / read
		//**********************************************************************


		/**
		 * sets text into a text field type widget
		 * @param String sLocator string used to locate the widget
		 * @param String value value to type into the text field type widget
		 */
		public void type(String sLocator, String value)
		{
	
				driver.findElement(by(sLocator)).clear();
				driver.findElement(by(sLocator)).sendKeys(value);
		
		}


		/**
		 * wrapper for the type method
		 * @param String sLocator string used to locate the widget
		 * @param String value value to type into the text field type widget
		 */
		public void setText(String sLocator, String value)
		{
				driver.findElement(by(sLocator)).clear();
				driver.findElement(by(sLocator)).sendKeys(value);
		}

		
		/**
		 * wrapper for the sendKeys method. Mimics typing key into calling object
		 * @param String sLocator string used to locate the widget
		 * @param Keys key to type into calling object widget
		 */
		public void sendKeys(String sLocator, Keys sKey)
		{
		
				driver.findElement(by(sLocator)).sendKeys(sKey);
		
		}
		

		/**
		 * convertLocatorToBy(String sLocator,boolean bExactMatch)
		 * returns a WebDriver By datatype from a given string locator value
		 * <p>
		 * @param String sLocator - Selenium RC locator to be converted to WebDriver By locator type
		 * @param boolean bExactMatch - true is locator must be exact match
		 */
		public By by(String sLocator,boolean bExactMatch)
		{
		@SuppressWarnings("unused")
		By by;	
		String sName 	= "name=";
		String sId 		= "id=";
		String sLink 	= "link=";
		String sCss 	= "css=";
		String sXpath	= "xpath=";
		
		if (sLocator.startsWith(sName)){
			//System.out.println(sLocator.substring(sName.length()));
			return by = By.name(sLocator.substring(sName.length())); 
		}
		else if (sLocator.startsWith(sId)){
			//System.out.println(sLocator.substring(sId.length()));
			return by = By.id(sLocator.substring(sId.length())); 
		}
		else if (sLocator.startsWith(sCss)){
			//System.out.println(sLocator.substring(sCss.length()));
			return by = By.cssSelector(sLocator.substring(sCss.length())); 
		}
		else if (sLocator.startsWith(sLink)){
			if (bExactMatch)
				return by = By.linkText(sLocator.substring(sLink.length()));
			else
				return by = By.partialLinkText(sLocator.substring(sLink.length()));
		}
		else if (sLocator.startsWith(sXpath)){
			//System.out.println(sLocator.substring(sXpath.length()));
			return by = By.xpath(sLocator.substring(sXpath.length())); 
		}
		else{
			//System.out.println(sLocator);
			return by = By.xpath(sLocator);
		}
			
		}
		
		
		/**
		 * by(String sLocator,boolean bExactMatch)
		 * returns a WebDriver By datatype from a given string locator partial value
		 * @param String sLocator - WebDriver By locator type
		 */
		public By by(String sLocator)
		{
		return by(sLocator,WebWidget.bExactMatch);
		}
		
		/**
		 * clicks a widget found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 */
		public void click(String sLocator)
		{
			
				driver.findElement(by(sLocator)).click();
		}
		
		
		/**
		 * clicks a widget found using a sLocator string that is an exact match
		 * @param String sLocator - exact match string used to locate the widget
		 */
		public void clickExactMatch(String sLocator)
		{
			driver.findElement(by(sLocator, true)).click();
		}
		
		

		/**
		 * Selects an item using the text value in a listbox found using the sLocator string passed in
		 * @param String sLocator string used to locate the widget
		 * @param String sText item to select in the listbox by the text of the item
		 */
		public void selectByText(String sLocator, String sText)
		{
			WebElement wListbox = driver.findElement(by(sLocator));
			new Select(wListbox).selectByVisibleText(sText);
		}
		
		
		
		/**
		 * Selects an item in a listbox found using the sLocator and Label string values (Option visible string
		 * value). 
		 * <p>
		 * @param sLocator string used to locate the widget
		 * @param sLabel item to select in the list box
		 * @throws Exception if listbox widget is not found 
		 */
		public void selectByLabel(String sLocator, String sLabel) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select select = new Select(weListbox);
				List<WebElement> options = select.getOptions();
				boolean bFound = false;

				for (WebElement option : options) {
					if (option.getText().equals(sLabel)) {
						bFound = true;
						option.click();
						break;
					}
				}

				if (!bFound) {
					throw new Exception("Option not found");
				}
			}
		
		
		/**
		 * Selects an item in a listbox found using the sLocator and the partial text of an option in a list.
		 * <p>
		 * @param sLocator string used to locate the widget
		 * @param sLabel item to select in the list box
		 * @throws Exception if listbox is not found
		 */
		public void selectByPartialLabel(String sLocator, String sLabel) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select select = new Select(weListbox);
				List<WebElement> options = select.getOptions();
				boolean bFound = false;

				for (WebElement option : options) {
					if (option.getText().contains(sLabel.trim())) {
						bFound = true;
						option.click();
						break;
					}
				}

				if (!bFound) {
					throw new Exception("Option not found");
				}
			}
			
	
		
		
		

		/**
		 * Selects an item in a listbox found using the sLocator and Index value
		 * Note: not available for Selenium RC version 1
		 * @param sLocator string used to locate the widget
		 * @param iIndex Index item to select in the list box
		 * @throws Exception if listbox is not found
		 */
		public void selectByIndex(String sLocator, int iIndex)
		{ 
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select select = new Select(weListbox);
				select.selectByIndex(iIndex);
				
		}
		
		
		
		/**
		 * Clicks on a link, button, check box or radio button at a specific point
		 * <p>
		 * @param sLocator0 an element locator
		 * @param sLocator1 specifies the x,y position (i.e. - 10,20) of the mouse event
		 * relative to the element returned by the locator.
		 */
		public void clickAt(String sLocator0, String sLocator1) {
			
				Actions builder = new Actions(driver);
				WebElement element = driver.findElement(by(sLocator0));
				builder.moveToElement(element).click().build().perform();
				//driver.findElement(convertLocatorToBy(sLocator0)).click();
			
		}

		/**
		 * clicks the right mouse button on the widget found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 */
		public void clickMouseButton(String sLocator)
		{
			
				Actions builder = new Actions(driver);
				WebElement element = driver.findElement(by(sLocator));
				builder.contextClick(element).click().build().perform();
			
		}

		
		/**
		 * clicks the right mouse button on the widget found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 */
		public void rightClick(String sLocator)
		{
		
				Actions action = new Actions(driver);
				action.contextClick(driver.findElement(by(sLocator))).perform();			
	
		}

		/**
		 * Double clicks the right mouse button on the widget found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 */
		public void doubleClick(String sLocator) {
		
			Actions action = new Actions(driver);
			action.doubleClick(driver.findElement(by(sLocator))).perform();			
		}

		/**
		 * Simulates a user hovering a mouse over the specified element.
		 * @param sLocator, element locator
		 */
		public void hover(String sLocator) {

				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(by(sLocator))).perform();
			}	
		
		
		
		/**
		 * Simulates a user hovering a mouse over the specified element.
		 * @param sLocator, element locator
		 */
		public void mouseOver(String sLocator) {

	
				Actions action = new Actions(driver);
//				action.moveToElement(driver.findElement(convertLocatorToBy(sLocator))).perform();
				action.moveToElement(driver.findElement(by(sLocator))).build().perform();
				Platform.sleep(Log.giAutomationPause2TO);
				Platform.sleep(Log.giAutomationPause2TO);
			
		}

		/**
		 * Move the focus to the specified element; for example,
		 * if the element is an input field, move the cursor to that field.
		 * @param sLocator, element locator
		 */
		public void focus(String sLocator) {
			
				Actions action = new Actions(driver);
				action.moveToElement(driver.findElement(by(sLocator))).perform();
			
		}

//		/**
//		 * Simulates a user pressing and releasing a key.
//		 * @param sLocator, element locator
//		 * @param keySequence, Either be a string("\" followed by the numeric keycode
//		 * 						of the key to be pressed, normally the ASCII value of that key),
//		 * 						or a single character. For example: "w", "\119".
//		 */
//		public void keyPress(String sLocator, String keySequence) {
//			
//			
//				Actions action = new Actions(driver);
//				action.sendKeys(driver.findElement(by(sLocator)),keySequence).perform();
//			
//		}
		
//		
//		/**
//		 * Simulates a user pressing and releasing a key in the active window.
//		 * @param arg0 - key
//		 */
//		public void keyPressNative(String arg0) {
//			
//				Platform.sendKeyPress(Integer.valueOf(arg0) );
//			
//		}


		
		/**
		 * Simulates a user pressing a key (without releasing it yet).
		 * @param sLocator, element locator
		 * @param keySequence,  Either be a string("\" followed by the numeric keycode
		 * 						 of the key to be pressed, normally the ASCII value of that key),
		 * 						or a single character. For example: "w", "\119".
		 */
		public void keyDown(String sLocator, String keySequence) {
			
				Actions action = new Actions(driver);
				action.sendKeys(driver.findElement(by(sLocator)),keySequence).perform();
			
		}

		/**
		 * Simulates a user releasing a key.
		 * @param sLocator, element locator
		 * @param keySequence, Either be a string("\" followed by the numeric keycode
		 * 						of the key to be pressed, normally the ASCII value of that key),
		 * 						or a single character. For example: "w", "\119".
		 */
		public void keyUp(String sLocator, String keySequence) {
						
				Actions action = new Actions(driver);
				action.sendKeys(driver.findElement(by(sLocator)),keySequence).perform();
		}


		/**
		 * Submit the specified form. This is particularly useful for forms without
		 * submit buttons, e.g. single-input "Search" forms.
		 * @param sLocator, an element locator for the form you want to submit
		 */
		public void submit(String sLocator) {

				driver.findElement(by(sLocator)).submit();
	
		}

		/**
		 * Checks a checkbox found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 */
		public void check(String sLocator)
		{
			
				WebElement element = driver.findElement(by(sLocator));
				if (!element.isSelected()) {
					element.click();
				}
			
		
		}

		/**
		 * Unchecks a checkbox found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 */
		public void uncheck(String sLocator)
		{
			
				WebElement element = driver.findElement(by(sLocator));
				if (element.isSelected()) {
					element.click();
				}
	
			
		}

		/**
		 * Gets whether a toggle-button (checkbox/radio) is checked. Fails if the
		 * specified element doesn't exist or isn't a toggle-button.
		 * @param sLocator, an element locator pointing to a checkbox or radio button
		 * @return true if the checkbox is checked, false otherwise
		 */
		public boolean ischecked(String sLocator) {
		
				return driver.findElement(by(sLocator)).isSelected(); 
		
		}

		
		
		//**********************************************************
		// validation methods - exists / ready / etc
		//**********************************************************
		
		
		/**
		 * Returns true/false if a widget is found using the sLocator string passed in
		 * @param String sLocator - string used to locate the widget
		 * @return true is object exists
		 */
		public boolean exists(String sLocator)
		{
			waitForElementPresent(Log.giAutomationNormalTO,sLocator);
			return isElementPresent(sLocator);
		}


		/**
		 * Returns true/false if the current browser's ready state is "complete"
		 * <p>
		 * may not work with certain objects
		 * may not work as expected on Google Chrome
		 * @return boolean true/false if page is ready
		 */
		public boolean isReady()
		{
			try {
				
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				
			} catch (Exception e) {
				//System.out.println("window.document.readyState=" +browser.getEval("window.document.readyState"));
				return false;
			}
		}

		/**
		 * Waits for the current page frame to finish loading<p>
		 * may not work with certain objects
		 * @param frameName the name of the frame
		 * @return boolean true/false if page is ready (beyond the timeout)
		 */
		public boolean waitForFrame(String frameName)
		{
			boolean foundFrame = false;

			for (int i = 0; i < Log.giAutomationLongTO; i++) {
				try {
					
						driver.switchTo().frame(frameName);
										
					foundFrame = true;
					break;
					}
				catch (Exception e) {
					Platform.sleep(Log.giAutomationPause2TO);
				}
			}
			return foundFrame;
		}

		
		
		/**
		 * Returns a true/false if the widget/element is found on the page
		 * @param String sLocator - used to locate the element of the current page
		 * @return boolean true/false if the element is found
		 */
		public boolean isElementPresent(String sLocator)
		{
		
				By byLocator = by(sLocator);
				try{
					driver.findElement(byLocator);
					return true;
					}catch(Exception e){
					return false;
					}
		}

		/**
		 * Returns a string containing the current browser page body text or page source
		 * @return String of the current browser body text or page source
		 */
		public String getBodyText() {
		
			return driver.getPageSource();	
	
		}



		/**
		 * Returns a true/false if the text is found on the page
		 * @param String text - text to search for on the page
		 * @return boolean true/false if the text is found
		 */
		public boolean isTextPresent(String text) {
		
					try{
			            boolean b = driver.getPageSource().contains(text);
			            return b;
			        }
			        catch(Exception e){
			            return false;

			        }
		
		}

		/**
		 * Determines if the specified element is visible.
		 * This method will fail if the element is not present.
		 * @param locator, an element locator
		 * @return true if the specified element is visible, false otherwise
		 */
		public boolean isVisible(String locator) {
		try{
			
			return driver.findElement(by(locator)).isDisplayed();
			
		}catch(Exception ex){
			return false;
		}

		}
		
		
		/**
		 * Determines if the specified element is editable.
		 * This method will fail if the element is not present.
		 * <p>
		 * @param locator an element locator
		 * @return true if the specified element is editable, false otherwise
		 */
		public boolean isEditable(String locator) {
		try{
			
				return driver.findElement(by(locator)).isEnabled();
		
		}catch(Exception ex){
			return false;
		}

		}




		/**
		 * Assert the condition is true
		 * @param message description of comparison
		 * @param condition comparison resulting in a boolean 
		 */
		public void assertTrue(String message, boolean condition) {
			Log.altVerify(true, condition, message);
		}

		/**
		 * Like assertTrue, but won't end the execution, assert the condition is true
		 * @param condition comparison resulting in a boolean 
		 * @return true/false
		 */
		public boolean verifyTrue(boolean condition) {
			return Log.altVerify(true, condition, "");
		}
		




		/**
		 * Asserts that two string arrays have identical string contents
		 * @param text1, expected string content
		 * @param text2, actual string content
		 * @return true if strings match, false otherwise
		 */
		public boolean verifyEquals(String text1, String text2) {

			return Log.altVerify(text1, text2, true);
		}

		/**
		 * Gets the (whitespace-trimmed) value of an input field (or anything else
		 * with a value parameter). For check-box/radio elements, the value will be
		 * "on" or "off" depending on whether the element is checked or not.
		 * <p>
		 * @param sLocator, an element locator
		 */
		public String getValue(String sLocator) {

				return driver.findElement(by(sLocator)).getAttribute("value");
		
		}

		/**
		 * Gets the text of an element. This works for any element that contains
		 * text.
		 * <p>
		 * @param sLocator, an element locator
		 */
		public String getText(String sLocator) {

				return driver.findElement(by(sLocator)).getText();
		
		}

		/**
		 * Gets option value (value attribute) for selected option in the specified selected element.
		 * @param sLocator object locator identifier
		 * @return the selected option value in the specified select drop-down
		 */
		public String getSelectedValue(String sLocator) {

				return driver.findElement(by(sLocator)).getAttribute("value");
			
		}
		
		/**
		 * Gets the text of selected item in a listbox found using the sLocator string passed in
		 * @param String sLocator string used to locate the widget
		 */
		public String getSelectedItemText(String sLocator)
		{
		
				WebElement wListbox = driver.findElement(by(sLocator));
				return new Select(wListbox).getFirstSelectedOption().getText();
				
		}
		
		
		
		/**
		 * Drag and Drop specified elements.
		 * @param sLocatorFrom - locator to grab object from
		 * @param sLocatorTo - locator to drop object into
		 */
		public void dragAndDrop(String sLocatorFrom,String sLocatorTo) {
		
				WebElement fromItem1 = driver.findElement(by(sLocatorFrom));
				WebElement toItem2 = driver.findElement(by(sLocatorTo));
				new Actions(driver).dragAndDrop(fromItem1, toItem2).build().perform();
		
		}
		
		

		/**
		 * Selects a frame within the current window. (You may invoke this command
		 * multiple times to select nested frames.) To select the parent frame, use
		 * "relative=parent" as a locator; to select the top frame, use
		 * "relative=top". You can also select a frame by its 0-based index number;
		 * select the first frame with "index=0", or the third frame with "index=2".
		 * You may also use a DOM expression to identify the frame you want
		 * directly, like this: dom=frames["main"].frames["subframe"]
		 * <p>
		 * @param sLocator, an element locator identifying a frame or iframe
		 */
		public void selectFrame(String sLocator) {
		
				driver.switchTo().frame(sLocator);		
		}

		/**
		 * Selects a popup window using a window locator; once a popup window has been selected,
		 * all commands go to that window. To select the main window again, use null as the target.
		 * @param windowID, the JavaScript window ID of the window to select
		 */

		public void selectWindow(String windowID) {

				driver.switchTo().window(windowID);		
	
		}

		/**
		 * Waits for a popup window to appear and load up.
		 * <p>
		 * @param windowID
		 *            , the JavaScript window "name" of the window that will appear
		 *            (not the text of the title bar) If unspecified, or specified
		 *            as "null", this command will wait for the first non-top window
		 *            to appear (don't rely on this if you are working with multiple
		 *            popups simultaneously).
		 * @param timeout
		 *            , a timeout in milliseconds, after which the action will
		 *            return with an error. If this value is not specified, the
		 *            default Selenium timeout will be used. See the setTimeout()
		 *            command
		 */
		public void waitForPopUp(String windowID, String timeout) {
		
				for(int iteration = 0; iteration < Integer.valueOf(timeout); iteration++)
				{
						
					try{
						for (String handle : driver.getWindowHandles()) {
						     driver.switchTo().window(handle);} 
						}
					catch (Exception e){}
					Platform.sleep(Log.giAutomationPause1TO);
				}
		
		}
		
		
		
		/**
		 * Waits for a specified time for object to become visible.
		 * <p>
		 * @param String sLocator the locator as string for method to wait for visibility of            
		 * @param iWait time to wait in seconds
		 */
		public void waitUntilVisible(String sLocator, long iWait)
		{
		    
		
				ExpectedCondition<WebElement> elementVisibleCondition = ExpectedConditions.visibilityOfElementLocated(by(sLocator));

				WebDriverWait wait = new WebDriverWait(driver, iWait);
				wait.until(elementVisibleCondition);
			
			
		}	
		
		

		/**
		 * Verifies that the specified element is somewhere on the page. extend the
		 * super method to wait in a cycle for 60 seconds
		 * <p>
		 * @param sLocatoran element locator
		 * @return true if the element is present, false otherwise
		 */
		public boolean waitForElementPresent(int iWait, String sLocator) {

			boolean bElementPresent = false;

				waitUntilVisible(sLocator, iWait);
				
				return bElementPresent;
		}
		
		
		
		
		
		
		
		/**
		 * Prints all links found with default locators
		 */
		public void printAllLinks()
		{
			
				List<WebElement> link=driver.findElements(By.tagName("a"));
				for(WebElement ele:link)
				{
			     System.out.println(ele.getText());
				} 
	
		}



		/**
		 * Gets the Xpath Count
		 * @param arg0 locator for element
		 */
		public Number getXpathCount(String arg0)
		{
			return driver.findElements(by(arg0)).size();
		}



		/**
		 * Gets the object Attributes
		 * @param arg0 locator with attribute info
		 * @return String containing requested object attribute 
		 */
		public String getAttribute(String arg0)
		{
	
				String[] splitString = arg0.split("~");
				return driver.findElement(by(splitString[0])).getAttribute(splitString[1]);	//requires 2 args for WebDriver TBD

		}

		
		/**
		 * Returns All Window Ids in a String array
		 * @return All Window Ids in a String array
		 */
		public String[] getAllWindowIds()
		{
		String ls[];
		int x =0;
	
			Set<String> handles = driver.getWindowHandles();
			ls = new String[handles.size()];
			for (String handle : handles) {
				ls[x]=handle;
				x++;
			}
			return ls;
			}
		
		
		
		/**
		 * Gets Window Id
		 * @return String Window Handle
		 */
		public String getWindowId()
		{
		
				String handle = driver.getWindowHandle();
				return handle;
		}

		
		
		/**
		 * Returns all Window Names in a String array
		 * @return all Window Names in a String array
		 */
		public String[] getAllWindowNames()
		{
		String ls[];
		int x =0;
		
			Set<String> handles = driver.getWindowHandles();
			ls = new String[handles.size()];
			for (String handle : handles) {
				ls[x]=driver.switchTo().window(handle).getTitle();
				x++;
			}
			return ls;
	
		}



		/**
		 * Returns all Window Titles in a String array
		 * @author all Window Titles in a String array
		 */
		public String[] getAllWindowTitles()
		{
		String ls[];
		int x =0;
		
				Set<String> handles = driver.getWindowHandles();
				ls = new String[handles.size()];
				for (String handle : handles) {
					ls[x]=driver.switchTo().window(handle).getTitle();
					x++;
				}
				return ls;
			
		
		}
	
		
		/**
		 * Returns an Alert as string
		 * @return an Alert as string
		 */
		public String getAlert()
		{
			return driver.switchTo().alert().getText();
		}



		/**
		 * Refresh the page
		 */
		public void refresh()
		{
			driver.navigate().refresh();	
		}


		/**
		 * Deletes all Cookies
		 * @param arg0 required for RC not required for webdriver
		 * @param arg1 required for RC not required for webdriver
		 */
		public void deleteCookie(String arg0, String arg1)
		{
		
				driver.manage().deleteAllCookies();
		
		}


		/**
		 * deletes All Visible Cookies
		 */
		public void deleteAllVisibleCookies()
		{
		
				driver.manage().deleteAllCookies();
		
		}	

		/**
		 * Gets cookies as string
		 */
		public String getCookie()
		{
			
				return driver.manage().getCookies().toString();
			
		}


		/**
		 * Get the alert confirmation text
		 * @return the alert confirmation text
		 */
		public String getConfirmation()
		{
		
				return driver.switchTo().alert().getText();
		
		}

		/**
		 * Accept confirmation alert
		 */
		public void chooseOkOnNextConfirmation()
		{
			
				driver.switchTo().alert().accept();
			
		}

		/**
		 * Choose Cancel on confirmation alert
		 */
		public void chooseCancelOnNextConfirmation()
		{
		
				driver.switchTo().alert().dismiss();
		
		}

		
		
		/**
		* DeSelects an item in a listbox found using the sLocator and Text Label string values (Option visible string
		* value)
		* <p> 
		* @param sLocator string used to locate the widget
		* @param sLabel text item to deselect in the list box
		* @throws Exception if option is not found
		*/
		public void deSelectByLabel(String sLocator, String sLabel) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select listbox = new Select(weListbox);
				listbox.deselectByVisibleText(sLabel);
			
		}
		
		
		
		/**
		 * Deselects an item in a listbox found using the sLocator and the partial text of an option in a list 
		 * <p>
		 * @param sLocator string used to locate the widget
		 * @param sLabel partial text of item to deselect in the list box
		 * @throws Exception if item is not found
		 */
		public void deSelectByPartialLabel(String sLocator, String sLabel) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select listbox = new Select(weListbox);
				List<WebElement> options = listbox.getOptions();
				boolean bFound = false;

				for (WebElement option : options) {
					if (option.getText().contains(sLabel.trim())) {
						bFound = true;
						String sOption = option.getText(); 
						listbox.deselectByVisibleText(sOption);
						break;
					}
				}

				if (!bFound) {
					throw new Exception("List option not found");
				}
			}
			

		
		
		

		/**
		* DeSelects an item in a listbox found using the sLocator and value of list option 
		* <p> 
		* @param sLocator string used to locate the widget
		* @param sValue Value to deselect in the list box
		* @throws Exception if option is not found
		*/
		public void deSelectByValue(String sLocator, String sValue) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select listbox = new Select(weListbox);
				listbox.deselectByValue(sValue);
			
		}
		
		
		
		/**
		* DeSelects an item in a listbox found using the sLocator and value of list option 
		* <p> 
		* @param sLocator string used to locate the widget
		* @param iIndex value to deselect in the list box
		* @throws Exception if option is not found
		*/
		public void deSelectByIndex(String sLocator, int iIndex) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select listbox = new Select(weListbox);
				listbox.deselectByIndex(iIndex);
				
		
		}
		
		
		
		/**
		* DeSelects all items in a listbox found using the sLocator 
		* <p> 
		* @param sLocator string used to locate the list widget
		* @throws Exception if list object is not found
		*/
		public void deSelectAll(String sLocator) throws Exception {
			
				WebElement weListbox = driver.findElement(this.by(sLocator));
				Select listbox = new Select(weListbox);
				listbox.deselectAll();
			
		}
		
		/**
		* Highlights the webelement 
		* 
		*/		
		public void highlightElement(String sLocator) {
			
			waitForElementPresent(Log.giAutomationNormalTO,sLocator);
			
	        for (int i = 0; i <2; i++) {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("arguments[0].style.border='4px groove green'", driver.findElement(by(sLocator)));
	            js.executeScript("arguments[0].style.background='yellow'", driver.findElement(by(sLocator)));
	            
	            try {
					Thread.sleep(Log.giAutomationPause1TO);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            
	            js.executeScript("arguments[0].style.border=''", driver.findElement(by(sLocator)));
	            js.executeScript("arguments[0].style.background=''", driver.findElement(by(sLocator))); 
	            
	        }
	    }
		
	 /**
	  * Scrolls the webelement into view of the browser page 
	  */	
	 public void scrollElementIntoView(String sLocator) 
	  {
		 waitForElementPresent(Log.giAutomationNormalTO,sLocator);
		 
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by(sLocator)));
	  }

	 /**
	  * Scrolls the browser page based on the co-ordinates specified
	  * @param x number of pixels to scroll by, along the x-axis (horizontal).
	  * 		 Positive values will scroll to the left, while negative values will scroll to the right 
	  * @param y number of pixels to scroll by, along the y-axis (vertical).
	  * 		 Positive values will scroll down, while negative values scroll up
	  */ 
	 public void scrollingByCoordinatesofAPage(int x, int y) 
	 {
		((JavascriptExecutor) driver).executeScript("window.scrollBy("+x+","+y+")");
	 }
		
		
	 /**
	  * Scrolls the browser to the bottom of the page 
	  */
	 public void scrollToBottom() 
	  {
		 Actions builder = new Actions(driver);
		 builder.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		 
	  }
	 
	 /**
	  * Scrolls the browser to the top of the page 
	  */
	 public void scrollToTop() 
	  {
		 Actions builder = new Actions(driver);
		 builder.keyDown(Keys.CONTROL).sendKeys(Keys.HOME).perform();
		 
	  }
	 
		
	 
		
		
		
	}	//end of inner seleniumHelper class


}//end of main SeleniumCore class
