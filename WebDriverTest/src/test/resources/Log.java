package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.swing.JOptionPane;

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

		/**Automation test engine. i.e SELENIUM, WEBDRIVER, etc.*/
		public static String gsAutomationTestEngine = "WEBDRIVER";

		/**Automation base path alternate for Mac OS X clean home directories...*/
		public static String gsAutoAltBasePath = Platform.getUserHome() + Platform.getFileSeparator() + "aci" + Platform.getFileSeparator() + "automation" + Platform.getFileSeparator();

		/**Automation base path*/
		public static String gsAutoBasePath = Platform.getUserHome() + Platform.getFileSeparator();

		/**Automation properties file name*/
		public static String gsAutoPropFile = gsAutoBasePath + "automation.properties";

		/**Automation suite statistic property file name*/
		public static String gsSuiteStatFile =  gsAutoBasePath + "suitestats.properties";

		/**Global string for automation directory*/
		public static String gsAutomationAutoPath = Platform.getCurrentProjectPath(); //(Platform.isWindows() ? getPropertyFromFile("gsAutomationAutoPath",gsAutoPropFile) : FileIOfuncs.pathExists(gsAutoAltBasePath)? gsAutoAltBasePath : Platform.getUserHome());

		/**Global string for automation result directory*/
		public static String gsAutomationAutoResultPath = Platform.getCurrentProjectPath() + "results" + Platform.getFileSeparator() ;
		
		/**Global string for automation result file suffix*/
		public static String gsAutomationAutoResultFileSuffix = ".txt";

		/**Global string for automation result image suffix*/
		public static String gsAutomationAutoResultErrorImageSuffix = ".jpg";
		
		/**Global string for automation Test Data Spreadsheet suffix*/
		public static String gsAutomationTestDataSpreadsheetSuffix=".xls";
		
		/**Global string for old Excel .xls file extension*/
		public static String gsAutomationExcelXLSExt=".xls";
		
		/**Global string for Google Excel .xlsx file extension*/
		public static String gsAutomationExcelXLSXExt=".xlsx";

		/**Global string for automation support documents path*/
		public static String gsAutomationAutoSupportDocs = (Platform.isLinux() ? gsAutomationAutoPath : "c:" + Platform.getFileSeparator());

		/**Global strings for Browser Profile file*/
		public static String gsAutomationBrowserProfile;
		
		/**Global strings for Browser Path*/
		public static String gsAutomationBrowserPath;

		//Andrew Quinan's code
		///**Allows modules to differentiate their gsAutomationAutoSupportDocs path*/
	    //public static String gsModuleName = "Testing123";


		//Global script test server/client info

		/**Global string for test server*/
		public static String gsTestBaseServer; // = "maple.iris.com";

		/**Global string for test server URL*/
		public static String gsAutomationBaseURL = "about:blank";

		/**Global string for test browser*/
		public static String gsAutomationTestBrowser; // = "Internet Explorer 6.0";

		/**Global string for user login user name*/
		public static String gsAutomationUsername; // = "wpsadmin";

		/**Global string for user login password*/
		public static String gsAutomationPassword; // = "wpsadmin";

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

		//Log Type formats

		/**Global default Pass-Fail, action description, time, elapsed time output Log type*/
		public static final int LOGTYPE_TIME_PASS_FAIL = 1;

		/**Global Script output Log type*/
		public static final int LOGTYPE_SCRIPT_OUTPUT = 2;

		/**Global Simple output Log type*/
		public static final int LOGTYPE_SIMPLE = 3;

		/**Global No output Log type*/
		public static final int LOGTYPE_NONE = 4;

		/**Global Error output Log type*/
		public static final int LOGTYPE_ERROR_OUTPUT = 5;

		/**Global Error output Log type*/
		public static final int LOGTYPE_DEBUG_INFO = 6;

		/**Global log type - used to add html information in result html log*/
		public static final int LOGTYPE_HTML = 7;

		/**Global Fail info output log type*/
		public static final int LOGTYPE_FAIL = 8;

		/**Global command trace back info output log type*/
		public static final int LOGTYPE_APPEND_INVOCATION = 9;

		//global Default Log Type

		/**Global default Log type*/
		public static int giAutomationLogType = LOGTYPE_TIME_PASS_FAIL;


		//global Error Handling Options

		/**Global default Image capture output option*/
		public static boolean gbAutomationImageCapture = false;
		
		/**Global default Error Handling Stack Trace output Option*/
		public static boolean gbAutomationStackTrace = true;

		/**Global default Error Handling Image capture output option*/
		public static boolean gbAutomationErrorImageCapture = true;

		/**Global default Close browsers upon test script completion option*/
		public static boolean gbAutomationCloseTestBrowserAtScriptCompletion = true;
		

		//Global testcase logging information

		/**Global int for tracking number of test cases executed*/
		public static int giScriptCounter;

		/**Global boolean checking for failed script*/
		public static boolean gbScriptFailed = false;

		/**Global int for tracking number of failed scripts*/
		public static int giScriptFailedCounter;

		/**Global int for tracking number of passed scripts*/
		public static int giScriptPassedCounter;

		/**Global int for tracking number of test cases executed*/
		public static int giTestCaseCounter;

		/**Global int for tracking number of failures flagged in the entire script*/
		public static int giErrorCounter;

		/**Global boolean checking for failed test case*/
		public static boolean gbTestCaseFailed = false;

		/**Global int for tracking number of failed test cases*/
		public static int giTestCaseFailCounter;

		/**Global int for tracking number of test actions*/
		public static int giTestActionCounter;

		/**Global string for Test case header*/
		public static String gsTestcaseHeader = "* Testcase - Start - ";

		/**Global string for Script header*/
		public static String gsScriptHeader = "* Script Name:           ";

		/**Global string for Script header*/
		public static String gsScriptFooter = "* Elapsed Time:";

		/**Global string for current date and time*/
		public static String gsCurrentDateAndTime;

		/**Global string for Total Script header*/
		public static String gsTotalScriptsExecuted = "* Total Scripts Executed:";

		/**Global string for Total Scripts (suite) Elapsed time*/
		public static String gsTotalElapsedTime = "* Total Elapsed Time:";

		/**Global string for Total Script header*/
		public static String gsTestcasesExecuted = "* Testcases Executed:";

		/**Global string for Total Script header*/
		public static String gsTestcaseFooter="* Testcases Executed:";
		

		//Global script header information

		/**Global string for script author*/
		public static String gsScriptAuthor;

		/**Global string for script description*/
		public static String gsScriptDescription;

		/**Global string for script test area*/
		public static String gsScriptTestArea;

		/**Global string for script name*/
		public static String gsScriptName;

		//result formatting variables
		/**Automation html formatted result file name*/
		public static String gsPassFailFile;

		/**Automation html formatted result file name*/
		public static String gsHtmlResultFileName = "auto_results";

		/**Global long for script start time*/
		public static long glStartTime = 0;

		/**Global string for line separator*/
		public static String gsNewline = System.getProperty("line.separator");

		/**Global string for automation result file*/
		public static String gsResultFile;

		/**Global string for automation suite result file*/
		public static String gsSuiteResultFile = null;

		/**Global string for default Browser Title*/
		public static String gsAutomationBrowserBaseStatePage = "about:blank";

		/**Global int for Base State*/
		public static int giAutomationBaseState = 0;
		
		/** Global variable to store Test Suite Start time*/
		public static String gsSuiteStartTime;

		/**Global boolean for tracking if suite or script is running*/
		public static boolean gbIsSuite = false;

		/**Global string for tracking if suite or script is running*/
		public static String gsAutomationResultViewerApp;

		/**Global string for image marker in log output.*/
		public static String gsImageMarker = "Image Saved to: ";

		/**Global string for FAIL item in pass/fail portion of log output.*/
		public static String gsFail = "FAIL - ";

		/**Global string for PASS item in pass/fail portion of log output.*/
		public static String gsPass = "PASS - ";
		
		/** Global string for passed test case footer */
		public static String gsTestcasePassFooter = "Testcase - " + gsPass;

		/** Global string for failed test case footer */
		public static String gsTestcaseFailFooter = "Testcase - " + gsFail;

		/**Global string for Debug info in log output.*/
		public static String gsDebug = "DEBUG - ";

		/**Global string for Warning info in log output.*/
		public static String gsWarning = "WARNING - ";

		/**Global string for Abort signature in log output.*/
		public static String gsAbortSign = "**** ABORTING ****";

		/**Global int for Test Level.*/
		public static int giAutomationTestLevel;

		protected static int iCurrentScriptTestCaseNumber;
		protected static int numScriptsRun = 0;


		//Generic variables the end user can customize and use for any scripting purpose

		/**Global string array glsArgs - generic string array variable to store any number of end-user specific arguments.
		 * User specifies args in the Automation.properties glsAutomationArgs variable like this (comma separated)
		 * glsAutomationArgs=John Smith, Fred Johnson, Steve Jones
		 * You can enter as many arguments as you want by simply separating the arguments with a comma.
		 * The user can then access these array variables in scripts like this:
		 * String sMyUser = glsAutomationArgs[0];  //sMyUser would be equal to "John Smith" (i.e.
		 * glsAutomationArgs[0]=="John Smith" and glsAutomationArgs[1]=="Fred Johnson" and glsAutomationArgs[2]=="Steve Jones")  */
		public static String[] glsAutomationArgs;
		public static String gsArgs;


		/**
		 * Global counter variables for ROI execution counter 
		 */
		public static int iROICounter = 0;
		public static String gsExecutionROICounterSubDir = "execution_counter" + Platform.getFileSeparator();



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

		/**
		 * Initializes test script and system variables at script startup
		 */
		public static void initialize()
		{
			
			
			//initialize automation global variables 
			try {
				autoSetup(true);
			} catch (Exception e) {
				errorHandler("error occurred at script initialization", e);
			}
			
			//set Automation Tool Engine
			if(gsAutomationTestEngine.toLowerCase().equals(Platform.SELENIUM.toLowerCase()))
			{
				Platform.setEngine(Platform.SELENIUM);
				Log.logScriptInfo("Automation Engine: SELENIUM RC");
				
				//Start Selenium Server
				Platform.startSeleniumServer();
				
			}
			else{
				Platform.setEngine(Platform.WEBDRIVER);
				Log.logScriptInfo("Automation Engine: WEBDRIVER");
				
			}
			
			
			
			

		}

		
		/**
		 * Executes script cleanup and test metric gathering functions upon script termination
		 */
		public static void terminate()
		{

			try{

			//if option is true then close test browsers otherwise leave browsers up
			if (Log.gbAutomationCloseTestBrowserAtScriptCompletion == true){
				Browser.close();
				Browser.stop();
				}

			//Get test metrics and report results
			autoCleanup(true);
			}
			catch(Exception e)
				{
				errorHandler("error occurred at script termination",e);
				}

			//if option is true then send results to SCTM web server
			copyResultsToSilkCentral( );

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


			giScriptCounter=1;

			//Setup and initialize automation environment
			DateFormat dtformat = DateFormat.getDateInstance();
			DateFormat tmformat = DateFormat.getTimeInstance();


			//Get system specific global variables
			loadAutoPropSettings(gsAutoPropFile);

			//create unique result file based on script name and date
			gsResultFile = gsAutomationAutoResultPath + gsScriptName + "_" + DateTime.genDateBasedRandVal() + gsAutomationAutoResultFileSuffix;
			FileIO.writeFileContentsByUTF8(gsResultFile, "");

			//assign current date and time to global variable
			gsCurrentDateAndTime = dtformat.format(new Date()) + " " + tmformat.format(new Date());

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


			//Write Script Header info to result log
			if (bShowHeaderInfo == true)
			{
				logScriptInfo("******************************************************************************", 3);
				logScriptInfo("* Script Name:           " + gsScriptName, 3);
				logScriptInfo("* Script Author:         " + gsScriptAuthor, 3);
				logScriptInfo("* Script Description:    " + gsScriptDescription, 3);
				logScriptInfo("* Script Test Area:      " + gsScriptTestArea, 3);
				logScriptInfo("* Script Result Folder:  " + gsAutomationAutoResultPath, 3);
				logScriptInfo("* Test Data Folder:      " + gsAutomationAutoSupportDocs, 3);
				logScriptInfo("* Test Client:           " + getLocalClientName(), 3);
				logScriptInfo("* Test Client OS:        " + Platform.getOSNameAndVersion(),3);
				logScriptInfo("* Core Automation:       " + Platform.getVersion(), 3);
				logScriptInfo("* Test Browser:          " + myBrowserDisplayName, 3);
				logScriptInfo("* Start Date and Time:   " + gsCurrentDateAndTime, 3);
				logScriptInfo("******************************************************************************", 3);
			}

			//Start script clock
			setStartTime();


			//Clear giScriptPassedCounter
			giScriptPassedCounter = 0;

			//Clear giScriptFailedCounter
			giScriptFailedCounter = 0;

			//Clear testcase counter
			giTestCaseCounter = 0;

			//Clear error counter
			giErrorCounter = 0;

			//Clear failed testcase counter
			giTestCaseFailCounter = 0;

			//Set testcase failed boolean to false
			gbTestCaseFailed = false;

			//Set script failed boolean to false
			gbScriptFailed = false;

			//Clear test action counter
			giTestActionCounter = 0;


		}



		/**
		* Logs script information to multiple output sources. Must explicitly state which log format type to use. Writes info to log in any of a number of specified formats <p>
		* <p>
		* @param sLog Log message
		* @param iType type of log information (i.e. 1=PASS/FAIL, 2=SCRIPT OUTPUT, etc.)
		*/
		public static void logScriptInfo(String sLog, int iType)
		{

			//Overloaded to explicitly state which log format type to use. Writes info to log in any of a number of specified formats
			DateFormat tmformat = DateFormat.getTimeInstance();
			String s = "";

			switch (iType)
			{
				case LOGTYPE_APPEND_INVOCATION: // Verbose - append invocation information
					s = tmformat.format(new Date()) + " - " + DateTime.getElapsedTime(glStartTime) + " - " + gsPass + sLog; //format
					giTestActionCounter++; //add one to test action counter
					break;
				case LOGTYPE_TIME_PASS_FAIL : //Verbose - PASS-FAIL format output
					s = tmformat.format(new Date()) + " - " + DateTime.getElapsedTime(glStartTime) + " - " + gsPass + sLog; //format
					giTestActionCounter++; //add one to test action counter
					break;
				case LOGTYPE_SCRIPT_OUTPUT : //Verbose - Manual script format output
					s = "[ ] - " + sLog; //format
					giTestActionCounter++; //add one to test action counter
					break;
				case LOGTYPE_SIMPLE : //Verbose - Log only what is entered
					s = sLog;
					break;
				case LOGTYPE_NONE : //Do NOT log actions info to log file
					break;
				case LOGTYPE_ERROR_OUTPUT :
					s = sLog;
					System.err.println(s);
					appendContentToResultFile(s); // txt log
					break;
				case LOGTYPE_DEBUG_INFO :
					s = tmformat.format(new Date()) + " - " + DateTime.getElapsedTime(glStartTime) + " - " + gsDebug + sLog; //format
					break;
				// Real time log implementation items
				case LOGTYPE_HTML:
					s = sLog;
					break;
				case LOGTYPE_FAIL:
					s = tmformat.format(new Date()) + " - "
						+ DateTime.getElapsedTime(glStartTime) + " - " + gsFail + sLog; // format
					giTestActionCounter++; // add one to test action counter
					break;

			}
			
			
			//abort script, set fail vars
			if(s.endsWith(gsAbortSign)){
				if(giTestCaseCounter>0) gbTestCaseFailed = true;
				if(giScriptCounter>0) gbScriptFailed = true;
			}

			//write results to text file (gsResultFile) and to console
			if (iType != LOGTYPE_NONE && iType != LOGTYPE_ERROR_OUTPUT) {
				if (iType != LOGTYPE_HTML) {
					System.out.println(s); // console
				}

				if(iType == LOGTYPE_APPEND_INVOCATION){
					s = s + getInvocationInfo();
				}

				appendContentToResultFile(s); // txt log
			}

		}

		/**
		 * logs a snapshot of the desktop to the HTML result log and saves the image as a file in the designated result directory 
		 * <p>
		 * @param description description for generated snapshot.
		 * @param bError - whether to generate snapshot for error or not.
		 */
		public static void logScreenCapture(String description, boolean bError){
			try{
				// To avoid previous image file form being over written, use DateTime.getFormattedDateTime(new Date().getTime(), "MMddHHmmssSSS") not genDateBasedRandVal().
				String fileName = gsAutomationAutoResultPath + gsScriptName + "_" + DateTime.getFormattedDateTime(new Date().getTime(), "MMddHHmmssSSS") + gsAutomationAutoResultErrorImageSuffix;
				
				//add description in log for captured image
				if (bError == false){
					if (description != null){ 
						logScriptInfo("Image Description: " + description);}
				}
				
				
				Images.captureScreen(gsScriptName, description, fileName, bError);
				logScriptInfo(gsImageMarker + fileName, bError?LOGTYPE_ERROR_OUTPUT:LOGTYPE_TIME_PASS_FAIL);			
			}catch(Exception e){
				//DO NOT use errorHandler method here or will fall in infinite invoking, because this method is invoked in errorHandler method.
				System.err.println("Exception Log.logScreenCapture(TestObject, String, boolean): "+e.getMessage());
				return;
			}
		}

		/**
		 * log screen snapshot with description. used for normal purpose.
		 * @param description of screen snapshot.
		 */
		public static void logScreenCapture(String description){
			logScreenCapture(description,false);
		}


		/**
		 * log screen snapshot without description
		 */
		public static void logScreenCapture(){
			logScreenCapture(null,false);
		}


		/**
		* Logs script information to multiple output sources. Overloaded to simplify to single parameter.
		* Forces use of global Log Type giAutomationLogType parameter.
		* @param sLog log message
		*/
		public static void logScriptInfo(String sLog)
		{
		//Overloaded to simplify to single parameter. Forces use of global Log Type giAutomationLogType parameter for Log formatting.
		logScriptInfo(sLog,giAutomationLogType);
		}


		/**
		* Logs debug script information to multiple output sources.
		* Forces use of global Log Type giAutomationLogType parameter set to #6 (equals debug info).
		* @param sLog log message
		*/
		public static void logDebugInfo(String sLog)
		{
		//Forces use of Debug Log Type giAutomationLogType parameter for Logging of debug info.
		if (giAutomationLogType == 6)
			logScriptInfo(sLog,6);
		}


//		/**
//		* Gets elapsed time from the specified start time. Returns string in "HH:mm:ss:SSS" format. <p>
//		* @param startTime the starting time
//		* @return A string containing the time difference between the script start time and the script elapsed time.
//		*/
//		public static String getElapsedTime(long startTime)
//		{
//			//end time long format
//			long endTime = System.currentTimeMillis();
//			//System.out.println("End Time:   " + String.valueOf(endTime));
	//
//			//elapsed time long format
//			long time = endTime - startTime;
//			//System.out.println("Elapsed Time: " + String.valueOf(time));
	//
//			//Format the Date time information
//			//String s = getFormattedDateTimeForElapsedTime(time, "HH:mm:ss:SSS");
//			String s = DateTime.getFormattedDateTimeForElapsedTime(time);
	//
//			return s;
//		}

//		/**
//		* returns long data type of elapsed script time to do arithmetic timing operations   <p>
//		* @param startTime the starting time
//		* @return Long containing the time difference between the script start time and the script elapsed time.
//		*/
//		public static long getElapsedTimeLong(long startTime)
//		{
//			//end time long format
//			long endTime = System.currentTimeMillis();
//			//System.out.println("End Time:   " + String.valueOf(endTime));
	//
//			//elapsed time long format
//			long time = endTime - startTime;
//			//System.out.println("Elapsed Time: " + String.valueOf(time));
	//
//			return time;
//		}




		/**
		* Sets script clock start time - by default this is started automatically at script startup when the
		* autoSetup() function is used. The function sets a global variable called glStartTime which is then
		* used as an argument to the getElapsedTime(glStartTime) function which is called in the logScriptInfo() function   <p>
		*/
		public static void setStartTime()
		{
			long lTime = System.currentTimeMillis();
			glStartTime = lTime;
		}

//		/**
//		* returns formatted String version of date supplied as long  <p>
//	    * @param dDateTime date and time in long data type
//		* @param sDateTimeFormat The string format you would like the date and time to be displayed as. (i.e. "HH:mm:ss:SSS")
//		* @return String containing the formatted date and time of the given long date and time.
//		*/
//		public static String getFormattedDateTime(long dDateTime, String sDateTimeFormat)
//		{
//			SimpleDateFormat tmformat = new SimpleDateFormat(sDateTimeFormat);
	//
//			Date tm = new Date(dDateTime);
	//
//			String s = tmformat.format(tm);
	//
//			return s;
//		}

		
//		/**
//		* returns formatted String version of date supplied as long  <p>
//		* @param lElapsedTime date and time in long data type of the elapsed time
//		* @return String containing the formatted date time of the given long datetime elspased.
//		*/
//		public static String getFormattedDateTimeForElapsedTime(long lElapsedTime) {
//			   long hr = TimeUnit.MILLISECONDS.toHours(lElapsedTime);
//			   long min = TimeUnit.MILLISECONDS.toMinutes(lElapsedTime - TimeUnit.HOURS.toMillis(hr));
//			   long sec = TimeUnit.MILLISECONDS.toSeconds(lElapsedTime - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
//			   long ms = TimeUnit.MILLISECONDS.toMillis(lElapsedTime - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) -                   TimeUnit.SECONDS.toMillis(sec));
//			   return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
//		}
//			
		
		

		/**
		* Logs script testcase information and other metrics info <p>
		* @param bShowResultFooter true to show result metrics in the footer section of the log results
		*/
		public static void autoCleanup(boolean bShowResultFooter)
		{

			// Check if script contained any failures
			if (gbTestCaseFailed == true) {
				giTestCaseFailCounter++;

				}

			if (gbScriptFailed == false)
			{
				giScriptPassedCounter=1;
			}
			else
			{
				giScriptFailedCounter=1;
				giScriptPassedCounter=0;
			}


			int iTestCasePassedCounter;

			//calculate number of passed test cases
			iTestCasePassedCounter = (giTestCaseCounter - giTestCaseFailCounter);

			if (giTestCaseCounter < iCurrentScriptTestCaseNumber) {
				numScriptsRun += (iCurrentScriptTestCaseNumber - giTestCaseCounter);
			}

			if (bShowResultFooter)
			{
			//calculate percentages
			double dPrctPassed = (int)(((double) iTestCasePassedCounter / (double) giTestCaseCounter) * 10000)/100.0;
			double dPrctFailed = (int)(((double) giTestCaseFailCounter / (double) giTestCaseCounter) * 10000)/100.0;

			//store elapsed time
			String sElapsedTime = DateTime.getElapsedTime(glStartTime);
			long lElapsedTime = DateTime.getElapsedTimeLong(glStartTime);
			
			DateFormat dtformat = DateFormat.getDateInstance();
			DateFormat tmformat = DateFormat.getTimeInstance();
			
			//Write Script Testcase Information to result log
			logScriptInfo("******************************************************************************", 3);
			logScriptInfo("* Testcases Executed:              " + giTestCaseCounter, 3);
			logScriptInfo("* Testcases Passed:                " + iTestCasePassedCounter, 3);
			logScriptInfo("* Testcases Failed:                " + giTestCaseFailCounter, 3);
			logScriptInfo("* Percent Testcases Passed:        " + dPrctPassed + "%", 3);
			logScriptInfo("* Percent Testcases Failed:        " + dPrctFailed + "%", 3);
			logScriptInfo("* Number of Test Actions Executed: " + giTestActionCounter, 3);
			logScriptInfo("* Number of Errors found:          " + giErrorCounter, 3);
			logScriptInfo("* End Date and Time:               " + dtformat.format(new Date()) + " " + tmformat.format(new Date()),3);
			logScriptInfo("* Elapsed Time:                    " + sElapsedTime, 3);
			logScriptInfo("******************************************************************************", 3);

			
			//open temporary file to tally test statistics for suite results
			String sTmp;
			int inScriptCounter = 0;
			int inScriptPassedCounter = 0;
			int inScriptFailedCounter = 0;

			int inTestCaseCounter = 0;
			int inTestCasePassedCounter = 0;
			int inTestCaseFailCounter = 0;
			int inTestActionCounter = 0;
			int inErrorCounter = 0;
			long inElapsedTime = 0;

			try
			{
				FileInputStream in = new FileInputStream(gsSuiteStatFile);
				Properties settings = new Properties();
				settings.load(in);

				sTmp = settings.getProperty("ScriptCounter");
				inScriptCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ScriptPassedCounter");
				inScriptPassedCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ScriptFailedCounter");
				inScriptFailedCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ScriptPrctPassed");
				//inScriptPrctPassed = Double.parseDouble(sTmp);

				sTmp = settings.getProperty("ScriptPrctFailed");
				//inScriptPrctFailed = Double.parseDouble(sTmp);


				sTmp = settings.getProperty("TestCaseCounter");
				inTestCaseCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("TestCasePassedCounter");
				inTestCasePassedCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("TestCaseFailCounter");
				inTestCaseFailCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("PrctPassed");
				//inPrctPassed = Double.parseDouble(sTmp);

				sTmp = settings.getProperty("PrctFailed");
				//inPrctFailed = Double.parseDouble(sTmp);

				sTmp = settings.getProperty("TestActionCounter");
				inTestActionCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ErrorCounter");
				inErrorCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ElapsedTime");
				inElapsedTime = Long.parseLong(sTmp);

				in.close();
			}
			catch (Exception e)
			{
				// commenting this out as the next code will create the file...
				//logScriptInfo("Error loading suite statistic INI variables in file: " + gsSuiteStatFile, 5);
			}

			//tally and update suite statistics file
			try
			{
				FileOutputStream out = new FileOutputStream(gsSuiteStatFile);
				Properties settings = new Properties();

				settings.put("ScriptCounter", String.valueOf(inScriptCounter + 1));
				settings.put("ScriptPassedCounter", String.valueOf(inScriptPassedCounter + giScriptPassedCounter));
				settings.put("ScriptFailedCounter", String.valueOf(inScriptFailedCounter + giScriptFailedCounter));
				settings.put("ScriptPrctPassed", String.valueOf((int)((((double) inScriptPassedCounter + (double) giScriptPassedCounter) / ((double) inScriptCounter + (double) giScriptCounter)) * 10000)/100.0));
				settings.put("ScriptPrctFailed", String.valueOf((int)((((double) inScriptFailedCounter + (double) giScriptFailedCounter) / ((double) inScriptCounter + (double) giScriptCounter)) * 10000)/100.0));

				settings.put("TestCaseCounter", String.valueOf(inTestCaseCounter + giTestCaseCounter));
				settings.put("TestCasePassedCounter", String.valueOf(inTestCasePassedCounter + iTestCasePassedCounter));
				settings.put("TestCaseFailCounter", String.valueOf(inTestCaseFailCounter + giTestCaseFailCounter));
				settings.put("PrctPassed", String.valueOf((int)((((double) inTestCasePassedCounter + (double) iTestCasePassedCounter) / ((double) inTestCaseCounter + (double) giTestCaseCounter)) * 10000)/100.0));
				settings.put("PrctFailed", String.valueOf((int)((((double) inTestCaseFailCounter + (double) giTestCaseFailCounter) / ((double) inTestCaseCounter + (double) giTestCaseCounter)) * 10000)/100.0));
				settings.put("TestActionCounter", String.valueOf(inTestActionCounter + giTestActionCounter));
				settings.put("ErrorCounter", String.valueOf(inErrorCounter + giErrorCounter));
				settings.put("ElapsedTime", String.valueOf(inElapsedTime + lElapsedTime));

				//Close out properties file
				settings.store(out, "");
				out.close();

			}
			catch (IOException ioe)
			{
				logScriptInfo("Error saving suite statistic property variables in file: " + gsSuiteStatFile, 5);
			}

			//append results to suite
			if (gsSuiteResultFile != null)
			{
				gsPassFailFile = gsAutomationAutoResultPath + gsHtmlResultFileName;
				writePassFailResultsToHtmlFormat(gsSuiteResultFile,gsPassFailFile);

			}
			else{
			//write out HTML formatted suite results file
			gsPassFailFile = gsAutomationAutoResultPath + gsHtmlResultFileName;
			writePassFailResultsToHtmlFormat(gsResultFile, gsPassFailFile);

			}

			//display log results in specified Viewer when test script completes
			if (gbIsSuite == false)	//if script is part of suite execution do not display viewer
				{
				if (gsAutomationResultViewerApp != null)
					try{
						String[] lsViewer = new String[] {gsAutomationResultViewerApp,gsAutomationAutoResultPath+"auto_results_details.htm"};
						//System.out.println(lsViewer[0]);
						//System.out.println(lsViewer[1]);
						Runtime.getRuntime().exec(lsViewer);
						}
						catch(Exception e){
							System.err.println("Error loading alternative Result Viewer application");
							e.printStackTrace();
							
						}
				}

			}

		}

		
		/**
		* Logs and tracks testcase information <p>
		* @param sDesc Description of testcase
		*/
		public static void startTestCase(String sDesc)
		{
			//calculate any previous pass-fail metrics
			autoCleanup(false);
			
			
			//Take screenshot if gbAutomationImageCapture=true
			if (gbAutomationImageCapture){
				Log.logScreenCapture();
			}

			// Add one to testcase counter
			giTestCaseCounter++;


			DateFormat dtformat = DateFormat.getDateInstance();
			DateFormat tmformat = DateFormat.getTimeInstance();

			//Display testcase description information
			logScriptInfo("==============================================================================", 3);
			logScriptInfo(gsTestcaseHeader + sDesc + " - Date: " + dtformat.format(new Date()) + "  " + tmformat.format(new Date()), 3);
			logScriptInfo("==============================================================================", 3);

			//reset testcase failed to false
			gbTestCaseFailed = false;
			//System.out.println(gbTestCaseFailed);
			
			
			
		}

		/**
		* Dumps non-exception based error information out to console, text result and HTML result logs together with screen capture if 
		* screen capture in automation log is set to true <p>
		* @param sLog The error description text to write out to the log file
		* @return
		*/
		public static void errorHandler(String sLog)
		{
			errorHandler (sLog,null);
		}
		

		/**
		* errorHandler function Dumps exception based error information out to console, text result and HTML result logs <p>
		* @param sLog The text you want to write out to the log file
		* @param e Exception error info
		*/
		public static void errorHandler(String sLog, Exception e)
		{

			DateFormat tmformat = DateFormat.getTimeInstance();
			
			//spacer
			logScriptInfo("", 3);

			//Log error to output result logs
			String s = tmformat.format(new Date()) + " - " + DateTime.getElapsedTime(glStartTime) + " - " + gsFail + sLog;
			System.err.println(s);
			giTestActionCounter++; //add one to test action counter
			// append content to result file.
			appendContentToResultFile(s);
			
			
			//Add 1 to error counter and set testcase and script failed booleans to true
			giErrorCounter++;
			if(giTestCaseCounter > 0){
				gbTestCaseFailed = true;

			}
			if(giScriptCounter > 0){
				gbScriptFailed = true;
			}


			//Get stack and error info
			logScriptInfo("", 3);
			logScriptInfo("Stack Trace:", 5);
			if (e != null){
				System.err.println(e.getMessage());
			}
			logScriptInfo(getStackTrace(e), 5);
			
			
			//Get screen bitmap
			if (gbAutomationErrorImageCapture == true)
			{
				logScreenCapture(sLog,true);
			}

			//spacer
			logScriptInfo("", 3);

		}

		/**
		* Gets stack trace information and returns it as a string <p>
		* @param e Exception
		* @return String containing stack trace
		*/
		public static String getStackTrace(Exception e)
		{
			if (e == null)
			{
				Exception f = new Exception();
				e = f;
			}

			StringWriter sw = null;
			PrintWriter pw = null;
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);

			return sw.toString();
		}

		
		/**
		* Initialize suite statistics file <p>
		*/
		public static void initializeSuiteStats()
		{


			//set boolean gbIsSuite to true. This means a suite is running (not a single script)
			gbIsSuite = true;

			//get suite file name
			gsScriptName = getScriptName();
			try
			{
				setScriptName(gsScriptName);
			}
			catch (Exception e) //in case the calling script doesn't have a matching script definition in the resources directory for some reason, just bail
			{
				gsScriptName = "suite";
				setScriptName(gsScriptName);
			}
			
			//Store Suite Start time
			DateFormat dtformat = DateFormat.getDateInstance();
			DateFormat tmformat = DateFormat.getTimeInstance();
			gsSuiteStartTime = dtformat.format(new Date()) + " " + tmformat.format(new Date());

			//Get system specific global variables
			loadAutoPropSettings(gsAutoPropFile);

			//create and initialize unique suite result file based on script name and date
			String sSuiteFile = gsScriptName;
			gsSuiteResultFile = gsAutomationAutoResultPath + sSuiteFile + "_" + DateTime.genDateBasedRandVal() + gsAutomationAutoResultFileSuffix;
			FileIO.writeFileContentsByUTF8(gsSuiteResultFile, "");

			//Initialize suite stats
			try
			{
				FileOutputStream out = new FileOutputStream(gsSuiteStatFile);
				Properties settings = new Properties();

				settings.put("ScriptCounter", String.valueOf(0));
				settings.put("ScriptPassedCounter", String.valueOf(0));
				settings.put("ScriptFailedCounter", String.valueOf(0));
				settings.put("ScriptPrctPassed", String.valueOf(0));
				settings.put("ScriptPrctFailed", String.valueOf(0));

				settings.put("TestCaseCounter", String.valueOf(0));
				settings.put("TestCasePassedCounter", String.valueOf(0));
				settings.put("TestCaseFailCounter", String.valueOf(0));
				settings.put("PrctPassed", String.valueOf(0));
				settings.put("PrctFailed", String.valueOf(0));
				settings.put("TestActionCounter", String.valueOf(0));
				settings.put("ErrorCounter", String.valueOf(0));
				settings.put("ElapsedTime", String.valueOf(0));

				//Close out properties file
				settings.store(out, "");
				out.close();

			}
			catch (IOException ioe)
			{
			errorHandler("Error saving suite statistic INI variables in file: " + gsSuiteStatFile, ioe);
			}



		}



		/**
		* Displays a banner in the result log output
		* @param sBanner message to display in banner
		*/
		public static void logBanner(String sBanner)
		{
		//logs a banner like this in log output:
		//==============================================================================
		//My Test Info
		//==============================================================================
		String sLineSeparator = "==============================================================================";
		logScriptInfo(sLineSeparator, 3);
		logScriptInfo(sBanner,3);
		logScriptInfo(sLineSeparator, 3);
		}


		/**
		* Logs test suite statistics <p>
		*/
		public static void logSuiteStats()
		{

			StringBuffer sSummary = new StringBuffer();

			//open temporary file to tally test statistics for suite results
			String sTmp;


			int inScriptCounter = 0;
			int inScriptPassedCounter = 0;
			int inScriptFailedCounter = 0;
			double inScriptPrctPassed = 0;
			double inScriptPrctFailed = 0;


			int inTestCaseCounter = 0;
			int inTestCasePassedCounter = 0;
			int inTestCaseFailCounter = 0;
			double inPrctPassed = 0;
			double inPrctFailed = 0;
			int inTestActionCounter = 0;
			int inErrorCounter = 0;
			long inElapsedTime = 0;

			try
			{
				FileInputStream in = new FileInputStream(gsSuiteStatFile);
				Properties settings = new Properties();
				settings.load(in);


				sTmp = settings.getProperty("ScriptCounter");
				inScriptCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ScriptPassedCounter");
				inScriptPassedCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ScriptFailedCounter");
				inScriptFailedCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ScriptPrctPassed");
				inScriptPrctPassed = Double.parseDouble(sTmp);

				sTmp = settings.getProperty("ScriptPrctFailed");
				inScriptPrctFailed = Double.parseDouble(sTmp);


				sTmp = settings.getProperty("TestCaseCounter");
				inTestCaseCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("TestCasePassedCounter");
				inTestCasePassedCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("TestCaseFailCounter");
				inTestCaseFailCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("PrctPassed");
				inPrctPassed = Double.parseDouble(sTmp);

				sTmp = settings.getProperty("PrctFailed");
				inPrctFailed = Double.parseDouble(sTmp);

				sTmp = settings.getProperty("TestActionCounter");
				inTestActionCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ErrorCounter");
				inErrorCounter = Integer.parseInt(sTmp);

				sTmp = settings.getProperty("ElapsedTime");
				inElapsedTime = Long.parseLong(sTmp);
				
				DateFormat dtformat = DateFormat.getDateInstance();
				DateFormat tmformat = DateFormat.getTimeInstance();

				in.close();

				//Get Suite Test Summary information
				sSummary.delete(0, sSummary.length()); //clear stringbuffer
				sSummary.append("******************************************************************************" + gsNewline); //inserted gsNewline instead of /n
				sSummary.append("* Test Suite Summary\n");
				sSummary.append("******************************************************************************" + gsNewline);
				sSummary.append("* Total Scripts Executed:                " + inScriptCounter + gsNewline);
				sSummary.append("* Total Scripts Passed:                  " + inScriptPassedCounter + gsNewline);
				sSummary.append("* Total Scripts Failed:                  " + inScriptFailedCounter + gsNewline);
				sSummary.append("* Total Percent Scripts Passed:          " + inScriptPrctPassed + "%" + gsNewline);
				sSummary.append("* Total Percent Scripts Failed:          " + inScriptPrctFailed + "%" + gsNewline);
				sSummary.append("* Total Testcases Executed:                " + inTestCaseCounter + gsNewline);
				sSummary.append("* Total Testcases Passed:                  " + inTestCasePassedCounter + gsNewline);
				sSummary.append("* Total Testcases Failed:                  " + inTestCaseFailCounter + gsNewline);
				sSummary.append("* Total Percent Testcases Passed:          " + inPrctPassed + "%" + gsNewline);
				sSummary.append("* Total Percent Testcases Failed:          " + inPrctFailed + "%" + gsNewline);
				sSummary.append("* Total Number of Test Actions:            " + inTestActionCounter + gsNewline);
				sSummary.append("* Total Number of Errors found:            " + inErrorCounter + gsNewline);
				sSummary.append("* End Date and Time:                       " + dtformat.format(new Date()).toString() + " " + tmformat.format(new Date()).toString());
				sSummary.append("* Total Elapsed Time:                      " + DateTime.getFormattedDateTimeForElapsedTime(inElapsedTime) + gsNewline);		//getFormattedDateTime
				//sSummary.append("* Total Elapsed Time:                      " + getElapsedTime(inElapsedTime) + gsNewline);
				sSummary.append("******************************************************************************" + gsNewline);


				gsResultFile = null;	//the following content is only for SUITE result files

				//Display suite totals
				logScriptInfo("******************************************************************************", 3);
				logScriptInfo("* Total Scripts Executed:                " + inScriptCounter, 3);
				logScriptInfo("* Total Scripts Passed:                  " + inScriptPassedCounter, 3);
				logScriptInfo("* Total Scripts Failed:                  " + inScriptFailedCounter, 3);
				logScriptInfo("* Total Percent Scripts Passed:          " + inScriptPrctPassed + "%", 3);
				logScriptInfo("* Total Percent Scripts Failed:          " + inScriptPrctFailed + "%", 3);
				logScriptInfo("* Total Testcases Executed:                " + inTestCaseCounter, 3);
				logScriptInfo("* Total Testcases Passed:                  " + inTestCasePassedCounter, 3);
				logScriptInfo("* Total Testcases Failed:                  " + inTestCaseFailCounter, 3);
				logScriptInfo("* Total Percent Testcases Passed:          " + inPrctPassed + "%", 3);
				logScriptInfo("* Total Percent Testcases Failed:          " + inPrctFailed + "%", 3);
				logScriptInfo("* Total Number of Test Actions:            " + inTestActionCounter, 3);
				logScriptInfo("* Total Number of Errors found:            " + inErrorCounter, 3);
				logScriptInfo("* End Date and Time:                       " + dtformat.format(new Date()) + " " + tmformat.format(new Date()),3);
				logScriptInfo("* Total Elapsed Time:                      " + DateTime.getFormattedDateTimeForElapsedTime(inElapsedTime), 3);
				//logScriptInfo("* Total Elapsed Time:                      " + getElapsedTime(inElapsedTime),3);
				logScriptInfo("******************************************************************************", 3);

			gsResultFile = gsSuiteResultFile;

			//write out HTML formatted suite results file
			gsPassFailFile = gsAutomationAutoResultPath + gsHtmlResultFileName;
			writePassFailResultsToHtmlFormat(gsResultFile, gsPassFailFile);

			}
			catch (Exception e)
			{
			errorHandler("Error loading suite statistic property variables in file: " + gsSuiteStatFile, e);
			}

			//display log results in specified Viewer when test suite completes
			if (gsSuiteResultFile != null)
			{
				if (gsAutomationResultViewerApp != null)
				{	
					try{
						String[] lsViewer = new String[] {gsAutomationResultViewerApp,gsAutomationAutoResultPath+"auto_results_details.htm"};
						//System.out.println(lsViewer[0]);
						//System.out.println(lsViewer[1]);
						Runtime.getRuntime().exec(lsViewer);
						}
						catch(Exception e){
							System.err.println("Error loading alternative Result Viewer application");
							e.printStackTrace();
							
						}
				}
			}
			
			//if option is true then send results to SCTM web server
			copyResultsToSilkCentral( );

		}

		/**
		* Displays interactive dialog message <p>
		 * @param sMsg
		 * @param sTitle
		*/
		public static void displayMessageDlg(String sMsg, String sTitle)
		{
			JOptionPane.showMessageDialog(null, sMsg, sTitle, JOptionPane.INFORMATION_MESSAGE);
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
				logScriptInfo("Verify " + sDesc + " Expected: " + bExpected + " Actual: " + bActual, giAutomationLogType);
				return true;
			}
			else
			{
			errorHandler("Verify " + sDesc + " Expected: " + bExpected + " Actual: " + bActual);
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
				//logScriptInfo("Verify " + sDesc + " Expected: " + bExpected + " Actual: " + bActual, giAutomationLogType);
				return true;
			}
			else
			{
			errorHandler(sDesc + " Expected: " + bExpected + " Actual: " + bActual);
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
					logScriptInfo("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"", giAutomationLogType);
					return true;
				}
				else
				{
				errorHandler("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"");
					return false;
				}
			}
			else
			{
				if (sActual.toUpperCase().indexOf(sExpected.toUpperCase()) != -1)
				{
					logScriptInfo("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"", giAutomationLogType);
					return true;
				}
				else
				{
				errorHandler("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"");
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
					logScriptInfo("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription, giAutomationLogType);
					return true;
				}
				else
				{
				errorHandler("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription);
					return false;
				}
			}
			else
			{
				if (sActual.toUpperCase().indexOf(sExpected.toUpperCase()) != -1)
				{
					logScriptInfo("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription, giAutomationLogType);
					return true;
				}
				else
				{
				errorHandler("Verify Expected: \"" + sExpected + "\" Actual: \"" + sActual + "\"" + " " + sDescription);
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
				logScriptInfo("Verify " + sDesc + " Expected: " + iExpected + " Actual: " + iActual,giAutomationLogType);
				return true;
			}
			else
			{
			errorHandler("Verify " + sDesc + " Expected: " + iExpected + " Actual: " + iActual);
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
			
			//if automation.properties file does not exist, check in alternate folder 
			if(!FileIO.fileExists(sFile))
			{
				
				String sAltDir = getAlternateAutoPropDir(); 
				
				//check for alternate automation properties directory specified in the system environment variable ALT_AUTOMATION_PROPERTIES_DIR
				if (sAltDir != "")
				{
					String sAltFile = getAlternateAutoPropDir() + FileIO.stripPath(sFile);
					
					if (!FileIO.fileExists(sAltFile)){
						System.err.println("ERROR: The " + "\"" + "Automation.properties" + "\"" + " file does not exist in the user home folder: " + FileIO.getParentPath(sFile) + " or alternate folder: " + getAlternateAutoPropDir());
						System.err.println("Either copy the automation.properties file to your home folder or create an alternate properties folder by creating a system environment variable called ALT_AUTOMATION_PROPERTIES_DIR and set the value to the desired alternate folder (i.e. c:\\temp\\)");
						System.exit(-1);	//Stops all further execution
					}
					
					//System.out.println(sAltFile);  //found automation.properties file in alternate folder 
				}	
				else{
					System.err.println("ERROR: The " + "\"" + "Automation.properties" + "\"" + " file does not exist in the user home folder: " + FileIO.getParentPath(sFile) + " or alternate folder: " + getAlternateAutoPropDir());
					System.err.println("Either copy the automation.properties file to your home folder or create an alternate properties folder by creating a system environment variable called ALT_AUTOMATION_PROPERTIES_DIR and set the value to the desired alternate folder (i.e. c:\\temp\\)");
					System.exit(-1);	//Stops all further execution
				}
			}
			
			//else
				//System.out.println(sFile);	//found automation.properties file in the users home folder

			
			
			
			//load Automation properties
			try
				{

					try {
						//set the execution engine to webdriver/selenium rc
						gsAutomationTestEngine = getPropertyFromFile("gsAutomationTestEngine", sFile);
						if (gsAutomationTestEngine == null || gsAutomationTestEngine.isEmpty())
							gsAutomationTestEngine = "WEBDRIVER"; //set to default if left blank in automation.propeties file
						
						if(gsAutomationTestEngine.toLowerCase().equals(Platform.SELENIUM.toLowerCase()))
						{
							Platform.setEngine(Platform.SELENIUM);
						}
						if(gsAutomationTestEngine.toLowerCase().equals(Platform.WEBDRIVER.toLowerCase()))
						{
							Platform.setEngine(Platform.WEBDRIVER);
						}

					} catch (Exception e) {
						Platform.setEngine(Platform.WEBDRIVER);  //if error occurred set to Defaut Test Engine
					}

					//Selemium Server name
					gsAutomationSeleniumServer = getPropertyFromFile("gsAutomationSeleniumServer",sFile);

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
					
					//Andrew quinan's code
					//else if (!gsModuleName.isEmpty()){
					//	gsAutomationAutoSupportDocs = gsAutomationAutoSupportDocs.replace("${moduleName}", gsModuleName);
					//}

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

					
					
					//Result file suffix
					gsAutomationAutoResultFileSuffix = getPropertyFromFile("gsAutomationAutoResultFileSuffix",sFile);
					if (gsAutomationAutoResultFileSuffix == null || gsAutomationAutoResultFileSuffix.isEmpty())
						gsAutomationAutoResultFileSuffix = ".txt"; //set to default if left blank in automation.propeties file

					//Image suffix
					gsAutomationAutoResultErrorImageSuffix = getPropertyFromFile("gsAutomationAutoResultErrorImageSuffix",sFile);
					if (gsAutomationAutoResultErrorImageSuffix == null || gsAutomationAutoResultErrorImageSuffix.isEmpty())
						gsAutomationAutoResultErrorImageSuffix = ".jpg"; //set to default if left blank in automation.propeties file
					
					//spreadsheet suffix
					gsAutomationTestDataSpreadsheetSuffix = getPropertyFromFile("gsAutomationTestDataSpreadsheetSuffix",sFile);
					if (gsAutomationTestDataSpreadsheetSuffix == null || gsAutomationTestDataSpreadsheetSuffix.isEmpty())
						gsAutomationTestDataSpreadsheetSuffix = ".xls"; //set to default if left blank in automation.propeties file

					
					//Log type
					sTmp = getPropertyFromFile("giAutomationLogType",sFile);
					if (sTmp == null || sTmp.isEmpty())
						giAutomationLogType = 1; //set to default if left blank in automation.propeties file
					else	
						giAutomationLogType = Integer.parseInt(getPropertyFromFile("giAutomationLogType",sFile));
					
					//Stack Trace
					sTmp = getPropertyFromFile("gbAutomationStackTrace",sFile);
					if (sTmp == null || sTmp.isEmpty())
						gbAutomationStackTrace = true;  //set to default if left blank in automation.propeties file  
					else
						gbAutomationStackTrace = Boolean.valueOf(getPropertyFromFile("gbAutomationStackTrace",sFile)).booleanValue();

					//Auto Image Capture on every testcase
					sTmp = getPropertyFromFile("gbAutomationImageCapture",sFile);
					if (sTmp == null || sTmp.isEmpty())
						gbAutomationImageCapture = false; //set to default if left blank in automation.propeties file
					else
						gbAutomationImageCapture = Boolean.valueOf(getPropertyFromFile("gbAutomationImageCapture",sFile)).booleanValue();

					//Auto Image capture on every error 
					sTmp = getPropertyFromFile("gbAutomationErrorImageCapture",sFile);
					if (sTmp == null || sTmp.isEmpty())
						gbAutomationErrorImageCapture = true;  //set to default if left blank in automation.propeties file
					else	
						gbAutomationErrorImageCapture = Boolean.valueOf(getPropertyFromFile("gbAutomationErrorImageCapture",sFile)).booleanValue();
					
					//Close browser at script completion 
					sTmp = getPropertyFromFile("gbAutomationCloseTestBrowserAtScriptCompletion",sFile);
					if (sTmp == null || sTmp.isEmpty())
						gbAutomationCloseTestBrowserAtScriptCompletion = true;  //set to default if left blank in automation.propeties file
					else
						gbAutomationCloseTestBrowserAtScriptCompletion = Boolean.valueOf(getPropertyFromFile("gbAutomationCloseTestBrowserAtScriptCompletion",sFile)).booleanValue();
					
					//Silk central Integration
					sTmp = getPropertyFromFile("gbAutomationSilkCentral",sFile);
					if (sTmp == null || sTmp.isEmpty())
						gbAutomationSilkCentral = false;  //set to default if left blank in automation.propeties file
					else	
						gbAutomationSilkCentral = Boolean.valueOf(getPropertyFromFile("gbAutomationSilkCentral",sFile)).booleanValue();
					
					
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
					


					//Test Server/User Properties
					
					//Test URL
					gsAutomationBaseURL = getPropertyFromFile("gsAutomationBaseURL",sFile);
					if (gsAutomationBaseURL == null || gsAutomationBaseURL.isEmpty())
						gsAutomationBaseURL = "about:blank";	//set to default if left blank in automation.propeties file
					
					
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
						}else if (gsAutomationTestBrowser.contains("7")){
							gsAutomationTestBrowser = Platform.gsInternetExplorer7;

						}
					}

					
					//user name
					gsAutomationUsername = getPropertyFromFile("gsAutomationUsername",sFile);
					//password
					gsAutomationPassword = getPropertyFromFile("gsAutomationPassword",sFile);


					//Base State Properties
					gsAutomationBrowserBaseStatePage = getPropertyFromFile("gsAutomationBrowserBaseStatePage",sFile);

					sTmp = getPropertyFromFile("giAutomationBaseState",sFile);
					if (sTmp == null || sTmp.isEmpty())
						giAutomationBaseState = 2; //set to default if left blank in automation.propeties file
					else
						giAutomationBaseState = Integer.parseInt(getPropertyFromFile("giAutomationBaseState",sFile));
					

					//Test Result Viewer application/browser 
					gsAutomationResultViewerApp = getPropertyFromFile("gsAutomationResultViewerApp",sFile);
					if (gsAutomationResultViewerApp.equals(""))
						gsAutomationResultViewerApp = null;
					
					
					//Browser Profile Properties
					gsAutomationBrowserProfile = getPropertyFromFile("gsAutomationBrowserProfile",sFile);
					if (gsAutomationBrowserProfile.equals(""))
						gsAutomationBrowserProfile = null;
					
					
					//BrowserPath
					gsAutomationBrowserPath = getPropertyFromFile("gsAutomationBrowserPath",sFile);
					if (gsAutomationBrowserPath.equals(""))
						gsAutomationBrowserPath = null;

					
					//Test level
					sTmp = getPropertyFromFile("giAutomationTestLevel",sFile);
					if (sTmp == null || sTmp.isEmpty())
						giAutomationTestLevel = 2; //set to default if left blank in automation.propeties file
					else	
						giAutomationTestLevel = Integer.parseInt(getPropertyFromFile("giAutomationTestLevel",sFile));
					

					//get user argument list
					sTmp = getPropertyFromFile("glsAutomationArgs",sFile);
					if (sTmp == null || sTmp.isEmpty())
						gsArgs = "";   //set to default if left blank in automation.propeties file
					else	
						gsArgs = getPropertyFromFile("glsAutomationArgs",sFile);

					if (gsArgs.equals(""))
						glsAutomationArgs = null;
					else
						{
						String[] lsArgs = Strings.stringToStringArray(gsArgs, ",");
						glsAutomationArgs = lsArgs;
						}




				}
				catch (Exception e)
				{
				errorHandler("Error loading automation Property settings in file: " + gsAutoPropFile, e);
				}

		}
		
		
		
		
		
		/**
		 * This method prints out the global variables in the automation.properties file after they have been initialized
		 */
		public static void printInitializedAutomationProperties(){
			logBanner("Displaying the initialized automation.properties values");
			logScriptInfo("gsAutomationTestEngine=" + gsAutomationTestEngine);
			logScriptInfo("gsAutomationSeleniumServer=" + gsAutomationSeleniumServer);
			logScriptInfo("gsAutomationAutoPath=" + gsAutomationAutoPath);
			logScriptInfo("gsAutomationAutoSupportDocs=" + gsAutomationAutoSupportDocs);
			logScriptInfo("gsAutomationAutoResultPath=" + gsAutomationAutoResultPath);
			logScriptInfo("gsAutomationAutoResultFileSuffix=" + gsAutomationAutoResultFileSuffix);
			logScriptInfo("gsAutomationAutoResultErrorImageSuffix=" + gsAutomationAutoResultErrorImageSuffix);
			logScriptInfo("gsAutomationTestDataSpreadsheetSuffix=" + gsAutomationTestDataSpreadsheetSuffix);
			logScriptInfo("giAutomationLogType=" + String.valueOf(giAutomationLogType));
			logScriptInfo("gbAutomationStackTrace=" + String.valueOf(gbAutomationStackTrace));
			logScriptInfo("gbAutomationErrorImageCapture=" + String.valueOf(gbAutomationErrorImageCapture));
			logScriptInfo("gbAutomationCloseTestBrowserAtScriptCompletion=" + String.valueOf(gbAutomationCloseTestBrowserAtScriptCompletion));
			logScriptInfo("gbAutomationSilkCentral=" + String.valueOf(gbAutomationSilkCentral));
			logScriptInfo("giAutomationPause1TO=" + String.valueOf(giAutomationPause1TO));
			logScriptInfo("giAutomationPause2TO=" + String.valueOf(giAutomationPause2TO));
			logScriptInfo("giAutomationShortTO=" + String.valueOf(giAutomationShortTO));
			logScriptInfo("giAutomationNormalTO="+ String.valueOf(giAutomationNormalTO));
			logScriptInfo("giAutomationWaitTO=" + String.valueOf(giAutomationWaitTO));
			logScriptInfo("giAutomationMedTO=" + String.valueOf(giAutomationMedTO));
			logScriptInfo("giAutomationLongTO="+ String.valueOf(giAutomationLongTO));
			logScriptInfo("gsAutomationBaseURL=" + gsAutomationBaseURL);
			logScriptInfo("gsAutomationTestBrowser=" + gsAutomationTestBrowser);
			logScriptInfo("gsAutomationUsername=" + gsAutomationUsername);
			logScriptInfo("gsAutomationPassword=" + gsAutomationPassword);
			logScriptInfo("gsAutomationBrowserBaseStatePage=" + gsAutomationBrowserBaseStatePage);
			logScriptInfo("giAutomationBaseState=" + String.valueOf(giAutomationBaseState));
			logScriptInfo("gsAutomationResultViewerApp=" + gsAutomationResultViewerApp);
			logScriptInfo("gsAutomationBrowserProfile=" + gsAutomationBrowserProfile);
			logScriptInfo("gsAutomationBrowserPath=" + gsAutomationBrowserPath);
			logScriptInfo("giAutomationTestLevel=" + String.valueOf(giAutomationTestLevel));
			
			String sTmp="";
			if (glsAutomationArgs==null)
				logScriptInfo("glsAutomationArgs=");
			else{	
				for (int x=0; x < glsAutomationArgs.length; x++)
				{
				 sTmp = sTmp + glsAutomationArgs[x] + ",";
				}
				logScriptInfo("glsAutomationArgs=" + sTmp);
			}
		}
		
		
		
		
		
		/**
		 * Returns an alternate file directory, for the automation.properties file, specified in the System environment variable ALT_AUTOMATION_PROPERTIES_DIR
		 * @return Sting containing alternate location for Automation properties files
		 */
		public static String getAlternateAutoPropDir() {
			String sDirName = System.getenv("ALT_AUTOMATION_PROPERTIES_DIR");

			if (sDirName == null) {
				sDirName = "";
			} else if (!sDirName.endsWith("\\")) {
				sDirName = sDirName + Platform.getFileSeparator();
			}
			return sDirName;
		}
		
		
		
		

		/**
		 * Creates a file containing the default settings for the automation.properties file
		 */
		public static void createDefaultAutomationPropertiesFile(String file) {
			
			//clear file content
			FileIO.writeFileContents(file, "");
			
			//write out default automation.properties file
			FileIO.appendStringToFile(file, "#ACI Selenium Automation Properties");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "# Place this properties file in your local system directory (e.g. c:\\users\\<your name> ");
			FileIO.appendStringToFile(file, "# To find your home folder in windows Click Start->Run enter %HOMEPATH% and click OK - Windows will");
			FileIO.appendStringToFile(file, "# open your home folder. This is the folder that the Automation.properties file should be placed in. ");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Test Tool Properties");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Test Tool engine options SELENIUM or WEBDRIVER or SB_WEBDRIVER");
			FileIO.appendStringToFile(file, "gsAutomationTestEngine=WEBDRIVER");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Selenium Server");
			FileIO.appendStringToFile(file, "gsAutomationSeleniumServer=");
			FileIO.appendStringToFile(file, "#selenium-server-standalone-2.39.0.jar");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#SilkCentral Integration");
			FileIO.appendStringToFile(file, "gbAutomationSilkCentral=false");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Browser Properties");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Test Browser Name");
			FileIO.appendStringToFile(file, "gsAutomationTestBrowser=Firefox");
			FileIO.appendStringToFile(file, "#gsAutomationTestBrowser=Firefox");
			FileIO.appendStringToFile(file, "#gsAutomationTestBrowser=Internet Explorer");
			FileIO.appendStringToFile(file, "#gsAutomationTestBrowser=Google Chrome");
			FileIO.appendStringToFile(file, "#gsAutomationTestBrowser=Apple Safari");
			FileIO.appendStringToFile(file, "#gsAutomationTestBrowser=Opera");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Close Test Browsers upon test script completion");
			FileIO.appendStringToFile(file, "gbAutomationCloseTestBrowserAtScriptCompletion=true");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Browser profiles allow you to customize your browsers options. To use use a browser");
			FileIO.appendStringToFile(file, "#profile file enter the name of the profile file here. Leave empty if the default browser is what you want.");
			FileIO.appendStringToFile(file, "gsAutomationBrowserProfile=");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Browser Path. leaving this property empty tells automation to use the default browser path");
			FileIO.appendStringToFile(file, "#If you are using a Opera or require an alternate browser directory, you can set it here");
			FileIO.appendStringToFile(file, "gsAutomationBrowserPath=");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Test Server/User Properties");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Test Server URL");
			FileIO.appendStringToFile(file, "gsAutomationBaseURL=http://www.google.com/");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Test user name");
			FileIO.appendStringToFile(file, "gsAutomationUsername=");
			FileIO.appendStringToFile(file, "#susanadams409");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Test user password");
			FileIO.appendStringToFile(file, "gsAutomationPassword=");
			FileIO.appendStringToFile(file, "#adams409");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Test Base-State properties");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Browser Base State Caption");
			FileIO.appendStringToFile(file, "gsAutomationBrowserBaseStatePage=");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Base state setting");
			FileIO.appendStringToFile(file, "giAutomationBaseState=");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#General Automation Path and Wait Properties");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Local Automation directory");
			FileIO.appendStringToFile(file, "gsAutomationAutoPath=c:\\\\Selenium_Automation\\\\trunk\\\\automation\\\\");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Test data and support document directory");
			FileIO.appendStringToFile(file, "gsAutomationAutoSupportDocs=C:\\\\Selenium_Automation\\\\trunk\\\\automation\\\\google\\\\testdata\\\\");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Global Sleep (wait) values in seconds");
			FileIO.appendStringToFile(file, "giAutomationPause1TO=1");
			FileIO.appendStringToFile(file, "giAutomationPause2TO=2");
			FileIO.appendStringToFile(file, "giAutomationShortTO=5");
			FileIO.appendStringToFile(file, "giAutomationNormalTO=10");
			FileIO.appendStringToFile(file, "giAutomationWaitTO=15");
			FileIO.appendStringToFile(file, "giAutomationMedTO=30");
			FileIO.appendStringToFile(file, "giAutomationLongTO=60");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Logging Properties");
			FileIO.appendStringToFile(file, "#****************************************************");
			FileIO.appendStringToFile(file, "#Result log directory");
			FileIO.appendStringToFile(file, "gsAutomationAutoResultPath=c:\\\\auto\\\\google\\\\results\\\\");
			FileIO.appendStringToFile(file, "#gsAutomationAutoResultPath=/TestResults/Sprint/results/");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Default Result Viewer Application");
			FileIO.appendStringToFile(file, "gsAutomationResultViewerApp=");
			FileIO.appendStringToFile(file, "#" + Strings.sDQ + "c:\\\\program files\\\\mozilla firefox\\\\firefox.exe" + Strings.sDQ);
			FileIO.appendStringToFile(file, "#" + Strings.sDQ + "c:\\\\program files\\\\google\\\\chrome\\\\application\\\\chrome.exe" + Strings.sDQ);
			FileIO.appendStringToFile(file, "#" + Strings.sDQ + "c:\\\\program files\\\\internet explorer\\\\iexplore.exe" + Strings.sDQ);
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Result log file suffix");
			FileIO.appendStringToFile(file, "gsAutomationAutoResultFileSuffix=.txt");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Result log error image suffix");
			FileIO.appendStringToFile(file, "gsAutomationAutoResultErrorImageSuffix=.jpg");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Test Data Spreadsheet suffix");
			FileIO.appendStringToFile(file, "gsAutomationTestDataSpreadsheetSuffix=.xls");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Log Type. There 9 different log output formats.");
			FileIO.appendStringToFile(file, "giAutomationLogType=1");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Capture Stack Trace upon failure");
			FileIO.appendStringToFile(file, "gbAutomationStackTrace=true");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Capture Screen Image upon failure");
			FileIO.appendStringToFile(file, "gbAutomationErrorImageCapture=true");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Capture a Screen Image during every testcase");
			FileIO.appendStringToFile(file, "gbAutomationImageCapture=false");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#Test Level");
			FileIO.appendStringToFile(file, "giAutomationTestLevel=");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "");
			FileIO.appendStringToFile(file, "#*******************************************************");
			FileIO.appendStringToFile(file, "#Generic array variable anyone can use to store global variables for any purpose.");
			FileIO.appendStringToFile(file, "#Separate variables by comma (i.e. glsArgs=John Jones,Fred Smith,Steve Johnson)");
			FileIO.appendStringToFile(file, "#Access the variables in your script like this glsArgs[0]=\"John Jones\", glsArgs[1]=\"Fred Smith\", etc.");
			FileIO.appendStringToFile(file, "#*******************************************************");
			FileIO.appendStringToFile(file, "glsAutomationArgs=");

			
		}

		
		
		/**
		* Prints automation.properties file properties and values to the console
		*/
		public static void printAutomationProperties()
		{
			//Get machine specific global automation variables
			File propFile = new File(gsAutoPropFile);
			if (propFile.exists())
			{

				try
				{
					//load automation properties from properties file
					FileInputStream in = new FileInputStream(gsAutoPropFile);
					Properties autoSettings = new Properties();
					autoSettings.load(in);
					
					logBanner("Displaying the properties and values from the active automation.properties file");
					
					//print settings from auto properties file
					String[] ls = Strings.stringToStringArray(autoSettings.entrySet().toString(),",");
					for (int x=0; x< ls.length;x++)
						logScriptInfo(ls[x]);

					//close properties file
					in.close();
				}
				catch (Exception e)
				{
				errorHandler("Error loading automation Property settings");
				}

			}

		}



		/**
		 * Prints out the directory into which you should put the properties file
		 */
		public static void printDirForPropertiesFile()
		{
			System.out.println(Platform.getUserHome());
		}


		/**
		 * Gets the directory into which you should put properties files
		 * @return String with properties directory path
		 */
		public static String getPropertiesDir()
		{
			return Platform.getUserHome();
		}



//		/**
//		 * Get properties from the file.
//		 * @param fileName properties file name to load
//		 * @return Properties of given properties file
//		 * @throws IOException
//		 */
//		public static Properties getPropertiesFromFile(String fileName) throws IOException {
//			return getPropertiesFromFile(new File(fileName));
//		}
	//	
	//	

//		/**
//		 * get properties from the file.
//		 * @param file
//		 * @return
//		 *
//		 * @param file properties file to load
//		 *
//		 * @exception IOException
//		 */
//		public static Properties getPropertiesFromFile(File file) throws IOException {
//			Properties prop = new Properties();
//			//if properties file does not exist create it
//			if (!file.exists())
//			{
//				FileIO.writeFileContentsByUTF8(file.getPath(), "");
//			}
	//
//			loadFromFile(prop, file);
//			return prop;
//		}
	//
//		/**
//		 * Loads properties from the file. Properties are appended to the existing
//		 * properties object.
//		 * @param p
//		 * @param fileName
//		 *
//		 * @param p        properties to fill in
//		 * @param fileName properties file name to load
//		 *
//		 * @exception IOException
//		 */
//		public static void loadFromFile(Properties p, String fileName) throws IOException {
//			loadFromFile(p, new File(fileName));
//		}
	//
		
		
		
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
			catch(Exception e){e.printStackTrace();sPropVal = "";}


			//System.out.println("");
			//System.out.println("");
			//System.out.println("prop file = " + file.getPath());
			//System.out.println("prop key = " + sKey);
			//System.out.println("prop Value = " + sPropVal);


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
		* Sets/writes/changes the specified automation property (sKey) value (sVal) in the specified automation 
	    * properties file at runtime - example usage: setAutomationProperty ("automation.properties", "gsAutomationResultPath", "c:\\myautodir\\");
		* <p>
		* @param String sPropFileName filename (name only - no path info) of the automation property file you                    
	      want to update
		* @param String sKey the property to modify
		* @param String sVal the new value to set for the above sKey property
		* @return new value for specified property
		*/
		public static String setAutomationProperty (String sPropFileName, String sKey, String sVal)
		{
			try{
				// Append the path to the file name
				String sPropFile = getPropertiesDir() + Platform.getFileSeparator()+ sPropFileName;
			     
				// Write new property in specified file
				File f1 = new File(sPropFile);
				if (f1.exists()) {
				   FileIO.updatePropertyFile(sPropFile, sKey, sVal);
				   getPropertyFromFile(sKey, sPropFile);
				   return getPropertyFromFile(sKey, sPropFile);
				}
				
			}catch(Exception e){
				
			   Log.errorHandler("Error setting " + "\"" + sKey + "\"" + " property in " + sPropFileName + " property file",e);
			   return "Property Not Found";
			}

			return "Property Not Found";
		 }



//		/**
//		 * Writes properties to a file.
//		 * @param p
//		 * @param fileName
//		 *
//		 * @param p        properties to write to file
//		 * @param fileName destination file name
//		 *
//		 * @exception IOException
//		 */
//		public static void writePropertiesToFile(Properties p, String fileName) throws IOException {
//			writePropsToFile(p, new File(fileName), null);
//		}
	//
//		/**
//		 * Writes properties to a file.
//		 * @param p
//		 * @param fileName
//		 * @param header
//		 *
//		 * @param p        properties to write to file
//		 * @param fileName destiantion file name
//		 * @param header   optional header
//		 *
//		 * @exception IOException
//		 */
//		public static void writePropertiesToFile(Properties p, String fileName, String header) throws IOException {
//			writePropsToFile(p, new File(fileName), header);
//		}
	//
//		/**
//		 * Writes properties to a file.
//		 * @param p
//		 * @param file
//		 *
//		 * @param p      properties to write to file
//		 * @param file   destination file
//		 *
//		 * @exception IOException
//		 */
//		public static void writePropertiesToFile(Properties p, File file) throws IOException {
//			writePropsToFile(p, file, null);
//		}
	//
//		/**
//		 * Writes properties to a file.
//		 * @param p
//		 * @param file
//		 * @param header
//		 *
//		 * @param p      properties to write to file
//		 * @param file   destiantion file
//		 * @param header optional header
//		 *
//		 * @exception IOException
//		 */
//		public static void writePropsToFile(Properties p, File file, String header) throws IOException {
//			if (p == null) {
//				return;
//			}
//			FileOutputStream fos = null;
//			try {
//				fos = new FileOutputStream(file);
//				p.store(fos, header);
//			} finally {
//				if (fos != null) {
//					fos.close();
//				}
//			}
//		}
		



		//The following methods are used to produce HTML formatted result file


		/**
		 * Loads a result file and extracts script names to a string array
		 * @param sFile the result file to extract script names from 
		 * @return String array containing all of the script names in the given result file
		*/
		public static String[] getScripts(String sFile)
		{

			String[] lsResFile = FileIO.getFileContentsAsListByUTF8(sFile);
			String sScriptHeader = gsScriptHeader;
			String sScripts = "";
			String[] lsScripts = { "" };

			//find Scripts
			for (int g = 0; g < lsResFile.length; g++)
			{
				if (lsResFile[g] != null)
				{
					if (lsResFile[g].indexOf(sScriptHeader) != -1)
					{
						sScripts = sScripts + lsResFile[g].substring(gsScriptHeader.length()).trim() + gsNewline;
					}
				}

			}

			lsScripts = Strings.stringToStringArray(sScripts, gsNewline);

			return lsScripts;
		}



		/**
		 * Loads a result file and extracts script content to a string
		 * @param sFile the result file to extract script info from
		 * @param sScriptBegin the text in the script to begin the extract
		 * @param sScriptEnd the text in the script to end the extract
		 * @return String containing all specified script information i.e. test cases
		*/
		public static String getScriptContent(String sFile, String sScriptBegin, String sScriptEnd)
		{
			String sScript = "";

			//get testcases for selected script
			sScript = extractBlock(sFile, sScriptBegin, sScriptEnd);

			return sScript;
		}





		/**
		 * Loads a result file and extracts content to a string
		 * @param sFile the result file to extract info from
		 * @param sBegin the text in the file to begin the extract
		 * @param sEnd the text in the file to end the extract
		 * @return String containing all specified text
		*/
		public static String extractBlock(String sFile, String sBegin, String sEnd)
		{
			//find extraction blocks
			int a = 1;
			//int iBeginLine = 0;
			//int iEndLine = 0;
			String sBlock = "";

			//get file
			File resfile = new File(sFile);

			//read in selected result file

			//System.out.println(resfile.getPath());
			String[] lsExtractionBlocks = FileIO.getFileContentsAsListByUTF8(resfile.getPath());

			out:for (int x = 0; x < lsExtractionBlocks.length; x++)
			{
				if (lsExtractionBlocks[x] != null)
				{
					//Extract Block by Search string criteria in Extract Blocks tab of Options dialog
					if (lsExtractionBlocks[x].equals(sBegin))
					{
						sBlock = sBlock + lsExtractionBlocks[x] + gsNewline;
						a = 1;

						if (lsExtractionBlocks[x + a] != null)
						{

							while (lsExtractionBlocks[x + a].indexOf(sEnd) == -1)
							{

								sBlock = sBlock + lsExtractionBlocks[x + a].trim() + gsNewline;
								a++;
								if ((x + a) == lsExtractionBlocks.length) {
									break out;
								}
							}
						}

						//get last item
						sBlock = sBlock + lsExtractionBlocks[x + a].trim() + gsNewline;
						//sBlock = sBlock + u.gsNewline;
					}
				}
			}

			return sBlock;
		}

		/**
		 * Extracts and returns a block of text from within a larger block of of text.
		 * Useful for getting a Testcase block from within a script block
		 * @param sContent the main text block to extract info from
		 * @param sBegin the text in the content to begin the extract
		 * @param sEnd the text in the content to end the extract
		 * @return String containing all specified text 
		 */
		public static String extractBlockFromString(String sContent, String sBegin, String sEnd)
		{
			//find extraction blocks
			int a = 1;
			//int iBeginLine = 0;
			//int iEndLine = 0;
			String sBlock = "";

			String[] lsExtractionBlocks = Strings.stringToStringArray(sContent, gsNewline);

			for (int x = 0; x < lsExtractionBlocks.length; x++)
			{
				if (lsExtractionBlocks[x] != null)
				{
					//Extract text
					if (lsExtractionBlocks[x].indexOf(sBegin)!=-1)
					{
						sBlock = sBlock + lsExtractionBlocks[x] + gsNewline;
						a = 1;

						if (lsExtractionBlocks[x + a] != null)
						{

							while (lsExtractionBlocks[x + a].indexOf(sEnd) == -1)
							{

								sBlock = sBlock + lsExtractionBlocks[x + a].trim() + gsNewline;
								a++;

							}
						}

						//get last item
						sBlock = sBlock + lsExtractionBlocks[x + a].trim() + gsNewline;
						return sBlock;
						//sBlock = sBlock + util.gsNewline;
					}
				}
			}

			return sBlock;
		}


		/**
		 * Extracts and returns a test case block of text from within a script.
		 * @param sContent the main text block to extract info from
		 * @param sBegin the text in the content to begin the extract
		 * @return String containing test case information
		 */
		public static String getTestcaseBlockFromString(String sContent, String sBegin)
		{
			//find extraction blocks
			int a = 1;
			//int iBeginLine = 0;
			//int iEndLine = 0;
			String sBlock = "";

			String[] lsExtractionBlocks = Strings.stringToStringArray(sContent, gsNewline);

			for (int x = 0; x < lsExtractionBlocks.length; x++)
			{
				if (lsExtractionBlocks[x] != null)
				{
					//Extract text
					if (lsExtractionBlocks[x].indexOf(sBegin)!=-1)
					{
						sBlock = sBlock + "==============================================================================" + gsNewline;
						sBlock = sBlock + lsExtractionBlocks[x] + gsNewline;
						a = 1;

						if (lsExtractionBlocks[x + a] != null)
						{

							while ((lsExtractionBlocks[x + a].indexOf(gsTestcaseHeader) == -1)
								&& (lsExtractionBlocks[x + a].indexOf(gsScriptHeader) == -1)
								&& (lsExtractionBlocks[x + a].indexOf(gsTestcaseFooter) == -1)
								&& (lsExtractionBlocks.length >= (x + a)))
							{

								sBlock = sBlock + lsExtractionBlocks[x + a].trim() + gsNewline;
								a++;

							}
						}

						//get last item
						//sBlock = sBlock + lsExtractionBlocks[x + a].trim() + gsNewline;
						return sBlock;
						//sBlock = sBlock + util.gsNewline;
					}
				}
			}

			return sBlock;
		}



		/**
		 * Extracts names of all scripts that failed from text string of a given result file
		 * @param sScriptContent the result log output
		 * @return String containing the names of all of the scripts that failed
		*/
		public static String getFailedScripts(String sScriptContent)
		{

			if (sScriptContent.indexOf(gsFail) != -1)
			{
				//System.out.println(getScriptNameFromBlock(sScriptContent));
				return getScriptNameFromBlock(sScriptContent);
			}

			return "";
		}


		/**
		 * Extracts script name from block of text from given script result log
		 * @param sScript the result output content 
		 * @return the name of the script that was executed in the result log
		*/
		public static String getScriptNameFromBlock(String sScript)
		{

			String[] lsResFile = Strings.stringToStringArray(sScript, gsNewline);
			String sScriptHeader = gsScriptHeader;
			//String sScripts = "";
			//String[] lsScripts = { "" };

			//find Testcases
			for (int g = 0; g < lsResFile.length; g++)
			{
				if (lsResFile[g] != null)
				{
					if (lsResFile[g].indexOf(sScriptHeader) != -1)
					{
						return lsResFile[g].substring(gsScriptHeader.length()).trim() + gsNewline;
					}
				}

			}
			return null;
		}


		/**
		 * Extracts test case names from block of text from given script result
		 * @param sBlock block of text from a result log
		 * @return a list of names of test cases in a given block of result log content 
		*/
		public static String[] getTestcasesFromBlock(String sBlock)
		{
			String[] lsResFile = Strings.stringToStringArray(sBlock, gsNewline);
			String sTestcaseHeader = gsTestcaseHeader;
			String sTestcases = "";
			String[] lsTestcases;

			//find Testcases
			for (int g = 0; g < lsResFile.length; g++)
			{
				if (lsResFile[g] != null)
				{
					if (lsResFile[g].indexOf(sTestcaseHeader) != -1)
					{
						sTestcases = sTestcases + lsResFile[g].substring(gsTestcaseHeader.length()) + gsNewline;
					}
				}

			}

			lsTestcases = Strings.stringToStringArray(sTestcases, gsNewline);

			return lsTestcases;
		}




		/**
		 * Extracts failed test case names from block of text from given script result file
		 * @param sResFile the result file to get test case names from
		 * @return names of test cases that failed
		*/
		public static String getFailedTestcases(String sResFile)
		{
			//String[] lsFailedTestcases;
			String sFailedTestcases = "";
			//read in selected result file

			//get result file contents
			String[] lsContents = FileIO.getFileContentsAsListByUTF8(sResFile);

			//read in file info and find any failures
			//boolean bFound = false;
			for (int x = 0; x < lsContents.length; x++)
			{
				//System.out.println(lsContents[x]);
				if (lsContents[x] != null)
				{
					//found a test failure
					if (lsContents[x].indexOf(gsFail) != -1)
					{
						//find what testcase failed
						for (int z = x; z > 0; z--)
						{
							if (lsContents[z].indexOf(gsTestcaseHeader) != -1)
							{
								sFailedTestcases = sFailedTestcases + lsContents[z] + gsNewline;
								break;
							}
						}
					}
				}
			}

		return sFailedTestcases;
		}

		
		
		/**
		 * Extracts a block of text from a given String in HTML format
		 * @param sScriptContent string to extract text from
		 * @param sBegin partial text of beginning of text block
		 * @param sEnd partial text from end of text block
		 * @return block of text from a given String in HTML format
		 */
		public static String extractBlockFromStringHtml(String sScriptContent, String sBegin, String sEnd)
		{
			//find extraction blocks
			int a = 1;
			//int iBeginLine = 0;
			//int iEndLine = 0;
			String sBlock = "";

			String[] lsExtractionBlocks = Strings.stringToStringArray(sScriptContent, gsNewline);

			for (int x = 0; x < lsExtractionBlocks.length; x++)
			{
				if (lsExtractionBlocks[x] != null)
				{
					//Extract text
					if (lsExtractionBlocks[x].indexOf(sBegin)!=-1)
					{
						sBlock = sBlock + lsExtractionBlocks[x]+ "<BR>";
						a = 1;

						if (lsExtractionBlocks[x + a] != null)
						{

							while (lsExtractionBlocks[x + a].indexOf(sEnd) == -1)
							{

								sBlock = sBlock + lsExtractionBlocks[x + a].trim() + "<BR>";
								a++;

							}
						}

						//get last item
						sBlock = sBlock + lsExtractionBlocks[x + a].trim() + "<BR>";
						return sBlock;

					}
				}
			}

			return sBlock;
		}

		
		
		
		/**
		 * Extracts a block of text from a given String starting from the end of the string
		 * @param sScriptContent string to extract text from
		 * @param sBegin - partial text of beginning of text block
		 * @param sEnd - partial text from end of text block
		 * @return block of text from a given String starting from the end of the string
		 */
		public static String extractBlockFromStringEnd(String sScriptContent, String sBegin, String sEnd)
		{
			//find extraction blocks
			int a = 1;
			//int iBeginLine = 0;
			//int iEndLine = 0;
			String sBlock = "";

			String[] lsExtractionBlocks = Strings.stringToStringArray(sScriptContent, gsNewline);

			//System.out.println("Num of Lines " + lsExtractionBlocks.length);


			for (int x = lsExtractionBlocks.length-1; x > 0; x--)
			{
				if (lsExtractionBlocks[x] != null)
				{
					//Extract text
					if (lsExtractionBlocks[x].indexOf(sBegin) != -1)
					{
						sBlock = sBlock + lsExtractionBlocks[x] + "<BR>";
						a = 1;



						if (lsExtractionBlocks[x + a] != null)
						{

							while (lsExtractionBlocks[x + a].indexOf(sEnd) == -1)
							{

								sBlock = sBlock + lsExtractionBlocks[x + a].trim() + "<BR>";
								a++;
								//System.out.println(sBlock);

							}
						}

						//get last item
						sBlock = sBlock + lsExtractionBlocks[x + a].trim() + "<BR>";
						return sBlock;

					}
				}
			}

			return sBlock;
		}



		/**
		 * Gets script result info in HTML format
		 * @param sScript - script to get result info from
		 * @return script metric information i.e. number of test cases that passed or failed, percentage passed, failed etc.
		 */
		public static String getScriptResultInfoHtml(String sScript)
		{

			String sContent = FileIO.getFileContentsByUTF8(sScript);
			String sTmp = extractBlockFromStringHtml(sContent, gsTestcasesExecuted, gsScriptFooter);

			StringBuffer sb = new StringBuffer();
			sb.delete(0, sb.length());

			sb.append("***************************************************************************************************************" + "<BR>");
			sb.append(sTmp);
			sb.append("***************************************************************************************************************" + "<BR>");

			return sb.toString();
		}

		
		/**
		 * Gets suite result information in HTML format
		 * @param sScript - suite to get suite results from
		 * @return suite metric information i.e. number of scripts passed or failed in suite, percentage passed, failed etc.
		 */
		public static String getSuiteResultInfoEnd(String sScript)
		{
			String sContent = FileIO.getFileContentsByUTF8(sScript);
			String sTmp = extractBlockFromStringEnd(sContent, gsTotalScriptsExecuted, gsTotalElapsedTime);

			StringBuffer sb = new StringBuffer();
			sb.delete(0, sb.length());

			sb.append("***************************************************************************************************************" + "<BR>");
			sb.append(sTmp);
			sb.append("***************************************************************************************************************" + "<BR>");

			return sb.toString();
		}





		/**
		 * Writes out HTMl formatted result log file
		 * @param sResultFileIn the result file in text format
		 * @param sResultFileOut the result file in HTML format 
		 */
		public static void writePassFailResultsToHtmlFormat(String sResultFileIn, String sResultFileOut)
		{
			String sScript = "";
			String sPassFailFile = sResultFileOut + ".htm";
			String sPassFailFileDetails = sResultFileOut + "_details.htm";

			//clear result files
			FileIO.writeFileContentsByUTF8(sPassFailFileDetails, "");
			FileIO.writeFileContentsByUTF8(sPassFailFile, "");

			//temporary script list file
			String sTempScriptListFile = gsAutomationAutoResultPath + "temp_script_list.txt";
			FileIO.writeFileContentsByUTF8(sTempScriptListFile, "");

			//temporary testcase list file
			String sTempTestcaseListFile = gsAutomationAutoResultPath + "temp_testcase_list.txt";
			FileIO.writeFileContentsByUTF8(sTempTestcaseListFile, "");

			//temporary testcase content file
			String sTempTestcaseActionsFile = gsAutomationAutoResultPath + "temp_testcase_actions.txt";
			FileIO.writeFileContentsByUTF8(sTempTestcaseActionsFile, "");

			//String sFailedScripts="";
			String sDQ = "\"";

			//create lists
			String[] lsScripts = {
			};
			String[] lsTestcases = {
			};
			//String[] lsTestcaseActions = {
			//};

			try
			{
				//get all scripts
				lsScripts = getScripts(sResultFileIn);
				
				String sStartTime;
				
				int i = lsScripts.length;
				
				if (i == 1){
					sStartTime = gsCurrentDateAndTime;
				}
				else{
					sStartTime = gsSuiteStartTime;
				}

				//Write out HTML header info
				FileIO.appendStringToFile(sTempScriptListFile,"<HTML><HEAD><TITLE>Automation Results for " + sStartTime + "</TITLE></HEAD>");

				//Write header info at top of result table
				FileIO.appendStringToFile(sTempScriptListFile,"<ALIGN=left>");
				FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
				FileIO.appendStringToFile(sTempScriptListFile,"<B>Automation Results</B>" + "<BR>");
				FileIO.appendStringToFile(sTempScriptListFile,"<B>Start Date and Time:</B>     " + sStartTime + "<BR><P>");
				//FileIO.appendStringToFile(sTempScriptListFile,"<B>Test Server:</B>   " + gsAutomationBaseURL + "<BR>");
				FileIO.appendStringToFile(sTempScriptListFile,"</FONT></ALIGN>");



				//Test result summary
				//check if result is a suite or single script
				if (i == 1)
				{
					FileIO.appendStringToFile(sTempScriptListFile,"<SMALL>");
					FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
					FileIO.appendStringToFile(sTempScriptListFile,getScriptResultInfoHtml(sResultFileIn));
					FileIO.appendStringToFile(sTempScriptListFile,"</FONT>");
					FileIO.appendStringToFile(sTempScriptListFile,"</SMALL>");
				}
				else
				{
//					Write out HTML footer info
					FileIO.appendStringToFile(sTempScriptListFile,"<SMALL>");
					FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
					FileIO.appendStringToFile(sTempScriptListFile,getSuiteResultInfoEnd(sResultFileIn));
					FileIO.appendStringToFile(sTempScriptListFile,"</FONT>");
					FileIO.appendStringToFile(sTempScriptListFile,"</SMALL>");
				}

				FileIO.appendStringToFile(sTempScriptListFile,"<TABLE BORDER STYLE=\"border-width: 1px;border-spacing: ;border-style: outset;border-color: gray;\">");

				FileIO.appendStringToFile(sTempScriptListFile,"<BR><BR>");
				FileIO.appendStringToFile(sTempScriptListFile,"<B>Scripts Executed:</B>");


				//Get Script Info
				for (int x = 0; x < lsScripts.length; x++)
				{
					//get script content
					String sTmp = getScriptContent(sResultFileIn, gsScriptHeader + lsScripts[x], gsScriptFooter);

					//display SCRIPT pass/fail status
					if (getFailedScripts(sTmp).indexOf(lsScripts[x].trim()) != -1)
					{
						//write out a new node for each script
						FileIO.appendStringToFile(sTempScriptListFile,"<TR BGCOLOR=" + sDQ + "white" + sDQ + "><TD ALIGN=left><B>");

						FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
						FileIO.appendStringToFile(sTempScriptListFile,"<A HREF="+sDQ+"#" + lsScripts[x] +sDQ+ ">" + lsScripts[x] + "</A>");
						FileIO.appendStringToFile(sTempScriptListFile,"</FONT></B></TD>");

						FileIO.appendStringToFile(sTempScriptListFile,"<TD BGCOLOR=" + sDQ + "red" + sDQ + "><B>");
						FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
						FileIO.appendStringToFile(sTempScriptListFile,"FAIL");
						FileIO.appendStringToFile(sTempScriptListFile,"</FONT></B></TD>");

						FileIO.appendStringToFile(sTempScriptListFile,"</TR>");

						sScript = "<TR BGCOLOR=" + sDQ + "silver" + sDQ + "><TD ALIGN=left><B>" + "<FONT FACE=" + sDQ + "arial" + sDQ +">" + lsScripts[x] + "</FONT></B></TD>" + "<TD BGCOLOR=" + sDQ + "red" + sDQ + "><B>" + "<FONT FACE=" + sDQ + "arial" + sDQ +">" + "FAIL" + "</FONT></B></TD>" + "</TR>";

					}
					else
					{
						FileIO.appendStringToFile(sTempScriptListFile,"<TR BGCOLOR=" + sDQ + "white" + sDQ + "><TD ALIGN=left><B>");

						FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
						FileIO.appendStringToFile(sTempScriptListFile,"<A HREF="+sDQ+"#" + lsScripts[x] +sDQ+ ">" + lsScripts[x] + "</A>");
						FileIO.appendStringToFile(sTempScriptListFile,"</FONT></B></TD>");

						FileIO.appendStringToFile(sTempScriptListFile,"<TD BGCOLOR=" + sDQ + "green" + sDQ + "><B>");
						FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
						FileIO.appendStringToFile(sTempScriptListFile,"PASS");
						FileIO.appendStringToFile(sTempScriptListFile,"</FONT></B></TD>");
						
						//additional column for script elapsed time in html result output for future reference
						//FileIO.appendStringToFile(sTempScriptListFile,"<TD BGCOLOR=" + sDQ + "white" + sDQ + "><B>");
						//FileIO.appendStringToFile(sTempScriptListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
						//FileIO.appendStringToFile(sTempScriptListFile,"00:01:43");
						//FileIO.appendStringToFile(sTempScriptListFile,"</FONT></B></TD>");

						FileIO.appendStringToFile(sTempScriptListFile,"</TR>");

						sScript = "<TR BGCOLOR=" + sDQ + "silver" + sDQ + "><TD ALIGN=left><B>" + "<FONT FACE=" + sDQ + "arial" + sDQ +">" + lsScripts[x] + "</FONT></B></TD>" + "<TD BGCOLOR=" + sDQ + "green" + sDQ + "><B>" + "<FONT FACE=" + sDQ + "arial" + sDQ +">" + "PASS" + "</FONT></B></TD>" + "</TR>";

					}







					//get Testcases for selected script
					lsTestcases = getTestcasesFromBlock(extractBlock(sResultFileIn, gsScriptHeader + lsScripts[x], gsScriptFooter));



					//System.out.println("Testcases: " + lsTestcases.length);
					FileIO.appendStringToFile(sTempTestcaseListFile,"<BR><BR>");

					//write anchor
					FileIO.appendStringToFile(sTempTestcaseListFile,"<A NAME=" + sDQ + lsScripts[x] + sDQ + ">" + "</A>");

					FileIO.appendStringToFile(sTempTestcaseListFile,"<TABLE BORDER STYLE=\"border-width: 1px;border-spacing: ;border-style: outset;border-color: gray;\" WIDTH=75%>");

					FileIO.appendStringToFile(sTempTestcaseListFile,sScript);

					//add testcases
					for (int z = 0; z < lsTestcases.length; z++) {
						String sFailedCase = getFailedTestcases(sResultFileIn).trim();
						String sTestCase = lsTestcases[z].trim();

						if ((!sFailedCase.isEmpty()) && (sFailedCase.indexOf(sTestCase) != -1)) {
						   // display failed TESTCASES in red
				
//							for (int z = 0; z < lsTestcases.length; z++)
//							{
	//
//								if (getFailedTestcases(sResultFileIn).trim().indexOf(lsTestcases[z].trim()) != -1)
//								{
//							
							
							//display failed TESTCASES in red
							FileIO.appendStringToFile(sTempTestcaseListFile,"<TR ALIGN=left><TD WIDTH=85%><SMALL>");

							FileIO.appendStringToFile(sTempTestcaseListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
							FileIO.appendStringToFile(sTempTestcaseListFile,"<A HREF="+sDQ+"#" + gsTestcaseHeader + lsTestcases[z] + sDQ + ">" + lsTestcases[z] + "</A>");
							FileIO.appendStringToFile(sTempTestcaseListFile,"</FONT></SMALL></TD>");

							FileIO.appendStringToFile(sTempTestcaseListFile,"<TD BGCOLOR=" + sDQ + "red" + sDQ + ">");
							FileIO.appendStringToFile(sTempTestcaseListFile,"<SMALL>");
							FileIO.appendStringToFile(sTempTestcaseListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
							FileIO.appendStringToFile(sTempTestcaseListFile,"FAIL");
							FileIO.appendStringToFile(sTempTestcaseListFile,"</FONT></SMALL></TD>");

							FileIO.appendStringToFile(sTempTestcaseListFile,"</TR>");

						}
						else
						{
							FileIO.appendStringToFile(sTempTestcaseListFile,"<TR ALIGN=left><TD WIDTH=85%><SMALL>");

							FileIO.appendStringToFile(sTempTestcaseListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
							FileIO.appendStringToFile(sTempTestcaseListFile,"<A HREF="+sDQ+"#" + gsTestcaseHeader + lsTestcases[z] + sDQ + ">" + lsTestcases[z] + "</A>");
							FileIO.appendStringToFile(sTempTestcaseListFile,"</FONT></SMALL></TD>");

							FileIO.appendStringToFile(sTempTestcaseListFile,"<TD BGCOLOR=" + sDQ + "green" + sDQ +">");
							FileIO.appendStringToFile(sTempTestcaseListFile,"<SMALL>");
							FileIO.appendStringToFile(sTempTestcaseListFile,"<FONT FACE=" + sDQ + "arial" + sDQ +">");
							FileIO.appendStringToFile(sTempTestcaseListFile,"PASS");
							FileIO.appendStringToFile(sTempTestcaseListFile,"</FONT></SMALL></TD>");

							FileIO.appendStringToFile(sTempTestcaseListFile,"</TR>");
						}


				}

				//end table for tescases
				FileIO.appendStringToFile(sTempTestcaseListFile,"</TABLE BORDER>");

			}





			//spacer for complete script contents portion of result
			FileIO.appendStringToFile(sTempTestcaseActionsFile,"<BR><BR>");

			//write out complete script content - marking each testcase with an anchor tag
			writeTestcaseContent(sResultFileIn, sTempTestcaseActionsFile);
			}
			catch (Exception err)
			{
			//do nothing here
			}


			//create Detailed auto results - HTML result file
			//write Script link list result portion - Section #1
			FileIO.appendStringToFile(sPassFailFileDetails,FileIO.getFileContentsByUTF8(sTempScriptListFile));
			FileIO.appendStringToFile(sPassFailFileDetails,"</TABLE BORDER>");
			FileIO.appendStringToFile(sPassFailFileDetails,"<BR><BR><BR><BR>");

			//write Testcase link list portion of result log - Section #2
			FileIO.appendStringToFile(sPassFailFileDetails,FileIO.getFileContentsByUTF8(sTempTestcaseListFile));
			FileIO.appendStringToFile(sPassFailFileDetails,"<BR><BR><BR><BR>");

			//write complete script contents of result file - Section #3
			FileIO.appendStringToFile(sPassFailFileDetails,FileIO.getFileContentsByUTF8(sTempTestcaseActionsFile));
			FileIO.appendStringToFile(sPassFailFileDetails,"</HTML>");

			//create simple pass/fail HTML results file
			FileIO.appendStringToFile(sPassFailFile,FileIO.getFileContentsByUTF8(sTempScriptListFile));
			FileIO.appendStringToFile(sPassFailFile,"</TABLE BORDER>");
			FileIO.appendStringToFile(sPassFailFile,"<BR><BR><BR><BR>");

			//write Testcase link list portion of result log
			FileIO.appendStringToFile(sPassFailFile,FileIO.getFileContentsByUTF8(sTempTestcaseListFile));
			FileIO.appendStringToFile(sPassFailFile,"<BR><BR><BR><BR>");
			FileIO.appendStringToFile(sPassFailFile,"</HTML>");



		}

		/**
		 * Update log sign color. The color is defined based on log type. For
		 * example: if current log type is LOGTYPE_TIME_PASS_FAIL, then "PASS" will
		 * be set as GREEN color; if current log type is LOGTYPE_DEBUG_INFO, then
		 * "DEBUG" will be set as DARK YELLOW.
		 * <p>
		 * @param sLog - log content
		 * @return String - updated log content
		 */
		private static String changeLogSignColor(String sLog) {
			//display PASS log sign with green color
			if (sLog.indexOf(gsPass)!=-1) {
				sLog = Strings.replace(sLog, gsPass,
						"<span style='color:#009900;font-weight:bold'>PASS</span> - ");
			}
			//display DEBUG log sign with saffron color
			if (sLog.indexOf(gsDebug)!=-1) {
				sLog = Strings.replace(sLog, gsDebug,
						"<span style='color:#FF6600;font-weight:bold'>DEBUG</span> - ");
			}
			//other log sign
			return sLog;
		}

		
		/**
		 * Update test case result anchor and description with html tag to display them
		 * with different color according result type. If result is pass, then
		 * green; if fail, then red.
		 * <p>
		 * @param sLogLine
		 * @return  test case result info in appropriate color green for pass and red for fail 
		 */
		private static String colorTestcaseResultAnchor(String sLogLine) {
			if (sLogLine == null || sLogLine.trim().length() == 0) {
				return "";
			}

			if (sLogLine.trim().indexOf(gsTestcaseFailFooter) == 0) {
				sLogLine = "<span style='color:red;font-weight:bold'>" + sLogLine
						+ "</span>";
			} else if (sLogLine.trim().indexOf(gsTestcasePassFooter) == 0) {
				sLogLine = "<span style='color:green;font-weight:bold'>" + sLogLine
						+ "</span>";
			} else {
				return sLogLine;
			}
			return sLogLine;
		}




		/**
		 * Writes out entire script contents marking each testcase with an html anchor tag
		 * for HTML result file output. Only to be used within writePassFailResultsToHtmlFormat() method
		 * @param sResFileNameIn - Result file
		 * @param sResFileNameOut - temporary Html formatted result file
		 */
		private static void writeTestcaseContent(String sResFileNameIn, String sResFileNameOut)
		{

			try
			{
			String sDQ = "\"";
			//get result file
			File resfile = new File(sResFileNameIn);

			//read selected result file into string array
			//System.out.println(resfile.getPath());
			String[] lsContents = FileIO.getFileContentsAsListByUTF8(resfile.getPath());

			//write out line of script marking testcases with an html anchor tag
			for (int x = 0; x < lsContents.length; x++)
				{
					//if line item is not null write it out
					if (lsContents[x] != null)
					{
						//change log sign color
						lsContents[x] = changeLogSignColor(lsContents[x]);
						lsContents[x] = colorTestcaseResultAnchor(lsContents[x]);
						//lsContents[x] = htmlAuchorSign(lsContents[x]);
						lsContents[x] = colorInvocationInfomation(lsContents[x]);

						//if lineitem contains a testcase then mark it with an html anchor tag
						if (lsContents[x].indexOf(gsTestcaseHeader) != -1)
						{
							FileIO.appendStringToFile(sResFileNameOut,"<A NAME=" + sDQ + lsContents[x] + sDQ + ">" + "</A>");
						}

						//if lineitem contains a failure then print it in red
						if (lsContents[x].indexOf(gsFail) != -1)
						{
							FileIO.appendStringToFile(sResFileNameOut,"</FONT><FONT COLOR=" +sDQ + "red" + sDQ + "><B>");
						}

//						write script contents - line item at a time
						FileIO.appendStringToFile(sResFileNameOut,lsContents[x]  + "<BR>");

						//stop printing in red if error info has already been captured
						if (lsContents[x].indexOf(gsTestcaseHeader) != -1 || lsContents[x].indexOf(gsImageMarker) != -1 || lsContents[x].indexOf("==============================================================================") != -1 || lsContents[x].indexOf("*******************************************************") != -1 || lsContents[x].indexOf(gsAbortSign)!=-1)
						{
							FileIO.appendStringToFile(sResFileNameOut,"</B></FONT><FONT COLOR=" +sDQ + "black" + sDQ + ">");
						}

						//if lineitem contains an error image insert image into HTML results
						if (lsContents[x].indexOf(gsImageMarker) != -1)
						{

							int i = lsContents[x].indexOf(gsImageMarker);
							String sImgPath = lsContents[x].substring(i + gsImageMarker.length());
							File f = new File(sImgPath);

							//This is where I need to update to see Images imbedded in logs sent to SCTM --Tony
							FileIO.appendStringToFile(sResFileNameOut,"<IMG SRC=" + sDQ + f.getName().trim() + sDQ + "><BR>");

						}
					}
				}


			}
			catch (Exception e)
			{
			//do nothing here
			}

		}

		/**
		 * Color stack trace information.
		 * @param string one line of log content
		 * @return colored log content
		 */
		private static String colorInvocationInfomation(String string) {
			int index = string.indexOf("Trace[");
			if(index==-1){
				return string;
			}
			String log = string.substring(0, index);
			String str = "<span style='color:#888;font-size:12px;'>"+string.substring(index)+"</span>";
			return log+str;
		}

		
		
		
		
		/**
		 * Returns String containing the current scripts path information
		 * @return String containing the current scripts path information
		 */
		public static String getTopScriptDir()
		{
		    String dsLoc = Platform.getCurrentProjectPath();
		    return dsLoc + File.separator ;

		}

		/**
		 * Returns a string containing the automation datastore directory
		 * @return string containing the automation datastore directory
		 */
		public static String getDatastoreDir()
		{
			return Platform.getCurrentProjectPath() + Platform.getFileSeparator();
		}


		/**
		 * Returns a string containing the automation datastore parent directory
		 * @return string containing the automation datastore parent directory
		 */
		public static String getDatastoreParentDir()
		{
			return getDatastoreDir();

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
		
		
		
//		/**
//		 * sign that automation script is running or not
//		 */
//		protected static boolean isRunning = false;
	//
//		/**
//		 * block the program when writting action is not over, lock the resource to
//		 * avoid conflict
//		 */
//		protected static boolean isWrittenOver = true;
	//
//		/**
//		 * thread used to wrapper real time log event
//		 */
//		protected static Thread realTimeLogger = null;
	//
		




		/**
		 * Append stack trace after log content. This will be very helpful to locate hidden performance issue.
		 */
		public static String getInvocationInfo(){
			String ivkInfo = "";
			//if not append log type, return empty string
			if(giAutomationLogType != LOGTYPE_APPEND_INVOCATION){
				return ivkInfo;
			}

			//get stack trace information
			Map<?,?> stackTraces = Thread.getAllStackTraces();
			Set<?> keys = stackTraces.keySet();
			Iterator<?> it = keys.iterator();
			Object key = null;

			//go throught
			while(it.hasNext()){
				key = it.next();
				StackTraceElement[] trace = (StackTraceElement[])stackTraces.get(key);
				boolean found = false;
				String className = null;
				String methodName = null;
				for(int i=0;i<trace.length;i++){
					methodName = trace[i].getMethodName();
					className = trace[i].getClassName();
					if(methodName.equals("getInvocationInfo")){
						found = true;
					}else if(found && !className.startsWith("core")){
						ivkInfo = ivkInfo +" Trace["+trace[i]+"]";
						break;
					}
				}
			}
			return ivkInfo;
		}

		
		
		
		/**
		 * Append new content into result file.
		 * result file will not be copied to suite file during autoCleanup().
		 * only append to result file or suite result file result file content will be copied to
		 * suite result file.
		 * <p>
		 * @param content log content to append to result file
		 */
		public static void appendContentToResultFile(String content) {

				if (gsResultFile != null){
					FileIO.appendStringToFile(gsResultFile, content);
				}

				if (gbIsSuite == true && gsSuiteResultFile != null) {
					FileIO.appendStringToFile(gsSuiteResultFile, content);
				}

		}

	//
//		/**
//		 * Get running test case's description and store in an array. <br>
//		 * Test case header is like "* Testcase - Start - My First Testcase - Date:
//		 * 2008-4-10 19:36:56". <br>
//		 * This method will only get the concrete description content such as "My First
//		 * Testcase".
//		 * @param sFile
//		 *
//		 * @param sFile -
//		 *            result file
//		 * @return String[] - test cases description array
//		 * @Author: Tony Venditti
//		 */
//		public static String[] getRunningTestCasesDescription(String sFile) {
//			String[] lsResFile = FileIO.getFileContentsAsListByUTF8(sFile);
//			StringBuffer sScripts = new StringBuffer();
//			String[] lsScripts = { "" };
	//
//			// find Scripts
//			int idx1 = -1;
//			int idx2 = -1;
//			for (int g = 0; g < lsResFile.length; g++) {
//				if (lsResFile[g] != null) {
//					idx1 = lsResFile[g].indexOf(gsTestcaseHeader);
//					if (idx1 != -1) {
//						idx2 = lsResFile[g].indexOf(" - Date");
//						sScripts.append(
//								lsResFile[g].substring(idx1
//										+ gsTestcaseHeader.length(), idx2)).append(
//								gsNewline);
//					}
//				}
//			}
	//
//			lsScripts = Strings.stringToStringArray(sScripts.toString(), gsNewline);
	//
//			return lsScripts;
//		}
	//	
	//	
//		/**
//		 * This method return the description of running test case. And the
//		 * description is used to locate the test case dot information in progress
//		 * bar file, then new dot information can be inserted into progress bar file
//		 * correctly.<br>
//		 *
//		 *
//		 * @return
//		 * @Author: Tony Venditti
//		 */
//		public static String[] getRunningTestCasesDescription() {
//			return getRunningTestCasesDescription(gsResultFile);
//		}
		
		
		
		/**
		 * This method counts the number of execution per each suite and updates the automation counter spreadsheet.
		 * <p>
		 * @param String sFileName name of the EXCEL Automation Counter spreadsheet
		 * @param String sSheetName name of the sheet to use in the Excel spreadsheet
		 * @param int NoOfScriptsExecuted the number of scripts executed in the suite - automatically counted in suite execution loop
		 * @param List<String> hmCounter row data from the spreadsheet 
		 * @param String sSuiteName the name of the suite executed 
		 */
		public static void autoROICounter(String sFileName , String sSheetName, int NoOfScriptsExecuted, List<String> hmCounter, String sSuiteName ) 
		{
			try{
			//get the automation result path from the automation.properties gsAutomationAutoResultPath global variable
			String sCounterSheetPath = Log.gsAutomationAutoResultPath+gsExecutionROICounterSubDir ;
			//System.out.println(sCounterSheetPath);
			
					if(NoOfScriptsExecuted!=0)
					{
						
						String countStr=hmCounter.get(0);
						
						if(null==countStr || countStr.isEmpty())
						{
							iROICounter=0;
						}
						else
						{

							iROICounter=Integer.parseInt(countStr);
						}
						iROICounter = iROICounter + 1;


						Map<String, String> map =new HashMap<String, String>();

						map.put("filepath",sCounterSheetPath+sFileName+Log.gsAutomationTestDataSpreadsheetSuffix);
						
						map.put("dummyfilepath",sCounterSheetPath+sFileName+"1"+Log.gsAutomationTestDataSpreadsheetSuffix);
						map.put("sheetName", sSheetName);
						map.put("headerlabel", "counter");
						
						//update the counter sheet
						Excel.updateColumn(String.valueOf(iROICounter), map,sSuiteName);

					}
					else
					{

						//System.out.println("NoOfScriptsExec:" +NoOfScriptsExecuted);
						Log.logScriptInfo("Full suite not Executed");

					}
			}
			catch(Exception e){
				Log.errorHandler("Error occurred during ROI Execution tracking", e);
			}
		}
		
		
		
		/**
		 * Copies automation result files to Silk Central 
		 */
		public static void copyResultsToSilkCentral( )
		{
		//if option is true then send results to SCTM web server
				if (Log.gbAutomationSilkCentral == true){
					//Copy results to TestArea folder so that results can be uploaded to SCTM
					int iPos = Strings.lastIndexOfIgnoreCase(Log.gsScriptTestArea,"\\");
					String sSCTMResultDir = Log.gsScriptTestArea.substring(0,iPos);
					//System.out.println(sSCTMResultDir);
					FileIO.copyDir(Log.gsAutomationAutoResultPath, sSCTMResultDir);
					
					/*
					//Write Selenium results to SCTM output.xml to 
					//display correct testcase pass/fail results in SCTM 
					//collect pass fail data
					//write pass fail data to new output.xml
					//replace existing output.xml
					String sOutPutTMP = sSCTMResultDir +Platform.getFileSeparator() + "output.tmp";
					String sOutPutXML = sSCTMResultDir +Platform.getFileSeparator() + "output.xml";
					String sOriginalXML = sSCTMResultDir +Platform.getFileSeparator() + "original.xml";
					System.out.println("Look for: " + sOutPutXML);
					
					
					//output.xml must be in the correct format
					if (FileIO.fileExists(sOutPutXML)){
						//rename existing output.xml file
						System.out.println("Found1: " + sOutPutXML);
						FileIO.copyFile(sOutPutXML, sOriginalXML);
					}
					else{
						System.out.println("Did not Find1: " + sOutPutXML);
					}
					
					//create new empty output.xml file
					FileIO.writeFileContentsByUTF8(sOutPutTMP, ""); 
						
					//write new output.xml with correct pass fail data
					FileIO.writeFileContents(sOutPutTMP, "<?xml version='1.0' encoding='UTF-8'?>"); 
					FileIO.appendStringToFile(sOutPutTMP,"<ResultElement TestItem='Selenium Test'>"); 
						
					//Total Number of testcases executed
					FileIO.appendStringToFile(sOutPutTMP,"<RunCount>"+ giTestCaseCounter + "</RunCount>");
					
					//Number of error encountered in you"re the script
					FileIO.appendStringToFile(sOutPutTMP,"<ErrorCount>"+ giTestCaseFailCounter + "</ErrorCount>");
										
					//Number of failures (same as errors) encountered in you"re the script
					FileIO.appendStringToFile(sOutPutTMP,"<FailureCount>"+ giTestCaseFailCounter + "</FailureCount>");
						
					//Was Test a success (pass or fail) 
					if (giTestCaseFailCounter > 0){
						FileIO.appendStringToFile(sOutPutTMP,"<WasSuccess>false</WasSuccess>");
						}
					else{
						FileIO.appendStringToFile(sOutPutTMP,"<WasSuccess>true</WasSuccess>");
					}
						
					FileIO.appendStringToFile(sOutPutTMP,"<Incident>");
						
					//Message to appear in Results -> Messages
					FileIO.appendStringToFile(sOutPutTMP,"<Message>some_message_text_</Message>");
						
					//Message Severity
					if (giTestCaseFailCounter > 0){
						FileIO.appendStringToFile(sOutPutTMP,"<Severity>Error</Severity>");}
					else{	
						FileIO.appendStringToFile(sOutPutTMP,"<Severity>Pass</Severity>");}
					
					FileIO.appendStringToFile(sOutPutTMP,"</Incident>");
					FileIO.appendStringToFile(sOutPutTMP,"</ResultElement>"); 
					
					Platform.sleep(Log.giAutomationNormalTO);
					
					//check for SCTM output.xml and rename it
					if (FileIO.fileExists(sOutPutXML)){
						//rename existing output.xml file
						System.out.println("Found2: " + sOutPutXML);
						FileIO.copyFile(sOutPutXML, sOriginalXML);
					}
					else{
						System.out.println("Did not Find2: " + sOutPutXML);
					}	
					
					
					FileIO.copyFile(sOutPutTMP, sOutPutXML);
					*/
				}
		}





}
