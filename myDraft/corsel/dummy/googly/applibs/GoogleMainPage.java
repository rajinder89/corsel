package dummy.googly.applibs;


import dummy.core.libs.Browser;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.Button;
import dummy.core.webwidgets.Link;
import dummy.core.webwidgets.ListBox;
import dummy.core.webwidgets.TextField;


/**
 * Google Main page UI library
 * <p>
 * This class contains definitions for all Google Main page UI objects like Search button
 * and search text field, etc. as well as methods to drive Google UI i.e. googleSearch() method
 * 
 * @author Tony Venditti
 */
public class GoogleMainPage {
	
	/**
	 * This file stores all Google application specific global properties values
	 */
	public static String gsGooglePropertiesFile = Log.getPropertiesDir() + Platform.getFileSeparator() + "google.properties";
	
	
	/**
	 * This global variable is to store the main Google URL
	 */
	public static String gsGoogleURL = "http://www.google.com";
	
	/**
	 * This global variable is to store the main Google Username
	 */
	public static String gsGoogleUsername = "vendittit";
	
	/**
	 * This global variable is to store the main Google main users password
	 */
	public static String gsGooglePassword = "mypassword";
	
	/**
	 * This global variable is to store the main Google Language
	 */
	public static String gsGoogleLanguage= "English";
	
	/**
	 * This global variable is to store the main Google Location
	 */
	public static String gsGoogleLocation= "Waltham";
	
	
	
	
	
	
	
	/**
	 * Declaration for the Google main page Search button
	 * @return Returns web UI object of specified type and definition 
	 */
	public static Button btnGoogleSearch() { 
		return new Button("name=btnG");
		
	}
	
	/**
	 * Google main page Search text field 
	 * @return Returns web UI object of specified type and definition
	 */
	public static TextField tfSearchText() { 
		return new TextField("name=q");
		
	}
	
	
	/**
	 * Google main page Image link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkImages() { 
		return new Link("link=Images");
	}
	
	/**
	 * Google main page Web link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkWeb() { 
		return new Link("link=Web");
	}
	
	/**
	 * Google main page Videos link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkVideos() { 
		return new Link("link=Videos");
	}
	
	/**
	 * Google main page News link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkNews() { 
		return new Link("link=News");
	}
	
	
	/**
	 * Google main page Maps link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkMaps() { 
		return new Link("link=Maps");
	}
	
	/**
	 * Google main page Shopping link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkShopping() { 
		return new Link("link=Shopping");
	}
	
	/**
	 * Google main page Sign In link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkSignIn() { 
		return new Link("link=Sign In"); //"gbi4s1"
	}
	
	/**
	 * Google main page Settings link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkSettings() { 
		return new Link("id=ab_opt_icon | link=Settings"); //"gbi5"
	}
	
	/**
	 * Google main page Search Settings link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkSearchSettings() { 
		return new Link("link=Search settings"); //"gbi5"
	}
	
	/**
	 * Google main page Web History link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkWebHistory() { 
		return new Link("link=Web History"); //"gbi5"
	}
	
	
	/**
	 * Google main page About link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkAboutGoogle() { 
		return new Link("//a[contains(text(),'About')]");
		
	}
	
	/**
	 * Google main page Learn More link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkLearnMoreGoogle() { 
		return new Link("link=Learn More"); 
	}
	

	/**
	 * Google main page Advanced Search link 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Link lnkAdvancedSearch() { 
		return new Link("link=Advanced search"); 
	}
	
	/**
	 * Google main page Search textfield 
	 * @return Returns web UI object of specified type and definition
	 */
	public static TextField tfAllTheseWords() { 
		return new TextField("name=as_q");
	}
	
	/**
	 * Google main page Advanced Search button 
	 * @return Returns web UI object of specified type and definition
	 */
	public static Button btnAdvancedSearch() { 
		return new Button("//input[@value='Advanced Search']"); 
	}
	
	/**
	 * Google main page Language Listbox 
	 * @return Returns web UI object of specified type and definition
	 */
	public static ListBox lstLanguage() { 
		return new ListBox("//li[@id=':1']/div"); 
	}
	

	
	
	
	/**
	 * Enters a search string in google search text field and clicks Search button
	 * @param sSearchText - search text to enter into google search text field
	 * @author Tony Venditti
	 */
	public static void googleSearch(String sSearchText){
		tfSearchText().highlight();
		
		tfSearchText().setText(sSearchText);	//enter search text into google search textfield
		btnGoogleSearch().click(); //click search button
	}
	
	
	/**
	 * Clicks Settings-Search Settings link to load Google Preferences page
	 * @author Tony Venditti
	 */
	public static void loadPreferencesPage(){
		lnkSettings().click();	//click Google Settings link to expose Search Settings drop down item
		lnkSearchSettings().click(); //click Search Settings button
		
	}
	
	
	/**
	 * Verifies a link exists for a specified search result
	 * @param sResultLink - The text caption of an expected search result link  
	 */
	public static void verifyResultLink(String sResultLink){
		//verify Search Results
		if (Browser.linkExists(sResultLink,Log.giAutomationShortTO))
			{
				Log.logScriptInfo("Verified Link: " + "\"" +  sResultLink   + "\"" +  " is present");
			}
		else
			{
			Log.errorHandler("Link: " + "\"" + sResultLink + "\"" + " does NOT exist");
			//fail("Link: " + "\"" + search_data[x][1] + "\"" + " does NOT exist");		//this command registers a fatal failure in JUnit. Use only if you are using more than one @Test annotations in your script		
			}
		}
		
		
	
	
	/**
	 * reads in values from the google.properties file and assigns global variables for Google application automation  
	 */
	public static void loadGoogleProperties(){
		
		//read in the values for the following google application properties from the google.properties file
		gsGoogleURL=Log.getPropertyFromFile("gsGoogleURL", gsGooglePropertiesFile);
		gsGoogleUsername=Log.getPropertyFromFile("gsGoogleUsername", gsGooglePropertiesFile);
		gsGooglePassword=Log.getPropertyFromFile("gsGooglePassword", gsGooglePropertiesFile);
		gsGoogleLanguage=Log.getPropertyFromFile("gsGoogleLanguage", gsGooglePropertiesFile);
		gsGoogleLocation=Log.getPropertyFromFile("gsGoogleLocation", gsGooglePropertiesFile); 
		
	}
	
	
}
