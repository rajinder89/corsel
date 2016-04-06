package dummy.core.templates;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.Browser;
import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;

/**
 * Standard ACI Selenium Test Script Template file.
 * <p>
 * Place your description of what the test script tests here. 
 * 
 */
public class TemplateScript {
	
	
	
	/**
	 * Initializes the automation for testing.
	 * 
	 * @throws Exception if an error occurs during the setup portion of the script
	 * 
	 */
	@BeforeClass
	public static void setUp() throws Exception {
	
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();	//script name is auto-generated via this code so DO NOT modify this item
		
		// TODO: change script descrition
		Log.gsScriptDescription = "Add test description here";		//update test description
		// TODO: change script author
		Log.gsScriptAuthor = "Tony Venditti";						//update script author
		
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator())); 	//script test area uses the project path info and is auto-generated via this code so DO NOT modify this item
		
		Log.initialize();		//Reads in Automation.properties values, Logs script header information, initializes script metrics and starts Selenium server 
		
		
		// TODO: change setup description
		Log.logBanner("Setup Description goes here");

		// TODO: replace if needed with your setup actions
		Browser.start(); // start browser
		Browser.loadURL("about:blank", Log.giAutomationShortTO);
		
	}
	
	

	
	

	
	/**
	 * Short test description goes here.
	 * <p>
	 * A Test case description goes here. A test case in this Java-based
	 * Selenium framework is denoted by the use of the
	 * Log.startTestCase("Test case description") method. This script template
	 * contains a sample Test section for ACI Selenium scripts. In order to work
	 * in the JUnit-Eclipse IDE the test method below must contain the word
	 * "test" at the beginning of the method name: for example,
	 * "testMyFirstScript()"
	 * 
	 */
	@Test
	public void testTemplateScriptExample(){
		
		try{		//good practice to wrap your test case in a try/catch block
		
			// TODO: place your test data here
			String sLink = "About";

			// TODO: denote the following code is a test case by specifying the Log.startTestCase("test case description") function here
			Log.startTestCase("Test Case: Verify " + sLink + " exists");

			// TODO: enter some test case code here. Example test code below
			// load test web page
			Browser.loadURL("http://www.google.com", Log.giAutomationShortTO);

			// TODO: verify test results here. Example verification code below.
			if (Browser.linkExists(sLink)) {
				Log.logScriptInfo("Verified Link: " + "\"" + sLink + "\""
						+ " is present");
			} else {
				Log.errorHandler("Link: " + "\"" + sLink + "\"" + " does NOT exist");
			}
		}
		
		catch(Exception e){		//catch all error handler 
			Log.errorHandler("Error occurred in TemplateScript.", e);
		}
	}
	
	
	
	
	
	
	
	/**
	 * This function ends the test, calculates and logs the test results and terminates the automation
	 * <p>
	 * 
	 */
	@AfterClass
	public static void tearDown() throws Exception {		
			
			//Gather metrics, report results and terminate automation 
			Log.terminate();
	}

}







