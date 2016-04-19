package mmt.applibs;


import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.Browser;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.Button;
import dummy.core.webwidgets.Link;
import dummy.core.webwidgets.ListBox;
import dummy.core.webwidgets.TextField;


/**
 * MMT Home page UI library
 * This class contains definitions for all MMT Main page UI objects  * 
 * @author 
 */
public class MMTMainPage {
	
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
		return new Button("//input[@name='btnK'] | //button[@name='btnG']" );
	}
	
	/**
	 * MMT main page Flights link 
	 * @return Returns web UI Flights link object
	 */
	public static Link lnkFlights() { 
		return new Link("//span[contains(.,'Flights')]");
	}
	
	/**
	 * MMT main page Hotels link 
	 * @return Returns web UI Hotels link object
	 */
	public static Link lnkHotels() { 
		return new Link("//a[contains(.,'Hotels')]");
	}
	
	/**
	 * MMT main page Holidays link 
	 * @return Returns web UI Holidays link object
	 */
	public static Link lnkHolidays() { 
		return new Link("//span[contains(.,'Holidays')]");
	}
	/**
	 * MMT main page Flight+Hotel link 
	 * @return Returns web UI Flight+Hotel link object
	 */
	public static Link lnkFlight_Hotels() { 
		return new Link("//span[contains(.,'Flight+Hotel')]");
	}
	/**
	 * MMT main page Bus link 
	 * @return Returns web UI Bus link object
	 */
	public static Link lnkBus() { 
		return new Link("//span[contains(.,'Bus')]");
	}
	/**
	 * MMT main page Trains link 
	 * @return Returns web UI Trains link object
	 */
	public static Link lnkTrains() { 
		return new Link("//span[contains(.,'Trains')]");
	}
	/**
	 * MMT main page Deals link 
	 * @return Returns web UI Deals link object
	 */
	public static Link lnkDeals() { 
		return new Link("//span[contains(.,'Deals')]");
	}
	/**
	 * MMT main page Stories link 
	 * @return Returns web UI Stories link object
	 */
	public static Link lnkStories() { 
		return new Link("//span[contains(.,'Stories')]");
	}
	
	
	//************************ Button Objects ************************************//
	/**
	 * MMT main page Domestic button 
	 * @return Returns web UI Domestic button object
	 */
	public static Button btnDomestic() { 
		return new Button("//a[contains(.,' Domestic ')]"); 
	}
	
	/**
	 * MMT main page International button 
	 * @return Returns web UI Domestic button object
	 */
	public static Button btnInternational() { 
		return new Button("//a[contains(.,' International ')]"); 
	}
	
	//**************************** Radio Button Objects *********************************************//
	
	
	/**
	 * MMT main page One Way Radio button 
	 * @return Returns web UI  One Way Radio button object
	 */
	public static Button rbOneWay() { 
		return new Button("//input[@type='radio' and @value='one_way']"); 
	}
	
	/**
	 * MMT main page Round Trip Radio button 
	 * @return Returns web UI  Round Trip Radio button object
	 */
	public static Button rbRoundTrip() { 
		return new Button("//input[@type='radio' and @value='round_trip']"); 
	}
	
	/**
	 * MMT main page Multi City Radio button 
	 * @return Returns web UI Multi City Radio button object
	 */
	public static Button rbMulticity() { 
		return new Button("//input[@type='radio' and @value='multi_city']"); 
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
	 * Verifies a link exists for a specified search result
	 * @param sResultLink - The text caption of an expected search result link  
	 */
	public static void verifyResultLink(String sResultLink){
		//verify Search Results
		if (Browser.linkExists(sResultLink,Log.giAutomationShortTO))
			{

				ATUReports.add("Verified Link: " + "\"" +  sResultLink   + "\"" +  " is present", LogAs.PASSED, null);
				
			}
		else
			{
				ATUReports.add("Link: " + "\"" + sResultLink + "\"" + " does NOT exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
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
