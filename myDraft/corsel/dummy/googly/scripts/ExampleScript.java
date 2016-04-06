package dummy.googly.scripts;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.Browser;
import dummy.core.libs.Excel;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.VideoRecord;
import dummy.googly.applibs.GoogleMainPage;


/**
 * @TestScript: GoogleAbout 
 * @Description: This script exercises Google About link and verifies About content
 * @Author: Tony Venditti
 */
public class ExampleScript  {
	
	//Excel test data variables
	static Map<String, String> hmSearchList;
	static final String sTESTDATA_FILENAME = "Google_TestData";
	static final String sTESTDATA_SEARCHLIST_SHEETNAME = "SearchList";
	
	//final String sTESTDATA_FILENAME = "CN_TestData";

	final String sTESTDATA_PAYMENT_SHEETNAME = "DomesticBulkPayment";

	Map<String, String> hmCreateDomesticBulkPayment, hmEditDomesticBulkPayment;
	
	@BeforeClass
	/**
	 * @Function: setUp() 
	 * @Description: This function initializes the automation for testing
	 */
	public static void setUp() throws Exception {
	
		//Standard script information section
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();
		Log.gsScriptDescription = "Test and verify Google About page";
		Log.gsScriptAuthor = "Tony Venditti";
		Log.gsScriptTestArea = Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator());
		
		Log.initialize(); //Standard script initialization routine
		
		VideoRecord.startRecording();
	
		Browser.start();
		//load Google page and wait for search button to appear
		Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
		GoogleMainPage.btnGoogleSearch().waitForExistence(Log.giAutomationMedTO);
		
		
		
	}
	
	
	
	
	

	@Test
	/**
	 * @Test: testGoogleAbout()  
	 * @Description: This test method executes a testcase that verifies the Google About page content
	 * @Author: Tony Venditti
	 */
	public void testGoogleAbout(){
		
				
		/**
		 * @Testcase: This testcase loads Google's About page and verifies content
		 * This is an example of having multiple defined testcases withion one script
		 * @Author: Tony Venditti
		 */
		
		try{	//good practice to place test case in try catch block 
		
			//ACI Standard Testcase initialization header
			Log.startTestCase("Test and verify Google About page");
			
			//test data
			String sAboutText = "Google’s mission is to organize the world’s information and make it universally accessible and useful.";
			
			//Example of how to get data from Excel sheet and use as test data for script
			hmSearchList = Excel.getXlsRowDataAsMap(sTESTDATA_FILENAME, sTESTDATA_SEARCHLIST_SHEETNAME, "1");
			
			
			
			//Enter search text obtained from Excel test data sheet and enter it into Google Search text field
			GoogleMainPage.tfSearchText().setText(hmSearchList.get("Search Text"));
			
			//Click the Google Search button
			GoogleMainPage.btnGoogleSearch().click();
			
			GoogleMainPage.lnkSettings().click();
			GoogleMainPage.lnkAdvancedSearch().click();
			GoogleMainPage.tfAllTheseWords().setText("testing 123");
			//Platform.sendKeys("Testing");
									
			//Platform.sleep(10);
			GoogleMainPage.lnkSettings().click();
			GoogleMainPage.btnAdvancedSearch().click();
			
			
			GoogleMainPage.tfSearchText().focus();
			//Log.displayMessageDlg("check focus" , "focus");
			
			//Platform.sendKeySelectAll();
			//Platform.sendKeyClipboardCopy();
			
			//Log.displayMessageDlg("check focus" , "focus");
			
			Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
			GoogleMainPage.lnkSettings().click();
			GoogleMainPage.lnkAdvancedSearch().focus();
			
			//GoogleMainPage.lnkAdvancedSearch().click();
			
			//load Google About Page by clicking the About link
			GoogleMainPage.lnkAboutGoogle().click();
			
			Browser.goBack();
			
			GoogleMainPage.lnkSettings().click();
			GoogleMainPage.lnkAdvancedSearch().focus();
			
			GoogleMainPage.lnkImages().focus();
			//Log.displayMessageDlg("check focus on Images link" , "focus");
			
			GoogleMainPage.lnkAdvancedSearch().click();
			//GoogleMainPage.lstLanguage().waitForExistence(30);
			//GoogleMainPage.lstLanguage().selectByLabel("Afrikaans");
			//Log.displayMessageDlg("check Language list selection" , "Afrikaans");
			
			GoogleMainPage.lnkMaps().hover();
			
			Browser.click("Maps");
			Browser.goBack();
			//Log.displayMessageDlg("check hover on Maps link" , "hover");
			
			GoogleMainPage.lnkSettings().click();
			GoogleMainPage.lnkAdvancedSearch().focus();
			
			GoogleMainPage.lnkMaps().mouseOver();
			//Log.displayMessageDlg("check mouseover on News link" , "mouseover");
			
			//Log.displayMessageDlg("click" , "click");
			//load Google About Page by clicking the About link
			GoogleMainPage.lnkAboutGoogle().click();
			
			//Example of screen capture function
			//Log.logScreenCapture("This is a test");
			//Log.displayMessageDlg("minimizewin" , "minwin");
			Platform.minimizeWin("About Google");
			
			//Platform.sleep(10);
			//Log.displayMessageDlg("maximizewin" , "maxwin");
			Platform.maximizeWin("About Google");
			
			
			
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
	
	
	
	
	
	
	@AfterClass
	/**
	 * @Function: tearDown() 
	 * @Description: This function ends the test and terminates the automation
	 */
	public static void tearDown() throws Exception {
		
		VideoRecord.stopRecording();
		//Gather metrics, report results and terminate automation 
		Log.terminate();
	}

}







