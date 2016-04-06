package dummy.googly.scripts;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.Browser;
import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.VideoRecord;
import dummy.googly.applibs.GoogleMainPage;



/**
 * The GoogleSearch script executes several searches and verifies expected search results 
 * <P>
 */
public class GoogleSearch{
	
	//static ScreenRecorder screenRecorder;
	
	@BeforeClass
	/**
	 * Initializes the automation for testing.
	 * <P>
	 * @throws throws an Exception if an error occurs during the setup portion of the script
	 * @author Rajinder Singh
	 */
	public static void setUp() throws Exception {
		
		
	
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();  //this.getClass().getName().toString();
		Log.gsScriptDescription = "Test and verify Google Searches";
		Log.gsScriptAuthor = "Rajinder Singh";
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator())); 	//script test area uses the project path info and is auto-generated via this code so do not modify this item 	
		
		Log.initialize();
		
		VideoRecord.startRecording();
		
		Log.logBanner("Setup for GoogleSearch Testcases");
		Browser.start();
		//load Google page and wait for search button to appear
		Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
			
	}
	
	
//	/**
//	 * These testcases execute a series of Google searches and verify expected results
//	 * <p>
//	 * 
//	 */
//	@Test()
//	public void testGoogleSearch(){
//		
//		try{
//		//Test Data
//		String search_data[][] = {
//		{"Patriots","Official Website of the New England Patriots"},
//		{"Jets","Official Site of the New York Jets"},
//		{"Colts","Official Website of the Indianapolis Colts"},
//		{"Bears","Da Bears"},
//		{"Raiders","Oakland Raiders"}
//		};
//		
//	//	Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
//		
//		//loop through all Search scenarios
//		for (int x = 0; x < search_data.length;x++)
//		{
//		//Execute Testcase
//		Log.startTestCase("Test Google Search: - " + search_data[x][0] +  " - Result: - " + search_data[x][1]);
//		GoogleMainPage.googleSearch(search_data[x][0]);
//		
//		//verify Search Results
//		GoogleMainPage.verifyResultLink(search_data[x][1]);
//		}
//		}
//		catch(Exception e){
//			Log.errorHandler("Error occurred during Test Google Search Testcase.",e);
//		}
//		
//	}
	
	/**
	 * @Test: testGoogleAbout()  
	 * @Description: This test method executes a test case that verifies the Google About page content
	 * @Author: Rajinder Singh
	 */
	@Test()
	public void testGoogleAbout() throws Exception {
		
				
		/**
		 * @Testcase: This testcase loads Google's About page and verifies content
		 * This is an example of having multiple defined testcases withion one script
		 * @Author:Rajinder Singh
		 */
		
		try{	//good practice to place test case in try catch block 
		
			// Standard Testcase initialization header
			Log.startTestCase("Test and verify Google About page");
			
			//test data
			String sAboutText = "Google’s mission is to organize the world’s information and make it universally";    // accessible and useful.
			
			Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
			GoogleMainPage.lnkAboutGoogle().focus();
			GoogleMainPage.lnkAboutGoogle().click();
			
			
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
	 * This function ends the test, calculates and logs the test results and terminates the automation
	 * @author Rajinder Singh
	 */
	public static void tearDown() throws Exception {
		
		VideoRecord.stopRecording();
		
		//Gather metrics, report results and terminate automation 
		Log.terminate();
		
	
		
	
				
	}
	
	
	
//	
//	public static void startRecording() throws Exception
//    {
//                          
//         GraphicsConfiguration gc = GraphicsEnvironment
//            .getLocalGraphicsEnvironment()
//            .getDefaultScreenDevice()
//            .getDefaultConfiguration();
//
//        screenRecorder = new ScreenRecorder
//        	(gc,new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
//        		new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                 CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15),
//                 QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
//                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null);
//        screenRecorder.start();
//      
//    }
//
//    public static void stopRecording() throws Exception
//    {
//      screenRecorder.stop();
//    }


}







