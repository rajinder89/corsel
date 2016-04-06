package dummy.core.templates;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;



/**
 * // TODO: change SUITE file description here
 * Standard ACI Selenium SUITE Template file.
 * For example, This Suite executes all of the scripts in the ACI Online Banking Check Services component
 * <p>
 * A SUITE script executes all of the test scripts that you specify in the lcScripts list below
 * 
 */
public class TemplateSuite {
	
	
	
	/**
	 * The SUITE setUp() function initializes the automation for the execution of a SUITE of scripts
	 * <p>
	 * 
	 * 
	 * @throws Exception if a failure occurs during setup process 
	 * 
	 */
	@BeforeClass
	public static void setUp() throws Exception {
	
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();	//script name is auto-generated via this code so DO NOT modify this item
		
		// TODO: change script description
		Log.gsScriptDescription = "Add test description here";		//update test description
		// TODO: change script author
		Log.gsScriptAuthor = "Tony Venditti";						//update script author
		
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".",Platform.getFileSeparator()));	
		
		//initialize for Suite run
		Log.initializeSuiteStats();	
	}
	
	

	
	

	
	/**
	 * This SUITE script executes all of the test scripts in the lcScripts list defined below
	 * A SUITE script is a special script that contains a collection of other, usually related, scripts to execute.
	 * <P>
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
	 * <P>
	 * @throws Exception if an error occurs during the termination portion of the script
	 * 
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		
		
			//Gather SUITE metrics and terminate automation 
			Log.logSuiteStats();
			
	}

}







