package mmt.scripts;

import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.Browser;
import dummy.core.libs.Excel;
import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.googly.applibs.GoogleMainPage;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
MethodListener.class })

/**
 * This script exercises Google About link and verifies About content.
 * Also performs a single search by getting test search data from an external Excel sheet and other assorted tests 
 * <p>
 */
public class GoogleAbout {
	
	{
        System.setProperty("atu.reporter.config", "C:\\Users\\rajinder_singh\\Desktop\\atu.properties");

    }
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
	
		Log.logBanner("Setup for GoogleAbout Testcases");
		
		Browser.start();
		
	//	ATUReports.setWebDriver(SeleniumCore.driver);
		ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");
			
		//load Google page and wait for search button to appear
		Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
		
		
	}
	
	
	
	
	

	/**
	 * This test method executes a testcase that verifies the Google About page content
	 */
	@Test
	public void TestGoogleAbout(){
		
		
		try{	
		
		
			//test data
			String sAboutText = "Google’s mission is to organize the world’s information and make it universally";
			
			//Example of how to get data from Excel sheet and use as test data for script
			hmSearchList = Excel.getXlsRowDataAsMap(sTESTDATA_FILENAME, sTESTDATA_SEARCHLIST_SHEETNAME, "1");
						
			//Enter search text obtained from Excel test data sheet and enter it into Google Search text field
			GoogleMainPage.googleSearch(hmSearchList.get("Search Text"));
			
			
			//Example of logging application properties to the result log 
	
			ATUReports.add("Google Language: " + GoogleMainPage.gsGoogleLanguage, LogAs.PASSED, null);

			ATUReports.add("Google Location: " + GoogleMainPage.gsGoogleLocation, LogAs.PASSED, null);
	
			ATUReports.add("Google Username: " + GoogleMainPage.gsGoogleUsername, LogAs.PASSED, null);
	
			ATUReports.add("Google Password: " + GoogleMainPage.gsGooglePassword, LogAs.PASSED, null);

			ATUReports.add("Google URL: " + GoogleMainPage.gsGoogleURL, LogAs.PASSED, null);
		
			GoogleMainPage.lnkImages().click();
			Platform.sleep(Log.giAutomationNormalTO);

			ATUReports.add("Patriot Images", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			if (GoogleMainPage.lnkShopping().isEnabled()){

				ATUReports.add("Shopping Link is enabled.", LogAs.PASSED, null);
			}else{

				ATUReports.add("Shopping Link is disabled.", LogAs.PASSED, null);
			}

			ATUReports.add("Shopping link enabled?", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
			Browser.goBack();
			
			Platform.sleep(Log.giAutomationNormalTO);
			
			Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
			
			GoogleMainPage.lnkAboutGoogle().focus();
			
			GoogleMainPage.lnkAboutGoogle().click();
			
			//verify About page content
			if (Browser.textExists(sAboutText)){
				//Standard log output
				ATUReports.add("Verified About Page Content: " + "\"" +  sAboutText   + "\"" +  " is present", LogAs.PASSED, null);
			}
			else
			{
				ATUReports.add("About Page Text: " + "\"" + sAboutText + "\"" + " does NOT exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
			
			
			

		
		}
		catch(Exception e){

			ATUReports.add("Error occurred during Test and verify Google About page Testcase." + e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			
		}
		
		
	}
	
	/**
	 * This function ends the test, calculates and logs the test results and terminates the automation
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		
		//Gather metrics, report results and terminate automation 
		Log.terminate();
	}

}







