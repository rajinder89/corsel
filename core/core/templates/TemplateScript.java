package core.templates;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import core.libs.Browser;
import core.libs.FileIO;
import core.libs.Log;
import core.libs.Platform;

/**
 * Standard Selenium Test Script Template file.
 * Place your description of what the test script tests here. 
 */

public class TemplateScript {
	
	/**
	 * Initializes the automation for testing.
	 * 
	 */
	@BeforeClass
	public static void setUp() {
	
		//Script name is auto-generated via this code so DO NOT modify this item
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();	
		
		// Change script description
		Log.gsScriptDescription = "Add test description here";		//update test description
		
		// Change script author
		Log.gsScriptAuthor = "Automation Tester";					//update script author
		
		//Script test area uses the project path info and is auto-generated via this code so DO NOT modify this item
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator())); 	
		
		Log.initialize();		//Reads in Automation.properties values, Logs script header information, initializes script metrics 
		
		//  Change setup description
		Log.logBanner("Setup Description goes here");

		// Replace if needed with your setup actions
		Browser.start(); // Start browser
		Browser.loadURL("about:blank", Log.giAutomationShortTO); // Load the application URL
		
	}
	
	
	/**
	 * Short test description goes here.
	 * A Test case description goes here. A test case in this Java-based Selenium framework is denoted by the use of the
	 * Log.startTestCase("Test case description") method. 
	 * This script template contains a sample Test section for Selenium scripts. 
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
				Log.logScriptInfo("Verified Link: " + Log.sDQ + sLink + Log.sDQ + " is present");
			} else {
				Log.errorHandler("Link: " + Log.sDQ + sLink + Log.sDQ + " does NOT exist");
			}
		}
		
		catch(Exception e){		//catch all error handler 
			Log.errorHandler("Error occurred in TemplateScript.", e);
		}
	}
	
	
	/**
	 * This function ends the test, calculates and logs the test results 
	 * and terminates the automation
	 */
	@AfterClass
	public static void tearDown() {		
		
		//Gather metrics, report results and terminate automation 
		Log.terminate();
	}
}