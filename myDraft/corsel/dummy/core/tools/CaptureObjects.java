package dummy.core.tools;

import org.junit.After;
import org.junit.Before;

import com.thoughtworks.selenium.SeleneseTestBase;

import dummy.core.libs.Browser;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;

/**
 * @TestScript: CaptureObjects
 * @Description: This tool captures UI objects on a webpage loaded in the setup() routine. 
 * It generates UI object getter methods, complete with Javadoc that you can simply copy and paste
 * in to your applibs object library files. 
 * @Author: Tony Venditti
 */
public class CaptureObjects extends SeleneseTestBase {
	
	
	@Before
	/**
	 * @Function: setUp() 
	 * @Description: This function initializes the automation for testing
	 */
	public void setUp() throws Exception {
	
		Log.gsScriptName = this.getClass().getName().toString();
		Log.gsScriptDescription = "Capture UI Objects on page";
		Log.gsScriptAuthor = "Tony Venditti";
		Log.gsScriptTestArea = Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".","\\"); 	
		
		Log.initialize();
		
		Log.logBanner("Load page to capture objects from...");
		
		
		Browser.start();
		//load Google page and wait for search button to appear
		Browser.loadURL("www.google.com");
		Platform.sleep(Log.giAutomationShortTO);
		//Browser.click("Images");
		
		
		
		
	}
	
	
	
	
	
//
//	@Test
//	public void testCreateUIObjects(){	
//				
//		try{
//		
//		//Testcase header
//		Log.startTestCase("Get all objects on page");
//		
//		//object types supported by Selenium
//		getAllObjects("Link");
//		getAllObjects("TextField");
//		getAllObjects("Button");
//
//		}
//		catch(Exception e){
//			Log.errorHandler("Error occurred during Object Capturing.",e);
//		}
//		
//		
//	}
	
	
	 
		
	
//	/**
//	 * Gets all objects of the specified class type
//	 * @param sObjType
//	 */
//	public void getAllObjects(String sObjType){
//	Log.logBanner("getting all " + sObjType + " objects on current page....");
//	String[] lsObjs;
//	String sObjPrefix = "";
//	String sObjText = "";
//	String[] lsInvalidChars = {"_","-","@","#","$","%","^","&","*","(",")","+","=","!","[","]","?"};
	
//	if (sObjType.equals("Button")){
//		lsObjs = SeleniumCore.getBrowser().getSeleniumAPI().getAllButtons(); 
//		sObjPrefix = "btn";
//	}
//	else if (sObjType.equals("TextField")){
//		lsObjs = SeleniumCore.getBrowser().getSeleniumAPI().getAllFields();
//		sObjPrefix = "tf";
//	}
//	else if (sObjType.equals("WindowIds")){
//		lsObjs = SeleniumCore.getBrowser().getSeleniumAPI().getAllWindowIds();
//		sObjPrefix = "win";
//	}
//	else {
//		lsObjs = SeleniumCore.getBrowser().getSeleniumAPI().getAllLinks();
//		sObjPrefix = "lnk";
//	}
	
	
	
//	
//	
//	for (int x =0; x< lsObjs.length; x++){
//	  String sObj = lsObjs[x];
//	  
//	  
//	  	
//		if (!sObj.equals("")){
//						
//			//get object info
//			WebWidget oObj = new WebWidget(sObj, sObjType);
//			sObjText = oObj.getText();
//					
//			if (sObjText.equals("")){
//				sObjText = sObj;
//			}
//			
//			//remove invalid characters
//			//System.out.println(sObjText);
//			sObjText = Strings.removeInvalidChars(sObjText,lsInvalidChars);  
//			//System.out.println(sObjText);
//			
//			Log.logScriptInfo("",3);
//			Log.logScriptInfo("/**",3);
//			Log.logScriptInfo("* @Object: " +sObjPrefix + Strings.removeWhiteSpace(sObjText),3);
//			Log.logScriptInfo("* @Description: " + sObjType + " object for " + Strings.sDQ + sObjText + Strings.sDQ + " " + sObjType + " with locator of " + sObj,3); 
//			Log.logScriptInfo("* @return - Returns web UI " + sObjType + " object for " + Strings.sDQ + sObjText + Strings.sDQ + " " + sObjType + " with locator of " + sObj,3);
//			Log.logScriptInfo("*/",3);
//			Log.logScriptInfo("public static " + sObjType + " " + sObjPrefix + Strings.removeWhiteSpace(sObjText) + "() {",3); 
//			Log.logScriptInfo("	return new " + sObjType + "(" + Strings.sDQ + "id=" + sObj + Strings.sDQ + ");",3);
//			Log.logScriptInfo("}",3);
//			Log.logScriptInfo("",3);
//		}
//			
//	}
//	}
//	
	
	
	@After
	/**
	 * @Function: tearDown() 
	 * @Description: This function ends the test and terminates the automation
	 */
	public void tearDown() throws Exception {
		
		//Gather metrics, report results and terminate automation 
		Log.terminate();
	}

}







