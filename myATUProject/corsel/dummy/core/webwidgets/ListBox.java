package dummy.core.webwidgets;

import java.util.ArrayList;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import dummy.core.libs.SeleniumCore;

/**
 * The ListBox class is a wrapper class to allow centralized
 * control of common web widget list objects and methods.
 */
public class ListBox extends WebWidget {

	/**
	 * Default constructor for ListBox object 
	 * @param sID the object identifier locator
	 */
	public ListBox(String sID) {
		super(sID, "ListBox");

	}
	
	/**
	 * ListBox constructor to override object name with sLabel variable
	 * @param sID locator ID object identifier
	 * @param sLabel the label to use in replace of the default label
	 */
	public ListBox(String sID, String sLabel) {
		super(sID, "ListBox", sLabel);

	}


	/**
	 * Selects an item in a listbox object
	 * @param sItem item to select in the listbox
	 */
	public void selectItem(String sItem) {
		if (SeleniumCore.getBrowser().exists(sLocator)) {
				
			ATUReports.add("Select item " + "\"" + sItem + "\"" + " in " + sIdentifier + " " + sClassName, LogAs.PASSED, null);
			
			
			SeleniumCore.getBrowser().selectByText(sLocator, sItem);
		} else {
			
			ATUReports.add("Error occurred selecting " + sItem + " in "	+ sClassName + ": " + sIdentifier + " with locator of "
					+ "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	/**
	 * Gets option value (value attribute) for selected option in the specified select element.
	 * @param myLocator locator object identifier
	 * @return the selected option value in the specified select drop-down
	 */
	public String getSelectedValue(String myLocator) {

		return SeleniumCore.getBrowser().getSelectedValue(myLocator);
	}
	
	
	/**
	 * Gets text of the selected item in a listbox object
	 */
	public String getSelectedItemText() {
		if (SeleniumCore.getBrowser().exists(sLocator)) {
			return SeleniumCore.getBrowser().getSelectedItemText(sLocator);
		} else {
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			return "";
		}
	}

	
	/**
	 * Gets value from text field type widget
	 * @return returns Attribute value of calling object
	 */
	public String getValue() {
		if (SeleniumCore.getBrowser().exists(sLocator)) {
			return SeleniumCore.getBrowser().getValue(sLocator);
		}
		else
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			return "";
		}
	}

	/**
	 * Checks if extjs combo box has any values or is null
	 * @return true when combo box has any values or false when combo box has no values  
	 */
	public boolean isPopulated() {
		// if combo box has n items return true; otherwise, return false
		if (countXpaths() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Counts how many nodes match the specified xpath
	 * @return the number of nodes that match the specified xpath, used to count number of rows in combobox
	 */
	public int countXpaths() {
		return SeleniumCore.getBrowser().getXpathCount(sLocator).intValue();
	}

	/**
	 * Gets the list values from the calling listbox or dropdown
	 * @return the list of values
	 */
	public ArrayList<String> getListValues() {
		ArrayList<String> optionList = new ArrayList<String>();
		int i = 1;
		try {
			sLocator=sLocator+"//option";
			while (SeleniumCore.getBrowser().isElementPresent(sLocator + "[" + i + "]")) {
				String option = SeleniumCore.getBrowser().getText(sLocator + "[" + i + "]");			
				optionList.add(option);
				i++;
			}
		} catch (Exception e) {
		}
		
		return optionList;
	}
	
	
	/**
	 * Gets the item count from the calling listbox or dropdown
	 * @return the count of items in the calling listbox or dropdown
	 */
	public int getListItemCount() {
		ArrayList<String> optionList = new ArrayList<String>();
		int i = 1;
		try {
			while (SeleniumCore.getBrowser().isElementPresent(
					sLocator + "[" + i + "]")) {
				String option = SeleniumCore.getBrowser().getText(
						sLocator + "[" + i + "]");
				ATUReports.add(option, LogAs.PASSED, null);
				optionList.add(option);
				i++;
			}
		} catch (Exception e) {
		}
		return optionList.size();
	}
	
	
	/**
	 * @Method: getListItemCount(){
	 * @Description: Gets the item count from the listbox/dropdown
	 * @Return int - returns the count of items in the listbox/dropdown
	 */
	public ArrayList<String> getListItem() {
		ArrayList<String> optionList = new ArrayList<String>();
		int i = 1;
		try {
			while (SeleniumCore.getBrowser().isElementPresent(
					sLocator + "[" + i + "]")) {
				String option = SeleniumCore.getBrowser().getText(
						sLocator + "[" + i + "]");
				ATUReports.add(option, LogAs.PASSED, null);
				optionList.add(option);
				i++;
			}
		} catch (Exception e) {
		}
		return optionList;
	}
	
	
	
	/**
	 * Selection performed by label value
	 * @param sLabel the label used to locate the select option
	 */
	public void selectByLabel(String sLabel) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
		
			ATUReports.add("Select item " + "\"" + sLabel + "\"" + " in " + this.sIdentifier
					+ " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().selectByLabel(this.sLocator, sLabel);

			} catch (Exception e) {
				
				ATUReports.add("Error occurred selecting " + sLabel + " in " + this.sClassName
						+ ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator
						+ "\" " + e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} else {
		
			ATUReports.add("Error occurred selecting " + sLabel + " in " + this.sClassName + ": "
					+ this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	/**
	 * Selection performed by partial label value
	 * @param sLabel the label used to locate the select option
	 */
	public void selectByPartialLabel(String sLabel) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
				ATUReports.add("Select item " + "\"" + sLabel + "\"" + " in " + this.sIdentifier
					+ " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().selectByPartialLabel(this.sLocator, sLabel);

			} catch (Exception e) {
				
				ATUReports.add("Error occurred selecting " + sLabel + " in " + this.sClassName
						+ ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator
						+ "\" " + e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} else {
		
				ATUReports.add("Error occurred selecting " + sLabel + " in " + this.sClassName + ": "
					+ this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	
	/**
	 * Selection performed by Index value
	 * @param sIndex the Index value used to locate the select option
	 */
	public void selectByIndex(String sIndex) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
			ATUReports.add("Select item " + "\"" + sIndex + "\"" + " in " + this.sIdentifier
					+ " " + this.sClassName, LogAs.PASSED, null);
		try {
				int iIndex = Integer.parseInt(sIndex);
				SeleniumCore.getBrowser().selectByIndex(this.sLocator, iIndex);

			} catch (Exception e) {
				
			ATUReports.add("Error occurred selecting " + sIndex + " in " + this.sClassName
						+ ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator
						+ "\" " + e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} else {
			
			ATUReports.add("Error occurred selecting " + sIndex + " in " + this.sClassName + ": "
					+ this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	
	

	/**
	 * DeSelection performed by label value
	 * @param sLabel the label used to locate the select option
	 */
	public void deSelectByLabel(String sLabel) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
			ATUReports.add("Deselect item " + "\"" + sLabel + "\"" + " in " + this.sIdentifier + " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().deSelectByLabel(this.sLocator, sLabel);
			} catch (Exception e) {
				
				ATUReports.add("Error occurred deselecting " + sLabel + " in " + this.sClassName 	+ ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				ATUReports.add(e.getMessage(), LogAs.FAILED, null);
			}
		} else {
					
			ATUReports.add("Error occurred deselecting " + sLabel + " in " + this.sClassName + ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}

	

	/**
	* DeSelection performed by partial label value 
	* @param sLabel the label used to locate the select option
	*/
	public void deSelectByPartialLabel(String sLabel) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
			ATUReports.add("Deselect item " + "\"" + sLabel + "\"" + " in " + this.sIdentifier
					+ " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().deSelectByPartialLabel(this.sLocator, sLabel);
				} catch (Exception e) {
				
				ATUReports.add("Error occurred deselecting " + sLabel + " in " + this.sClassName
						+ ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator
						+ "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				
				ATUReports.add(e.getMessage(), LogAs.FAILED, null);
			}
		} else {
						
			ATUReports.add("Error occurred deselecting " + sLabel + " in " + this.sClassName + ": "
					+ this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
			

	/**
	* DeSelection performed by Index value of the desired list option 
	* @param iIndex Index value of the desired list option to select
	*/
	public void deSelectByIndex(int iIndex) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
			ATUReports.add("Deselect item " + "\"" + iIndex + "\"" + " in " + this.sIdentifier
					+ " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().deSelectByIndex(this.sLocator, iIndex);
				} catch (Exception e) {
				
				ATUReports.add("Error occurred deselecting " + iIndex + " in " + this.sClassName + ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator
								+ "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				ATUReports.add(e.getMessage(), LogAs.FAILED, null);
			}
		} else {
		
			ATUReports.add("Error occurred deselecting " + iIndex + " in " + this.sClassName + ": "
					+ this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	/**
	* DeSelection performed by value of the desired list option 
	* @param sValue value of the desired list option to select
	*/
	public void deSelectByValue(String sValue) {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
			
			ATUReports.add("Deselect item " + "\"" + sValue + "\"" + " in " + this.sIdentifier
					+ " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().deSelectByValue(this.sLocator, sValue);
				} catch (Exception e) {
				
				ATUReports.add("Error occurred deselecting " + sValue + " in " + this.sClassName
						+ ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator
						+ "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				ATUReports.add(e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		} else {
	
			ATUReports.add("Error occurred deselecting " + sValue + " in " + this.sClassName + ": "
					+ this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
	}
	
	
	/**
	* DeSelection of all items in list 
	*/
	public void deSelectAll() {
		if (SeleniumCore.getBrowser().exists(this.sLocator)) {
			
			ATUReports.add("Deselect all items in the list: " + this.sIdentifier + " " + this.sClassName, LogAs.PASSED, null);
			try {
				SeleniumCore.getBrowser().deSelectAll(this.sLocator);
				} catch (Exception e) {
							
				ATUReports.add("Error occurred deselecting all items in the " + this.sClassName + ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				ATUReports.add(e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				}
		} else {
						
			ATUReports.add("Error occurred deselecting all items in the " + this.sClassName + ": " + this.sIdentifier + " with locator of " + "\"" + this.sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
	}
	
	
	/**
	* Highlights the list box object 
	* 
	*/		
	public void highlight() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().highlightElement(sLocator);
		}
		else
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
    }
	
	
	/**
	* Scrolls the Listbox element into view
	* 
	*/		
	public void scrollElementIntoView() {
		if (SeleniumCore.getBrowser().exists(sLocator)){
			SeleniumCore.getBrowser().scrollElementIntoView(sLocator);
		}
		else
		{
			ATUReports.add("Could not find " + sClassName + ": " + sIdentifier + " with locator of " +  "\"" + sLocator + "\"", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
    }
		

}