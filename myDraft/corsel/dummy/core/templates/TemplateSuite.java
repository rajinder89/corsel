package dummy.core.templates;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;


/**
 * Change SUITE file description here
 * Standard Selenium SUITE Template file.
 * For example, This Suite executes all of the scripts in the Google component
 * A SUITE script executes all of the test scripts that you specify in the lcScripts list below
 * 
 */
public class TemplateSuite {
	
		
	/**
	 * The SUITE setUp() function initializes the automation for the execution of a SUITE of scripts
	 * @throws Exception if a failure occurs during setup process 
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
		
		//Reads in Automation.properties values, Logs script header information, initializes script metrics for Suite run
		Log.initializeSuiteStats();	
	}
	
		
	/**
	 * This SUITE script executes all of the test scripts in the lcScripts list defined below
	 * A SUITE script is a special script that contains a collection of other, usually related, scripts to execute.
	 * 
	 */
	@Test
	public void testMyRegressionSuite(){
		
		//script list - lsScripts contains a list of other scripts to execute within this SUITE file 
		Class<?>[]  lcScripts = {
				
				//List all script class names that you want to include in this SUITE in the list below 
				dummy.googly.scripts.GoogleAbout.class,	//Example 1st script name with ".class" extension to run in suite
				dummy.googly.scripts.GoogleSearch.class		//Example 2nd script name with ".class" extension to run in suite
		};
			
		
		//SUITE execution code - runs scripts in list above
		for (int x =0;x< lcScripts.length;x++){
 			try{
 			org.junit.runner.JUnitCore.runClasses(lcScripts[x]);
 			}
 			catch(Exception e){
 				Log.errorHandler("Error occurred during execution of script: " + lcScripts[x].toString(),e);
 			}
 		}
					
	}
	
	/**
	 * This function terminates the SUITE, calculates the SUITE metrics and reports SUITE results
	 */
	@AfterClass
	public static void tearDown() {
				
		//Gather SUITE metrics and terminate automation 
		Log.logSuiteStats();
	}

}