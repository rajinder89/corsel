package dummy.core.tools;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.libs.Randomize;
import dummy.core.libs.Strings;

/**
 * @TestScript: CaptureObjects
 * @Description: This tool extracts all UI objects from specified web page source file and writes perfectly formatted Selenium applibs object declarations to the console.
 * It generates UI object getter methods, complete with Javadoc that you can simply copy and paste in to your applibs object library files. 
 * @author: Tony Venditti
 */
public class CaptureObjects_3 {
	
	
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
				
	}
	
	
	public List<String> extractObjectsOfType(String[] lsPgSrcContent, String sObjTypeOpen, String sObjTypeClose, String sObjClassType, String sObjPrefix){
		String[] lsInvalidChars = {"_","-","@","#","$","%","^","&","*","(",")","+","=","!","[","]","?",",","'","<",">",".",Strings.sDQ,":",";","/"};
		String sObjText = "";
		List<String> lsObjsList = new ArrayList<String>();
		String sObj = "";
		String sPropUsedForNaming = "";
		
		
		String sName 	= "name=";
		String sId 		= "id=";
		String sLink 	= "link=";
		String sType 	= "type=";
		String sValue   = "value="; 
		String sAlt	    = "alt=";
		String sCss 	= "css=";
		String sXpath	= "xpath=";	
		String sTitle = "title=";
		String sLabel = "label=";
		String sClass = "class=";
	
		
		for (int x=0; x < lsPgSrcContent.length; x++)
		{
		try{
		while ((lsPgSrcContent[x].indexOf(sObjTypeOpen)!= -1) && (lsPgSrcContent[x].indexOf(sObjTypeClose)!= -1)){
			int iPosBegin = lsPgSrcContent[x].indexOf(sObjTypeOpen);
			int iPosEnd = lsPgSrcContent[x].indexOf(sObjTypeClose);
			sObjText = lsPgSrcContent[x].substring(iPosBegin, iPosEnd+sObjTypeClose.length());
			lsObjsList.add(sObjText);
			lsPgSrcContent[x] = lsPgSrcContent[x].substring(iPosEnd+sObjTypeClose.length());
		}
		}
		catch(Exception e){
			//skip
		}
		}
		
		//print objects out
		for (int x=0; x < lsObjsList.size(); x++){
			//System.out.println(lsObjsList.get(x));
			
			
			//extract name, id, title, etc properties from page source line item
			String sObjName = extractObjectProperties(lsObjsList.get(x), sName);
			String sObjID = extractObjectProperties(lsObjsList.get(x), sId);
			String sObjLink = extractObjectProperties(lsObjsList.get(x), sLink);
			String sObjTitle = extractObjectProperties(lsObjsList.get(x), sTitle);
			String sObjLabel = extractObjectProperties(lsObjsList.get(x), sLabel);
			String sObjType = extractObjectProperties(lsObjsList.get(x), sType);
			String sObjAlt = extractObjectProperties(lsObjsList.get(x), sAlt);
			String sObjValue = extractObjectProperties(lsObjsList.get(x), sValue);
			String sObjClass = extractObjectProperties(lsObjsList.get(x), sClass);
			String sObjCSS = extractObjectProperties(lsObjsList.get(x), sCss);
			String sObjXPath = extractObjectProperties(lsObjsList.get(x), sXpath);
			String sObjCaption = extractObjectProperties(lsObjsList.get(x), Strings.sDQ+">", sObjTypeClose);
			
			//Calculate which object property is best suited for naming the object 
			if (sObjTitle != ""){
				sObjText = sObjTitle;
				sPropUsedForNaming = sTitle + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjLabel != ""){
				sObjText = sObjLabel;
				sPropUsedForNaming = sLabel + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjAlt != ""){
				sObjText = sObjAlt;
				sPropUsedForNaming = sAlt + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjCaption != ""){
				sObjText = sObjCaption;
				sPropUsedForNaming = "Caption or text at end of line item: " + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjName != ""){
				sObjText = sObjName;
				sPropUsedForNaming = sName + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjLink != ""){
				sObjText = sObjLink;
				sPropUsedForNaming = sLink + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjValue != ""){
				sObjText = sObjValue;
				sPropUsedForNaming = sValue + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjID != ""){
				sObjText = sObjID;
				sPropUsedForNaming = sId + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjClass != ""){
				sObjText = sObjClass;
				sPropUsedForNaming = sClass + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjType != ""){
				sObjText = sObjType;
				sPropUsedForNaming = sType + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjCSS != ""){
				sObjText = sObjCSS;
				sPropUsedForNaming = sCss + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else if (sObjXPath != ""){
				sObjText = sObjXPath;
				sPropUsedForNaming = sXpath + Strings.sDQ + sObjText + Strings.sDQ;
			}
			else{
				sObjText = "NoNameFound_" + Randomize.genDateBasedRandVal();
				sPropUsedForNaming = "No Property found for naming: " + Strings.sDQ + sObjText + Strings.sDQ;;
			}	
			
			
			//Calculate which object property is best suited for identifying the object 
			if (sObjID != ""){
				sObj = Strings.sDQ + sId + sObjID + Strings.sDQ;
			}
			else if ((sObjClassType == "Link") && (sObjCaption != "")){
				sObj = Strings.sDQ + sLink + sObjCaption + Strings.sDQ;
			}
			else if (sObjLink != ""){
				sObj = Strings.sDQ + sLink + sObjLink + Strings.sDQ;
			}
			else if (sObjName != ""){
				sObj = Strings.sDQ + sName + sObjName + Strings.sDQ;
			}
			else if (sObjXPath != ""){
				sObj = Strings.sDQ + sXpath + sObjXPath + Strings.sDQ;
			}
			else if (sObjCSS != ""){
				sObj = Strings.sDQ + sCss + sObjCSS + Strings.sDQ;
			}
			else if (sObjTitle != ""){
				sObj = Strings.sDQ + sLink + sObjTitle + Strings.sDQ;
			}
			else if (sObjClass != ""){
				sObj = Strings.sDQ + sClass + sObjClass + Strings.sDQ;
			}
			else
				sObj = "No_Locator_Found_" + Randomize.genDateBasedRandVal();
			
			
			
			
			//remove invalid characters
			//System.out.println(sObjText);
			sObjText = Strings.removeInvalidChars(sObjText,lsInvalidChars);
			sObjText = Strings.removeWhiteSpace(sObjText);
			if (sObjText.length() > 50)
				sObjText = sObjText.substring(0,50);
			//System.out.println(sObjText);
			
			
			//print out properly formatted Test Object getter to the eclipse console 
			Log.logScriptInfo("",3);
			Log.logScriptInfo("/**",3);
			Log.logScriptInfo("* @Object: " +sObjPrefix + sObjText,3);
			Log.logScriptInfo("* @Description: " + sObjClassType + " object for " + Strings.sDQ + sObjText + Strings.sDQ + " " + sObjClassType + " with locator of " + sObj,3); 
			Log.logScriptInfo("* @return - Returns web UI " + sObjClassType + " object for the " + Strings.sDQ + sObjText + Strings.sDQ + " " + sObjClassType + " with locator of " + sObj,3);
			Log.logScriptInfo("*/",3);
			Log.logScriptInfo("public static " + sObjClassType + " " + sObjPrefix + sObjText + "() {",3);
			Log.logScriptInfo("	// Actual Page Source code:   " + lsObjsList.get(x),3);
			Log.logScriptInfo("	// Property used for naming the object:   " + sPropUsedForNaming,3);
			Log.logScriptInfo("	return new " + sObjClassType + "(" + sObj + ");",3);
			Log.logScriptInfo("}",3);
			Log.logScriptInfo("",3);	
			
		
		}	
		return lsObjsList;	//return List of objects
	}
	
	
	public String extractObjectProperties(String sLine, String sProp){
	int iend = 0;
	String sTmp = "";
	
	//extract name, id, title, etc properties
	try{
	if (sLine.indexOf(sProp) != -1){
		int ipos = sLine.indexOf(sProp);
		sTmp = sLine.substring(ipos+1);
		iend = sTmp.indexOf(Strings.sDQ+" ");
		String sObjName = sTmp.substring(0+sProp.length(),iend); 
		return sObjName;
	}
	}
	catch(Exception e){
		try{
		//if a locator is not followed by a space (which triggers a runtime error) it is sometimes followed by this sequence "><"
		iend = sTmp.indexOf("><");
		if (iend == -1)
			iend = sTmp.indexOf(Strings.sDQ+">");
		String sObjName = sTmp.substring(0+sProp.length(),iend-1); 
		return sObjName;
		}catch(Exception e2){
			return "";
		}
	}
	return "";
	}
	
	
	public String extractObjectProperties(String sLine, String sPropBegin, String sPropEnd){
		int iend = 0;
		String sTmp = "";
		
		//extract name, id, title, etc properties
		try{
		if (sLine.indexOf(sPropBegin) != -1){
			int ipos = sLine.lastIndexOf(sPropBegin);
			sTmp = sLine.substring(ipos);
			iend = sTmp.indexOf(sPropEnd);
			String sObjName = sTmp.substring(0+sPropBegin.length(),iend);
			int iNameLength = sObjName.length();
			if (iNameLength <= 0)
				return "";
			else
				return sObjName;
		}
		}
		catch(Exception e){
			return "";
		}
		return "";
		}
	
	

	@Test
	public void testCreateUIObjects(){	
				
		try{
		
		//Testcase header
		Log.startTestCase("Get all objects from Page Source");
		
		//object types supported by Selenium
		String sPageSourceFile = Platform.getCurrentProjectPath() + "core\\tools\\pagesource.txt";
		System.out.println(sPageSourceFile);
		getAllObjects(sPageSourceFile);
		
		
		}
		catch(Exception e){
			Log.errorHandler("Error occurred during Object Capturing.",e);
		}
		
		
	}
	
	
	 
		
	
	/**
	 * Gets all objects of the specified class type
	 * @param sPageSourceFile - file containing web page source in text format
	 */
	public void getAllObjects(String sPageSourceFile){
	Log.logBanner("getting all objects from page source file: " + sPageSourceFile);

	
	
	extractObjectsOfType(FileIO.getFileContentsAsList(sPageSourceFile), "<button", "</button>", "Button", "btn");
	extractObjectsOfType(FileIO.getFileContentsAsList(sPageSourceFile), "<a", "</a>", "Link", "lnk");
	extractObjectsOfType(FileIO.getFileContentsAsList(sPageSourceFile), "<img", "</img>", "Image", "img");
	extractObjectsOfType(FileIO.getFileContentsAsList(sPageSourceFile), "<input", "</input>", "TextField", "tf");
	extractObjectsOfType(FileIO.getFileContentsAsList(sPageSourceFile), "<select", "</select>", "ListBox", "lst");
	extractObjectsOfType(FileIO.getFileContentsAsList(sPageSourceFile), "<label", "</label>", "Label", "st");
	
	
	
	
			
	
	}
	
	
	
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







