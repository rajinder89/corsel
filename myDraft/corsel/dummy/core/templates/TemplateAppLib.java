package dummy.core.templates;

import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.Button;
import dummy.core.webwidgets.Link;
import dummy.core.webwidgets.TextField;

/**
 * Standard Selenium Application Object Library Template file.
 * This class is the template for creating standard Application Object Library file for Selenium automation.
 * An Application Object Library file defines and declares UI objects and methods for specific web-based applications. 
 * 
 * /**
 * @ApplicationLibrary: TemplateAppLib - CBSetup library for contains common reusable methods.
 * @Description: This class contains application specific methods of Google Home Page
 * @Author: Automation Tester
 */
 
public class TemplateAppLib {
	
	
	/**
	 * Application Library files can contain String Constants to store information specific to a particular test web page.
	 * See example string declared below.
	 * add your application specific property variables and values here
	 */ 
	public static String sGoogleWebPage = "http://www.google.com/";
			
	
	/**
	 * Place test object description here
	 * Declares and returns a UI object for a specific 
	 * Application Under Test (AUT)
	 * This is an example text field object declaration for the 
	 * Search text field on the Google's home page.
	 * You define the name of the object, using the common automation naming standards. 
	 * Then supply the Selenium Locator for the object declaration (i.e. "//input[@name='q']")
	 * You can find the object Locator information using the Selenium IDE tool, or other open source tools such as Firebugs, xPathChecker, etc.   
	 * Make sure to create the object using the appropriate class type (i.e TextField) for the object you are defining.
	
	 	
	/**
	 * @Object: tfSearch()
	 * @Description: This is an text field for Search on Google's home page
	 * @return - TextField Object
	 */
	public static TextField tfSearch() { 
		return new TextField("//input[@name='q']");
	}
	
	/**
	 * @Object: btnGoogleSearch()
	 * @Description: This is a button for Google Search on Google's home page
	 * @return - Button Object
	 */
	public static Button btnGoogleSearch() { 
		return new Button("//input[@name='btnK']");
	}
	
	
	/**
	 * @Object: lnkAbout()
	 * @Description: This is a link for About on Google's home page
	 * @return - Link Object
	 */
	public static Link lnkAbout() { 
		return new Link("link=About");
	}
		
	
	
	/**
	 * Place application specific method description here
	 * This is an example application specific method that enters a search string in Google search text field
	 * and clicks Search button
	 * @param sSearchText - search text to enter into Google search text field
	 */
	public static void searchGoogle(String sSearchText){
		tfSearch().setText(sSearchText);			//enter search text into google search textfield
		Platform.sleep(Log.giAutomationShortTO); 	//wait 5 seconds for search to display results
		btnGoogleSearch().click(); 					//click search button
	}
	
}