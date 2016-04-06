package dummy.googly.scripts;

import javax.swing.JOptionPane;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.Browser;
import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.VideoRecord;
import dummy.googly.applibs.GoogleMainPage;

public class Test1 {

	@BeforeClass
	/**
	 * Initializes the automation for testing.
	 * <P>
	 * @throws throws an Exception if an error occurs during the setup portion of the script
	 * @author Rajinder Singh
	 */
	public static void setUp() throws Exception {
		
		
	
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();  //this.getClass().getName().toString();
		Log.gsScriptDescription = "Test and verify Test1";
		Log.gsScriptAuthor = "Rajinder Singh";
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator())); 	//script test area uses the project path info and is auto-generated via this code so do not modify this item 	
		
		Log.initialize();
		
		VideoRecord.startRecording();
		
//		Log.logBanner("Setup for GoogleSearch Testcases");
//		Browser.start();
		//load Google page and wait for search button to appear
//		Browser.loadURL(GoogleMainPage.gsGoogleURL,Log.giAutomationShortTO);
			
	}
	
	@Test
	public void test() throws Exception {
		Log.startTestCase("Test1");
		msgbox("Voilla.......");
		Log.logScreenCapture("Pop Up");

	}

	private void msgbox(String s){
		   JOptionPane.showMessageDialog(null, s);
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
	
	
}
