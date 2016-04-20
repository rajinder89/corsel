package dummy.core.libs;

import java.awt.Robot;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * The Platform class contains generic Platform specific functions to handle different Browsers, different Operating Systems, etc.
 */
public class Platform {


	//**************Operating system variables and methods**************


	/**Operating System Name */
	public static final String OS_NAME = System.getProperty("os.name");

	/**Operating System Version */
	public static final String OS_VERSION = System.getProperty("os.version");

	/**Java Version */
	public static final String JAVA_VERSION = System.getProperty("java.version");

	/**returns true if Windows OS */
	private static boolean isWindows = OS_NAME.startsWith("Windows");


	/**return Windows OS version*/
	private static String sWinXP = "Version 5.1";
	private static String sWin7 = "Version 6.1";
	private static String sVista = "Version 6.0"; 


	/**return true if Windows OS */
	public static boolean isWindows() {
		return isWindows;
	}

	/**
	 * @method getOSDisplayVersion()
	 * @description String - Returns the Operating system version 
	 */
	public static String getOSDisplayVersion()
	{
		String osVersion = "Unknown";

		if(isWinXP())
		{
			osVersion = "XP (" + OS_VERSION + ")";
		}
		if(isVista())
		{
			osVersion = "Vista (" + OS_VERSION + ")";
		}
		if(isWin7())
		{
			osVersion = "7 (" + OS_VERSION + ")";
		}

		return osVersion;

	}

	/**
	 * @method getOSNameAndVersion()
	 * @description String - Returns the Operating system name and version 
	 */
	public static String getOSNameAndVersion() {
		return OS_NAME + " " + OS_VERSION;			//getOSDisplayVersion();
	}

	/**
	 * Determine if the OS is WindowXP by examining OS version
	 * @return true if OS is Windows XP
	 */
	public static boolean isWinXP(){
		if(isWindows() && OS_VERSION.contains(sWinXP)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Determine if the OS is Vista by examining OS version
	 * @return true if OS is Vista
	 */
	public static boolean isVista(){
		if(isWindows() && OS_VERSION.contains(sVista)) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Determine if the OS is Win7 by examining OS version
	 * @return true if OS is Windows 7
	 */
	public static boolean isWin7(){
		if(isWindows() && OS_VERSION.contains(sWin7)) {
			return true;
		}else {
			return false;
		}
	}


	/**
	 * @method getJavaVersion()
	 * @description String - Returns the Java version 
	 */
	public static String getJavaVersion() {
		return "JDK v"+ JAVA_VERSION;			//getJavaVersion();
	}


	/**
	 * Returns the appropriate file separator for a specific Operating system
	 * i.e. "\" for windows OS, ":" for Mac OS, etc 
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	/**
	 * Returns the current users home path
	 * i.e. "c:\\documents and settings\\myname" for windows XP, 
	 *      "c:\\users\myname\\" for Windows 7, etc 
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}

	/**
	 * Returns the current Java project path
	 */
	public static String getCurrentProjectPath() {
		String myProjectPath = new File(".").getAbsolutePath().replace(".", ""); 

		//myProjectPath = myProjectPath.replace("\\", "/");

		if(myProjectPath.endsWith("/"))
		{
			myProjectPath = myProjectPath.substring(0,myProjectPath.length()-1);
		}

		return myProjectPath;
	}

	/**
	 * Returns the full file path corresponding to it's location
	 */
	public static String getResourceFullPath(String resourcePath) {
		return new File(ClassLoader.getSystemResource(resourcePath).getPath()).getPath();
	}

	//**************Browser variables and methods**************

	/**
	 * Returns the name of the web browser as string
	 * @return returns web browser name as String
	 */
	public static String getWebBrowserName() {
		return SeleniumCore.getBrowser().getDisplayName();
	}

	/**
	 * Returns true if current browser if Firefox
	 */
	public static boolean isFirefox() {
		return getWebBrowserName().equalsIgnoreCase(gsMozillaFirefox);
	}

	/**
	 * Returns true if current browser if InternetExplorer
	 */
	public static boolean isInternetExplorer() {
		return getWebBrowserName().equalsIgnoreCase(gsInternetExplorer);
	}
	
	/**
	 * Returns true if current browser if InternetExplorer9
	 */
	public static boolean isInternetExplorer9()
	{
		return getWebBrowserName().equalsIgnoreCase(gsInternetExplorer9);
	}

	/**
	 * Returns true if current browser if InternetExplorer10
	 */
	public static boolean isInternetExplorer10()
	{
		return getWebBrowserName().equalsIgnoreCase(gsInternetExplorer10);
	}


	/**Global string for Mozilla Firefox Browser*/
	public static String gsMozillaFirefox = "Firefox";

	/**Global string for Microsoft Internet Explorer Browser*/
	public static String gsInternetExplorer = "Internet Explorer";

	/**Global string for Microsoft Internet Explorer process name*/
	public static String gsIExplore = "iexplore.exe";

	/**Global string for Microsoft Internet 9.0 Explorer Browser*/
	public static String gsInternetExplorer9 = "Internet Explorer 9.0";

	/**Global string for Microsoft Internet 10.0 Explorer Browser*/
	public static String gsInternetExplorer10 = "Internet Explorer 10.0";

	/**Global string for Chrome Browser*/
	public static String gsGoogleChrome = "Google Chrome";

	//General methods & variables

	/**Global string for new line separator*/
	public static String gsNewline = System.getProperty("line.separator");


	/**
	 * @method: sleep()
	 * @Description: Pauses execution for a specified number of seconds
	 * @Parameter: numberOfSecondsToSleep - number of seconds to pause
	 */
	public static void sleep(int numberOfSecondsToSleep) {
		try {
			Thread.sleep(numberOfSecondsToSleep * 1000);
		} catch (Exception e) {
		}
	}

	//**************Clipboard & SendKey methods***************************

	/**
	 * Sets text to clipboard
	 * @param s String to send to clipboard
	 */
	public static void setClipboard(String s) {

		//get clipboard
		Clipboard clip = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();

		//set clipboard contents
		StringSelection ss = new java.awt.datatransfer.StringSelection(s);
		clip.setContents(ss, ss);
	}

	/**
	 * Gets text from clipboard and returns text as String
	 * @return String text from clipboard
	 */
	public static String getClipboard() {
		String tmp = "";

		//get content from clipboard into output String
		Clipboard clip = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = clip.getContents(null);

		try
		{
			tmp = (String) t.getTransferData(DataFlavor.stringFlavor);
		}
		catch (Exception e)
		{
			Log.errorHandler("" + e.toString(), e);
		}

		return tmp;
	}

	/**
	 * sendKeys - this method uses the Java Robot class to send actual alpha-numeric keyboard keystrokes
	 * to the active application on the desktop. For example, sendKeys("My Test 123"); 
	 * types in My Test 123 into the active desktop application. 
	 * @param sValue - keys to be typed
	 */
	public static void sendKeys(String sValue) {
		try{	
			Robot robot = new Robot();

			//break string into char array
			char[] c = sValue.toCharArray();

			for (int x=0; x < c.length; x++){  //cycle through input chars
				for (int i=1; i<128; i++) {	//cycle through ASCII chars
					if (c[x]==(char)i){		//compare input char to ascii char if match use keypress method 
						//System.out.println("x: " + x + "i:" + i);
						//System.out.println(c[x]+": " + (char)i);
						//Platform.sleep(Log.giAutomationPause1TO); //pause 1 second before each keystroke
						if (i<91 && i>64){  //captial letters 
							robot.keyPress(KeyEvent.VK_SHIFT); 
							robot.keyPress(i);
							robot.keyRelease(KeyEvent.VK_SHIFT);
							robot.keyRelease(i);
						}
						else if(i<123 && i>96){  //lower case letters
							i = i - 32;
							robot.keyPress(i);
							robot.keyRelease(i);
						}
						else if (i<58 && i>47){  //numeric characters
							robot.keyPress(i);
							robot.keyRelease(i);
						}
						else{ //will enter non-alpha-numeric keys but will flag invalid characters such as )(}{+*^@><? as errors
							try{
								robot.keyPress(i);
								robot.keyRelease(i);
							}catch (Exception e1){
								Log.errorHandler("Invalid Character: " + c[x]+ "Use sendKeys(int iValue) method to send non-alpha numeric keys",e1);
							}

						}
						break;
					}

				}	 
			}	 
			Log.logScriptInfo("Send Keys: " + "\"" + sValue + "\"" + " to active field in desktop.");

		}
		catch(Exception e){
			Log.errorHandler("Error sending keystrokes",e);
		}

	}


	/**
	 * sendPress - this method uses the Java Robot class to send a single keyboard press of any single key
	 * to the active application on the desktop. For example, sendKey(KeyEvent.VK_HOME)  //presses HOME key 
	 * @param iValue - int keycode of keys to be pressed
	 */
	public static void sendKeyPress(int iValue) {
		try{	
			Robot robot = new Robot();
			//KeyEvent.VK_ALT = 18
			//KeyEvent.VK_CONTROL = 17
			//KeyEvent.VK_END = 35
			//KeyEvent.VK_HOME = 36
			//To find more KeyEvent constant values uncomment the line below and hover over VK_HOME constant 
			//then click the "Constant Field Values" link in the context javadoc help window that appears 
			//int i = KeyEvent.VK_HOME;


			robot.keyPress(iValue); 
			Log.logScriptInfo("Press " + "\"" + KeyEvent.getKeyText(iValue) + "\"" + " Key");

		}
		catch(Exception e){
			Log.errorHandler("Error sending keypress",e);
		}
	}

	/**
	 * sendKeyRelease - this method uses the Java Robot class to release a single keyboard press of any single key
	 * to the active application on the desktop. For example, sendKey(KeyEvent.VK_HOME)  //presses HOME key 
	 * @param iValue - int keycode of keys to be pressed
	 */
	public static void sendKeyRelease(int iValue) {
		try{	
			Robot robot = new Robot();
			//KeyEvent.VK_ALT = 18
			//KeyEvent.VK_CONTROL = 17
			//KeyEvent.VK_END = 35
			//KeyEvent.VK_HOME = 36
			//To find more KeyEvent constant values uncomment the line below and hover over VK_HOME constant 
			//then click the "Constant Field Values" link in the context javadoc help window that appears 
			//int i = KeyEvent.VK_HOME;


			robot.keyRelease(iValue); 
			Log.logScriptInfo("Release " + "\"" + KeyEvent.getKeyText(iValue) + "\"" + " Key");

		}
		catch(Exception e){
			Log.errorHandler("Error sending key release",e);
		}
	}

	/**
	 * sendKeyClipboardCopy - this method uses the Java Robot class to press the Ctrl-C (clipboard Copy command)
	 * to the active systems clipboard. 
	 */
	public static void sendKeyClipboardCopy(){
		sendKeyPress(KeyEvent.VK_CONTROL);
		sendKeyPress(KeyEvent.VK_C);
		sleep(Log.giAutomationPause2TO);
		sendKeyRelease(KeyEvent.VK_C);
		sendKeyRelease(KeyEvent.VK_CONTROL);
		sleep(Log.giAutomationPause2TO);
		Log.logScriptInfo("Executed Clipboard Copy via key stroke CTRL-C");
	}


	/**
	 * sendKeyClipboardPaste - this method uses the Java Robot class to press the Ctrl-V (clipboard Paste command)
	 * to the active systems clipboard. 
	 */
	public static void sendKeyClipboardPaste(){
		sendKeyPress(KeyEvent.VK_CONTROL);
		sendKeyPress(KeyEvent.VK_V);
		sleep(Log.giAutomationPause2TO);
		sendKeyRelease(KeyEvent.VK_CONTROL);
		sendKeyRelease(KeyEvent.VK_V);
		sleep(Log.giAutomationPause2TO);
	}

	/**
	 * sendKeySelectAll - this method uses the Java Robot class to press the Ctrl-A (Select All command)
	 */
	public static void sendKeySelectAll(){
		sendKeyPress(KeyEvent.VK_CONTROL);
		sendKeyPress(KeyEvent.VK_A);

		sendKeyRelease(KeyEvent.VK_CONTROL);
		sendKeyRelease(KeyEvent.VK_A);
	}


	/**
	 * maximizeWin - this method uses AutoIt to maximize a specified window
	 * @param - sWindow - String caption of window to maximize
	 */
	public static void maximizeWin(String sWindow) {
		try{
			Runtime.getRuntime().exec(Log.gsAutomationAutoPath + "core" + 
					getFileSeparator() + "tools" + getFileSeparator()+ "autoit" + getFileSeparator()+
					"MaximizeWindow.exe " + Strings.sDQ + sWindow + Strings.sDQ);
			Log.logScriptInfo("Maximize Window: " + sWindow);
		}
		catch(Exception e){
			Log.errorHandler("Error maximizing window: " + sWindow,e);
		}
	}


	/**
	 * minimizeWin - this method uses AutoIt to minimize a specified window
	 * @param - sWindow - String caption of window to minimize
	 */
	public static void minimizeWin(String sWindow) {
		try{
			Runtime.getRuntime().exec(Log.gsAutomationAutoPath + "core" + getFileSeparator() + "tools" + getFileSeparator()+ "autoit" + getFileSeparator()+ "MinimizeWindow.exe " + Strings.sDQ + sWindow + Strings.sDQ);
			Log.logScriptInfo("Minimize Window: " + sWindow);
		}
		catch(Exception e){
			Log.errorHandler("Error minimizing window: " + sWindow,e);
		}
	}

}