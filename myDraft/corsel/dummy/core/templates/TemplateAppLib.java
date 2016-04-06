package dummy.core.templates;

import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.webwidgets.Button;
import dummy.core.webwidgets.Link;
import dummy.core.webwidgets.TextField;


/**
 * Standard Selenium Application Object Library Template file.
 * <P> 
 * This class is the template for creating standard Application Object Library file at ACI for Selenium automation.
 * An Application Object Library file defines and declares UI objects and methods for specific web-based applications. 
 * 
 */
public class TemplateAppLib {
	
	
	
	
	
	/**
	 * Application Library files can contain String Constants to store information specific to a particular test web page.
	 * See example string declared below.
	 * 
	 * // TODO: add your application specific property variables and values here
	*/ 
	public static String sACIWebPage = "http://www.aciworldwide.com/";
	
	
	
	
	
	/**
	 * // TODO: place test object description here
	 * Declares and returns a UI object for a specific 
	 * Application Under Test (AUT)
	 * <P>
	 * This is an example text field object declaration for the 
	 * Search textfield on the ACI Worldwide website homepage.
	 * You define the name of the object, using the ACI common automation naming standards. 
	 * Then supply the Selenium Locator for the 
	 * object declaration (i.e. "//input[@id='ctl14_txtCriteria']")
	 * You can find the object Locator information using the Selenium IDE tool 
	 * or other open source tools such as Firebugs, xPathChecker, etc.   
	 * Make sure to create the object using the 
	 * appropriate class type (i.e TextField) for the object you are defining.
	 * <P>
	 * // TODO: change return test object info
	 * @return web UI object of specified type and definition
	 * 
	 */
	public static TextField txtSearchACI() { 
		return new TextField("//input[@id='ctl14_txtCriteria']");
	}
	
	/**
	 * Declares and returns a UI object for a specific Application Under Test (AUT)
	 * This is an example Button object declaration for the Search button on the ACI Worldwide website homepage.
	 * <P>
	 * 
	 * // TODO: change return test object info
	 * @return web UI object of specified type and definition
	 * 
	 */
	public static Button btnSearchACI() { 
		return new Button("//input[@id='ctl14_btnSearch']");
	}
	
	
	/**
	 * This is an example link object declaration for "Contact Us" link on the ACI Worldwide website homepage.
	 * <P>
	 * 
	 * // TODO: change return test object info
	 * @return web UI object of specified type and definition
	 * 
	 */ 
	public static Link lnkContactUs() { 
		return new Link("link=Contact Us");
	}
	
	
	
	
	
	
	/**
	 * // TODO: place application specific method description here
	 * This is an example application specific method that enters a search string in ACI search text field
	 * and clicks Search button
	 * <P>
	 * @param sSearchText search text to enter into ACI search text field
	 */
	public static void searchACI(String sSearchText){
		txtSearchACI().setText(sSearchText);	//enter search text into google search textfield
		Platform.sleep(Log.giAutomationShortTO); //wait 5 seconds for search to display results
		btnSearchACI().click(); //click search button
		
	}
	
	
	
	
}
