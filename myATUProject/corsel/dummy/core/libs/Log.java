package dummy.core.libs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Properties;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;



/**
* The Log class Performs script initialization, termination and logging operations among many other functions. 
* <p>
* The log output is written simultaneously to a text file, the system console and to an HTML file.
* The HTML result log displays results hierarchically with passes in green and fails in red for easy identification.
* In order to implement the automation logging simply import the Log class in your test script and add the Log.initialize() and Log.terminate() methods
* to your scripts setup and cleanup sections. Also, all test cases in your test section must be prefaced by the method Log.startTestcase(). 
* All error handling should be done by the method Log.errorHandler().
* <p>
* The Automation Log class has an associated properties file called automation.properties.  Place the
* automation.properties file in your client system's home directory. The home directory is usually the
* c:\Users\<your name> folder. You can find out what your home directory is by executing
* the following java command: 
* <BR>
* System.out.printn(Platform.getUserHome());
* <BR> 
* Edit the properties in the automation.properties file to represent your local system and automation settings.
* If you do not have this file, the Log class will use default variables.
*/
public class Log
{
	//Global Automation & System variables

	/**Automation base path*/
	public static String gsAutoBasePath = Platform.getUserHome() + Platform.getFileSeparator();

	/**Automation properties file name*/
	public static String gsAutoPropFile = gsAutoBasePath + "automation.properties";

	/**Global string for automation directory*/
	public static String gsAutomationAutoPath = Platform.getCurrentProjectPath();

	/**Global string for automation result directory*/
	public static String gsAutomationAutoResultPath = Platform.getCurrentProjectPath() + "results" + Platform.getFileSeparator() ;
	
	
	/**Global string for automation Test Data Spreadsheet suffix*/
	public static String gsAutomationTestDataSpreadsheetSuffix=".xls";
	
	/**Global string for old Excel .xls file extension*/
	public static String gsAutomationExcelXLSExt=".xls";
	
	/**Global string for automation support documents path*/
	public static String gsAutomationAutoSupportDocs = (gsAutomationAutoPath + Platform.getFileSeparator());
	
	/**Global strings for Browser Profile file*/
	public static String gsAutomationBrowserProfile;
	
	/**Global strings for Browser Path*/
	public static String gsAutomationBrowserPath;
	
	/**Global string for test browser*/
	public static String gsAutomationTestBrowser; // = "Internet Explorer 6.0";

	//Global time-out variables

	/**Global Time-out variable - 1 Second*/
	public static int giAutomationPause1TO; // = 1;

	/**Global Time-out variable - 2 Seconds*/
	public static int giAutomationPause2TO; // = 2;

	/**Global Time-out variable - 5 Seconds*/
	public static int giAutomationShortTO; // = 5;

	/**Global Time-out variable - 10 Seconds*/
	public static int giAutomationNormalTO; // = 10;

	/**Global Time-out variable - 15 Seconds*/
	public static int giAutomationWaitTO; // = 15;

	/**Global Time-out variable - 30 Seconds*/
	public static int giAutomationMedTO; // = 30;

	/**Global Time-out variable - 60 Seconds*/
	public static int giAutomationLongTO; // = 60;

	
	//Global script header information

	/**Global string for script author*/
	public static String gsScriptAuthor;

	/**Global string for script description*/
	public static String gsScriptDescription;

	/**Global string for script test area*/
	public static String gsScriptTestArea;

	/**Global string for script name*/
	public static String gsScriptName;

	/**Global long for script start time*/
	public static long glStartTime = 0;

	/**Global string for line separator*/
	public static String gsNewline = System.getProperty("line.separator");

	/**Global string for default Browser Title*/
	public static String gsAutomationBrowserBaseStatePage = "about:blank";

	/** Global variable to store Test Suite Start time*/
	public static String gsSuiteStartTime;



	/**
	 * Constructor for Log class logging without script header variables
	 */
	public Log()
	{
	}

	/**
	 * Constructor for Log class Logging with script header variables
	 * <p>
	 * @param sAuthor script author
	 * @param sScriptDescription script description
	 * @param sTestArea script test functional area
	 */
	public Log(String sAuthor, String sScriptDescription, String sTestArea)
	{
	//assign script variables
	gsScriptAuthor = sAuthor;
	gsScriptDescription = sScriptDescription;
	gsScriptTestArea = sTestArea;
	}
	
	private void setAuthorInfoForReports() {
		   ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");
		}

	/**
	 * Initializes test script and system variables at script startup
	 */
	public static void initialize()
	{		
		//initialize automation global variables 
		try {
				autoSetup(true);
				
				ATUReports.setWebDriver(SeleniumCore.driver);
			} catch (Exception e) {
			
				ATUReports.add("Error in initializing property file", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		
	}

	
	/**
	 * Executes script cleanup and test metric gathering functions upon script termination
	 */
	public static void terminate()
	{
		//Get test metrics and report results
		autoCleanup(true);
		

	}
		

	/**
	 * Returns script name
	 * @return script name
	 */
	public static String getScriptName()
	{
		return gsScriptName;	
	}

	/**
	 * Sets the script name
	 * @param sName script name
	 */
	public static void setScriptName(String sName)
	{
		gsScriptName = sName;
	}


	/**
	* Sets up, initializes automation environment and logs script header info
	* <P>
	* @param bShowHeaderInfo	true displays script header information, false does not display script header information
	 * @throws Exception 
	*/
	public static void autoSetup(boolean bShowHeaderInfo)
	{
		
		if (getScriptName() == null)
		{
			try
			{
				setScriptName(gsScriptName);
			}
			catch(Exception e) //in case the calling script doesn't have a matching script definition in the resources directory for some reason, just bail
			{
				//noop
			}
		}


		//Setup and initialize automation environment
		DateFormat dtformat = DateFormat.getDateInstance();
		DateFormat tmformat = DateFormat.getTimeInstance();


		//Get system specific global variables
		loadAutoPropSettings(gsAutoPropFile);
		
		
		//set the current browser
		try{
				SeleniumCore.setCurrentBrowser(gsAutomationTestBrowser);
			}
		catch(Exception e)
		{
			SeleniumCore.setCurrentBrowser(gsAutomationTestBrowser);

		}

		String myBrowserDisplayName = "";

		// can't get the browser version until it's launched.  we'll log that later...
		myBrowserDisplayName = SeleniumCore.getBrowser().getDisplayName() ; 
	}
	
	
	/*
	* Sets script clock start time - by default this is started automatically at script startup when the
	* autoSetup() function is used. The function sets a global variable called glStartTime which is then
	* used as an argument to the getElapsedTime(glStartTime) function which is called in the logScriptInfo() function   <p>
	*/
	public static void setStartTime()
	{
		long lTime = System.currentTimeMillis();
		glStartTime = lTime;
	}

	/**
	* Logs script testcase information and other metrics info <p>
	* @param bShowResultFooter true to show result metrics in the footer section of the log results
	 * @throws Exception 
	*/
	public static void autoCleanup(boolean bShowResultFooter)
	{
		Browser.close();

	}

	/**
	* Displays a banner in the result log output
	* @param sBanner message to display in banner
	*/
	public static void logBanner(String sBanner)
	{
		ATUReports.add(sBanner, LogAs.INFO, null);
	}


	/**
	* Compares 2 boolean values and logs result info <p>
	* <p>
	* @param bExpected		Expected Value
	* @param bActual		Actual Value
	* @param sDesc			Description of boolean comparison
	* @return true if matched false if no-match
	*/
	public static boolean altVerify(boolean bExpected, boolean bActual, String sDesc)
	{
		if (bActual == bExpected)
		{
		
			ATUReports.add("Verify " + sDesc + " Expected: " + bExpected + " Actual: " + bActual, LogAs.PASSED, null);
			return true;
		}
		else
		{
		
		ATUReports.add("Verify " + sDesc + " Expected: " + bExpected + " Actual: " + bActual, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			return false;
		}

	}
	
	
	/**
	 * Compares 2 boolean values and displays all result info, throws exception if no match <p>
	 * @param bExpected		Expected Value
	 * @param bActual		Actual Value
	 * @param sDesc			Description of boolean comparison
	 * @return true if matched false if no-match
	 * Intended to be called within a try/catch block of a testcase and cease execution of subsequent commands
	 * in the testcase if the exception is thrown.  If the calling method should continue execution even if
	 * the comparison fails, use altVerify instead of altVerifyFatal
	 * @throws Exception
	 */
	public static boolean altVerifyFatal(boolean bExpected, boolean bActual, String sDesc) throws Exception
	{
		if (!altVerify(bExpected, bActual, sDesc)){
			throw new Exception("Error performing: " + sDesc + " Expected: " + bExpected + " Actual: " + bActual);
		} else {
			return true;
		}
	}

	/**
	* Compares 2 boolean values and displays result info - only log output of errors <p>
	* <p>
	* @param bExpected Expected Value
	* @param bActual Actual Value
	* @param sDesc Description of boolean comparison
	* @return true if matched false if no-match
	*/
	public static boolean altConfirm(boolean bExpected, boolean bActual, String sDesc)
	{
		if (bActual == bExpected)
		{
			ATUReports.add("Verify " + sDesc + " Expected: " + bExpected + " Actual: " + bActual, LogAs.PASSED, null);
			return true;
		}
		else
		{
		
		ATUReports.add(sDesc + " Expected: " + bExpected + " Actual: " + bActual, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			return false;
		}

	}

	/**
	 * Compares 2 String values and displays all result info, throws exception if no match <p>
	 * @param sExpected Expected Value
	 * @param sActual Actual Value
	 * @param bExact true if exact match is required, false for case insensitive
	 * @param sDesc Description of boolean comparison
	 * @return true if matched false if no-match
	 * Intended to be called within a try/catch block of a testcase and cease execution of subsequent commands
	 * in the testcase if the exception is thrown.  If the calling method should continue execution even if
	 * the comparison fails, use altVerify instead of altVerifyFatal
	 * @throws Exception
	 */
	public static boolean altVerifyFatal(String sExpected, String sActual, boolean bExact, String sDesc) throws Exception
	{
		if (!altVerify(sExpected, sActual, bExact, sDesc)){
			throw new Exception("Error verifying: " + sDesc +  " Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"");
		} else {
			return true;
		}
	}


	/**
	* Compares 2 string values and displays all result info <p>
	* @param sExpected Expected Value
	* @param sActual Actual Value
	* @param bExactMatch true means actual and expected must be exact, false means Expected must be contained within Actual
	* @return true if matched false if no-match
	*/
	public static boolean altVerify(String sExpected, String sActual, boolean bExactMatch)
	{
		if (bExactMatch == true)
		{
			if (sActual.toUpperCase().equals(sExpected.toUpperCase()))
			{
				
				ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"", LogAs.PASSED, null);
				return true;
			}
			else
			{
				ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				return false;
			}
		}
		else
		{
			if (sActual.toUpperCase().indexOf(sExpected.toUpperCase()) != -1)
			{
				
				ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"", LogAs.PASSED, null);
				return true;
			}
			else
			{
			ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				return false;
			}
		}

	}


	/**
	* Compares 2 string values and displays all result info. Can test for exact match<p>
	* @param sExpected Expected Value
	* @param sActual Actual Value
	* @param sDescription Description of the test
	* @return true if matched false if no-match
	*/
	public static boolean altVerify(String sExpected, String sActual, boolean bExactMatch, String sDescription)
	{
		if (bExactMatch)
		{

			if (sActual.toUpperCase().equals(sExpected.toUpperCase()))
			{
				ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription, LogAs.PASSED, null);
				return true;
			}
			else
			{
			
			ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				return false;
			}
		}
		else
		{
			if (sActual.toUpperCase().indexOf(sExpected.toUpperCase()) != -1)
			{
				ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription, LogAs.PASSED, null);
				return true;
			}
			else
			{
			
			ATUReports.add("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
				return false;
			}
		}

	}


	/**
	* Compares 2 integer values and displays all result info <p>
	* @param iExpected		Expected Value
	* @param iActual		Actual Value
	* @param sDesc			Description of boolean comparison
	* @return true if matched false if no-match
	*/
	public static boolean altVerify(int iExpected, int iActual, String sDesc)
	{
		if (iActual == iExpected)
		{
			ATUReports.add("Verify " + sDesc + " Expected: " + iExpected + " Actual: " + iActual, LogAs.PASSED, null);
			return true;
		}
		else
		{

		ATUReports.add("Verify " + sDesc + " Expected: " + iExpected + " Actual: " + iActual, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			return false;
		}

	}

	/**
	* Compares 2 integer values and displays all result info. Throws Exception that will stop script upon failure <p>
	* @param iExpected		Expected Value
	* @param iActual		Actual Value
	* @param sDesc			Description of boolean comparison
	* @return true if matched false if no-match
	* @throws Exception
	*/
	public static boolean altVerifyFatal(int iExpected, int iActual, String sDesc) throws Exception{
		if (!altVerify(iExpected, iActual, sDesc)){
			throw new Exception("Error verifying: " + sDesc);
		} else {
			return true;
		}
	}

	//****************************Automation Properties methods
	
	/**
	* Loads system specific global automation variables <p>
	* @param sFile		properties file to load
	*/
	public static void loadAutoPropSettings(String sFile)
	{
		//System.out.println(sFile);
		
		String sTmp = "";
		
		//if automation.properties file does not exist, 
		if(!FileIO.fileExists(sFile))
		{
			System.err.println("ERROR: The " + "\"" + "Automation.properties" + "\"" + " file does not exist in the user home folder: " + FileIO.getParentPath(sFile));
			System.err.println("Copy the automation.properties file to your home folder");
			System.exit(-1);	//Stops all further execution
		}
		
		
		//else
		//System.out.println(sFile);	//found automation.properties file in the users home folder
		
		//load Automation properties
		try
			{
				//General Automation Properties
				
				//Parent Automation Directory
				gsAutomationAutoPath = getPropertyFromFile("gsAutomationAutoPath",sFile);
				if (gsAutomationAutoPath == null || gsAutomationAutoPath.isEmpty()){
					gsAutomationAutoPath = Platform.getCurrentProjectPath(); //set to default if left blank in automation.propeties file
					//System.out.println(gsAutomationAutoPath);
				}

				//Test Data Directory
				gsAutomationAutoSupportDocs = getPropertyFromFile("gsAutomationAutoSupportDocs",sFile);
				if (gsAutomationAutoSupportDocs == null || gsAutomationAutoSupportDocs.isEmpty()){
					sTmp = Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator());
					gsAutomationAutoSupportDocs = FileIO.getParentPath(sTmp)+"testdata" + Platform.getFileSeparator(); //set to default if left blank in automation.propeties file
					//System.out.println(gsAutomationAutoSupportDocs);
				} 


				//Result Log Directory
				gsAutomationAutoResultPath = getPropertyFromFile("gsAutomationAutoResultPath",sFile);
				if (gsAutomationAutoResultPath == null || gsAutomationAutoResultPath.isEmpty()){
					sTmp = Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator());
					gsAutomationAutoResultPath = FileIO.getParentPath(sTmp)+"results" + Platform.getFileSeparator(); //set to default if left blank in automation.propeties file
					//System.out.println(gsAutomationAutoResultPath);
				}
					
				//if result directory doesn't exist create it
				File resultsDir = new File(gsAutomationAutoResultPath);
				if (!resultsDir.exists())
				{
					resultsDir.mkdirs();
				}
		
				
				//Spreadsheet suffix
				gsAutomationTestDataSpreadsheetSuffix = getPropertyFromFile("gsAutomationTestDataSpreadsheetSuffix",sFile);
				if (gsAutomationTestDataSpreadsheetSuffix == null || gsAutomationTestDataSpreadsheetSuffix.isEmpty())
					gsAutomationTestDataSpreadsheetSuffix = ".xls"; //set to default if left blank in automation.propeties file
						
											
				//Global Automation Time out / Wait variables 
				sTmp = getPropertyFromFile("giAutomationPause1TO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationPause1TO = 1; //set to default if left blank in automation.propeties file
				else	
					giAutomationPause1TO = Integer.parseInt(getPropertyFromFile("giAutomationPause1TO",sFile));
				
				
				sTmp = getPropertyFromFile("giAutomationPause2TO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationPause2TO = 2; //set to default if left blank in automation.propeties file
				else	
					giAutomationPause2TO = Integer.parseInt(getPropertyFromFile("giAutomationPause2TO",sFile));
				
				
				sTmp = getPropertyFromFile("giAutomationShortTO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationShortTO = 5; //set to default if left blank in automation.propeties file
				else	
					giAutomationShortTO = Integer.parseInt(getPropertyFromFile("giAutomationShortTO",sFile));
				
				
				sTmp = getPropertyFromFile("giAutomationNormalTO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationNormalTO = 10; //set to default if left blank in automation.propeties file
				else
					giAutomationNormalTO = Integer.parseInt(getPropertyFromFile("giAutomationNormalTO",sFile));
				
				
				sTmp = getPropertyFromFile("giAutomationWaitTO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationWaitTO = 15; //set to default if left blank in automation.propeties file
				else
					giAutomationWaitTO = Integer.parseInt(getPropertyFromFile("giAutomationWaitTO",sFile));
				
				sTmp = getPropertyFromFile("giAutomationMedTO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationMedTO = 30; //set to default if left blank in automation.propeties file
				else	
					giAutomationMedTO = Integer.parseInt(getPropertyFromFile("giAutomationMedTO",sFile));
				
				sTmp = getPropertyFromFile("giAutomationLongTO",sFile);
				if (sTmp == null || sTmp.isEmpty())
					giAutomationLongTO = 60; //set to default if left blank in automation.propeties file
				else
					giAutomationLongTO = Integer.parseInt(getPropertyFromFile("giAutomationLongTO",sFile));
				
				
				//Test browser
				gsAutomationTestBrowser = getPropertyFromFile("gsAutomationTestBrowser",sFile);
				if (gsAutomationTestBrowser == null || gsAutomationTestBrowser.isEmpty())
					gsAutomationTestBrowser = "Firefox";	//set to default if left blank in automation.propeties file
					
				
				//Determine if browser is Internet Explorer, and if so, what version to set a standard
				// value for the browser based on the value in Automation.properties
				//Default IE browser is IE6.  Look for most recent version of browser first
				if (gsAutomationTestBrowser.indexOf("Internet Explorer")>0| gsAutomationTestBrowser.indexOf("IE")>0){
					
					if (gsAutomationTestBrowser.contains("8")){
						gsAutomationTestBrowser = Platform.gsInternetExplorer8;
					}
					
					else if (gsAutomationTestBrowser.contains("7")){
						gsAutomationTestBrowser = Platform.gsInternetExplorer7;

					}
				}

			
				//Browser Profile Properties
				gsAutomationBrowserProfile = getPropertyFromFile("gsAutomationBrowserProfile",sFile);
				if (gsAutomationBrowserProfile.equals(""))
					gsAutomationBrowserProfile = null;
				
				
				//BrowserPath
				gsAutomationBrowserPath = getPropertyFromFile("gsAutomationBrowserPath",sFile);
				if (gsAutomationBrowserPath.equals(""))
					gsAutomationBrowserPath = null;

			}
			catch (Exception e)
			{
				ATUReports.add("Error loading automation Property settings in file: " + gsAutoPropFile + e.getMessage(), LogAs.FAILED, null);
			}

	}


	/**
	 * Returns a string containing the local host name of the test client system
	 * @return string containing the local host name of the test client system
	 */
	public static String getLocalClientName()
	{
		try{
		return java.net.InetAddress.getLocalHost().toString();}
		catch(Exception e){return "Could not get local host client name";}

	}
	
	/**
	 * Returns the specified property from the given properties file
	 * <p>
	 * @param String sKey - Property Key for value to return
	 * @param fileName properties file name to load
	 * @return String containing value of the specified key variable from the specified properties file 
	*/
	public static String getPropertyFromFile(String sKey, String fileName) {
		Properties prop = new Properties();
		File file = new File(fileName);
		String sPropVal = "";


		try{

		//if properties file does not exist
		if (!file.exists())
		{

			//System.out.println("WARNING: properties file: " + fileName + " does not exist. ");
			//check in alternate location
			String sAltPropFile = getPropertiesDir() + Platform.getFileSeparator() + FileIO.stripPath(fileName);
			//System.out.println("switching to alternate property file: " + sAltPropFile);

			//force to look for properties file in user's home folder
			file = new File(sAltPropFile);
			if (!file.exists())
			{
				//Try looking for properties file in automation project parent folder
				String sAltPropFile2 = getDatastoreDir() + FileIO.stripPath(fileName);	
				
				file = new File(sAltPropFile2);
				
				// if properties file still can't be found give up 
				if (!file.exists()){
					System.err.println("FATAL ERROR - property file does NOT exist. Tried to find the following files: " + fileName + " , " + sAltPropFile + " , " + sAltPropFile2);
					System.exit(-1);	//Stops all further execution
				}

			}
		}
		
		
		//assign property value
		try{
			loadFromFile(prop, file);
			sPropVal = prop.getProperty(sKey);
			}
		catch(Exception e)
		{
			e.printStackTrace();sPropVal = "";
		}

		return sPropVal;

		}
		//catch any other errors
		catch(Exception e){
      e.printStackTrace();
			//System.err.println(getStackTrace(e));
			//System.out.println("error - returned Property value = " + sPropVal);
			return sPropVal;
		}
		
	}
	
	
	/**
	 * Gets the directory into which you should put properties files
	 * @return String with properties directory path
	 */
	public static String getPropertiesDir()
	{
		return Platform.getUserHome();
	}

	/**
	 * Loads properties from a file. Properties are appended to the existing
	 * properties object.
	 * <p>
	 * @param p      properties to fill in
	 * @param file   file to read properties from
	 *
	 * @exception IOException
	 */
	public static void loadFromFile(Properties p, File file) throws IOException {
		if (p == null) {
			return;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			p.load(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * Returns a string containing the automation datastore directory
	 * @return string containing the automation datastore directory
	 */
	public static String getDatastoreDir()
	{
		return Platform.getCurrentProjectPath() + Platform.getFileSeparator();
	}
	
}
