package dummy.googly.scripts;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;

/**
 * This Suite executes all of the scripts in the Google application project
 * @author
 */
public class DummySuiteGoogle {
		
	/**
	 * The SUITE setUp() function initializes the automation for the execution of a SUITE of scripts
	 * @throws throws an Exception if a failure occurs during setup process 
	 */
	@BeforeClass
	public static void setUp() throws Exception {
	
		Log.gsScriptName = Thread.currentThread().getStackTrace()[1].getClassName();	//script name is auto-generated via this code so DO NOT modify this item
		Log.gsScriptDescription = "Google Test Suite";
		Log.gsScriptAuthor = "Raj";
		Log.gsScriptTestArea = FileIO.getParentPath(Platform.getCurrentProjectPath() + 
							   Log.gsScriptName.replace(".",Platform.getFileSeparator())); 	
		
		//initialize for Suite run
		Log.initializeSuiteStats();
				
	}
	

	
	/**
	 * This SUITE script executes all of the Google test scripts in the lcScripts list
	 */
	@Test
	public void testGoogleRegressionSuite(){
			
		//script lists
		Class<?>[] lcScripts = {
				
				//All google scripts
				ExampleScript.class,
				GoogleAbout.class,
				GoogleSearch.class,
				Test1.class
				
		};
		
		
		
		//execute scripts in list above
		for (int x =0;x< lcScripts.length;x++){
 			try{
 			org.junit.runner.JUnitCore.runClasses(lcScripts[x]);
 			}
 			catch(Exception e){
 				Log.errorHandler("Error occurred during execution of script: " + lcScripts[x].toString(),e);
 			}
 		}
				
			//to run scripts from the command line use the following
			//set CLASSPATH=%JUNIT_HOME%\junit.jar;c:\myproject\classes;c:\myproject\lib\something.jar
			//java org.junit.runner.JUnitCore TestClass1 [...other test classes...]

	}
	
	
	/**
	 * This function terminates the SUITE, calculates the SUITE metrics and reports SUITE results
	 * @throws an Exception if an error occurs during the termination portion of the script
	 */
	@AfterClass
	public static void tearDown() throws Exception {
				
			//Gather SUITE metrics and terminate automation 
			Log.logSuiteStats();
			
	}

}