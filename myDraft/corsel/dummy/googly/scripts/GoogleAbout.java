package dummy.googly.scripts;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.Browser;
import dummy.core.libs.Excel;
import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.googly.applibs.GoogleMainPage;


/**
 * This script exercises Google About link and verifies About content.
 * Also performs a single search by getting test search data from an external Excel sheet and other assorted tests 
 * <p>
 */
public class GoogleAbout {
	
	//Excel test data variables
	static Map<String, String> hmSearchList;
	static final String sTESTDATA_FILENAME = "Google_TestData";
	static final String sTESTDATA_SEARCHLIST_SHEETNAME = "SearchList";
	
			
	/**
	 * Initializes the automation for testing.
	 * <P>
	 * @throws an Exception if an error occurs during the setup portion of the script
	 */
	@BeforeClass
	public static void setUp() throws Exception {
	
		//Standard script information section
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();
		Log.gsScriptDescription = "Test and verify Google About page";
		Log.gsScriptAuthor = "Rajinder";
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator())); 	//script test area uses the project path info and is auto-generated via this code so do not modify this item 	
		
		Log.initialize(); //Standard script initialization routine
		
		
	//	VideoRecord.startRecording();
		
	//	Log.printInitializedAutomationProperties();
		
		//GoogleMainPage.loadGoogleProperties(); //loads google global properties from google.properties file
		
		//Application specific setup for test
		Log.logBanner("Setup for GoogleAbout Testcases");
		Browser.start();
		
		
		//load Google page and wait for search button to appear
		Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
		
		
	}
	
	
	
	
	

	/**
	 * This test method executes a testcase that verifies the Google About page content
	 */
	@Test
	public void testGoogleAbout(){
		
		
		try{	//good practice to place test case in try catch block 
		
			// Standard Testcase initialization header
			Log.startTestCase("Test and verify Google About page");
			
			//test data
			String sAboutText = "Google’s mission is to organize the world’s information and make it universally";
			
			//Example of how to get data from Excel sheet and use as test data for script
			hmSearchList = Excel.getXlsRowDataAsMap(sTESTDATA_FILENAME, sTESTDATA_SEARCHLIST_SHEETNAME, "1");
						
			//Enter search text obtained from Excel test data sheet and enter it into Google Search text field
			GoogleMainPage.googleSearch(hmSearchList.get("Search Text"));
			
			
			//Example of logging application properties to the result log 
			Log.logScriptInfo("Google Language: " + GoogleMainPage.gsGoogleLanguage);
			Log.logScriptInfo("Google Location: " + GoogleMainPage.gsGoogleLocation);
			Log.logScriptInfo("Google Username: " + GoogleMainPage.gsGoogleUsername);
			Log.logScriptInfo("Google Password: " + GoogleMainPage.gsGooglePassword);
			Log.logScriptInfo("Google URL: " + GoogleMainPage.gsGoogleURL);
		
			GoogleMainPage.lnkImages().click();
			Platform.sleep(Log.giAutomationNormalTO);
			Log.logScreenCapture("Patriot Images");
			
			if (GoogleMainPage.lnkShopping().isEnabled()){
				Log.logScriptInfo("Shopping Link is enabled.");
			}else{
				Log.logScriptInfo("Shopping Link is disabled.");
			}
			
			Log.logScreenCapture("Shopping link enabled?");
			
			Browser.goBack();
			
			Platform.sleep(Log.giAutomationNormalTO);
			
			Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
			
			GoogleMainPage.lnkAboutGoogle().focus();
			
			GoogleMainPage.lnkAboutGoogle().click();
			
			
			//Example of screen capture function
			//Log.logScreenCapture("This is a test");
			
			//verify About page content
			if (Browser.textExists(sAboutText)){
				//Standard log output
				Log.logScriptInfo("Verified About Page Content: " + "\"" +  sAboutText   + "\"" +  " is present");
			}
			else
			{
				//Standard error handling routine 
				Log.errorHandler("About Page Text: " + "\"" + sAboutText + "\"" + " does NOT exist");
			}
			
			
			

		
		}
		catch(Exception e){
			//Standard error handling routine
			Log.errorHandler("Error occurred during Test and verify Google About page Testcase.",e);
		}
		
		
	}
	
	
	
	
	
	
	
	/**
	 * This function ends the test, calculates and logs the test results and terminates the automation
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		
	//	VideoRecord.stopRecording();
		
	//	VideoRecord.getRecordedFiles();
		
//		VideoRecord.deleteRecordedFile();
		
		//Gather metrics, report results and terminate automation 
		Log.terminate();
	}

}







