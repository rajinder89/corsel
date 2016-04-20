package core.libs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Excel
{

	/**
	 * @param sheetNumber
	 * @return int
	 * @throws IOException
	 * @throws BiffException
	 */
	public static int getXlsRowCount(String Filename,String sheetName)throws IOException, BiffException {

		String completepath = "";
		/*getting the complete path of the file */
		if (!Filename.contains(Platform.getFileSeparator()) && !Filename.contains("/")) {
			String pathAppend=Log.gsAutomationAutoSupportDocs;  //getProperty(TESTDATA_FILELOC_KEY);
			Log.logDebugInfo("PATH APPENDED-------------------------->"+pathAppend);
			/*complete path */
			completepath=pathAppend+Filename+Log.gsAutomationTestDataSpreadsheetSuffix;;
		} else
			completepath=Filename+Log.gsAutomationTestDataSpreadsheetSuffix;;

			//configuring the log4j property		
			int rows=0;

			Log.logDebugInfo("[STARTED]:read method called");

			File inputWorkbook = new File(completepath);
			Log.logDebugInfo("[INFO]:File Object Created"+inputWorkbook);
			Workbook w;
			Sheet sheet;
			w = Workbook.getWorkbook(inputWorkbook);
			Log.logDebugInfo("[INFO]: Workbook Object Created"+inputWorkbook);
			// Get the first sheet
			sheet = w.getSheet(sheetName);
			rows=sheet.getRows();
			return rows	;
	}


	/**
	 * @param sheetNumber
	 * @return int
	 * @throws IOException
	 * @throws BiffException
	 */
	public static int getXlsColumnCount(String Filename,String sheetName) throws IOException, BiffException {			
		String completepath = "";
		/*getting the complete path of the file */
		if (!Filename.contains(Platform.getFileSeparator()) && !Filename.contains("/")) {
			String pathAppend=Log.gsAutomationAutoSupportDocs;	//getProperty(TESTDATA_FILELOC_KEY);
			Log.logDebugInfo("PATH APPENDED-------------------------->"+pathAppend);
			/*complete path */
			completepath=pathAppend+Filename+Log.gsAutomationTestDataSpreadsheetSuffix;;
		} else
			completepath=Filename+Log.gsAutomationTestDataSpreadsheetSuffix;;

			int cols=0;

			Log.logDebugInfo("[STARTED]:read method called");

			File inputWorkbook = new File(completepath);
			Log.logDebugInfo("[INFO]:File Object Created"+inputWorkbook);
			Workbook w;
			Sheet sheet;
			w = Workbook.getWorkbook(inputWorkbook);
			Log.logDebugInfo("[INFO]: Workbook Object Created"+inputWorkbook);
			// Get the first sheet
			sheet = w.getSheet(sheetName);
			cols=sheet.getColumns();
			return cols	;
	}


	/**
	 * @Description This method is used to retrieve the row data,
	 * by using findCell which gets a cell for which content matching the string passed in.
	 * @param filename
	 * @param sheetName
	 * @param rowValue 
	 * @return Map<String,String> this will read the data and it even reads empty cell
	 * @throws BiffException
	 * @throws IOException
	 */
	public static Map<String,String> getXlsRowDataAsMap(String filename, String sheetName, String rowValue) throws BiffException, IOException {
		String completepath = "";

		if (!filename.contains(Platform.getFileSeparator()) && !filename.contains("/"))
			/*getting the complete path of the file*/
		{
			String pathAppend=Log.gsAutomationAutoSupportDocs;   //getProperty(TESTDATA_FILELOC_KEY);
			Log.logDebugInfo("PATH APPENDED-------------------------->"+pathAppend);
			//complete path
			completepath=pathAppend+filename+Log.gsAutomationTestDataSpreadsheetSuffix;;
		} else
			completepath=filename+Log.gsAutomationTestDataSpreadsheetSuffix;;

			//configuring the log4j property
			Log.logDebugInfo("[STARTED]:getXlsRowData Called");
			Sheet sheetObject = null;
			Workbook workBookObj = null;
			Cell cellObj = null;
			int columnNumber = 0;
			int rowNumber = 0;

			//RowNumber of Column Headings
			int rowHeader=rowNumber;

			Map<String,String> hmRowData = new HashMap<String,String>();
			workBookObj = Workbook.getWorkbook(new File(completepath));
			Log.logDebugInfo("[INFO]WorkBook Object Created:"+workBookObj);
			sheetObject = workBookObj.getSheet(sheetName);
			Log.logDebugInfo("[INFO] Sheet Object Created:"+sheetObject);
			cellObj = sheetObject.findCell(rowValue);
			Log.logDebugInfo("[INFO]Cell Object Created:"+cellObj);
			columnNumber = cellObj.getColumn();
			rowNumber = cellObj.getRow(); 
			//getting no of columns in the sheet
			int totalColumns = sheetObject.getColumns();

			for (int i = columnNumber + 1; i < totalColumns; i++)
			{
				if(sheetObject.getCell(i, rowNumber).getContents()==null || sheetObject.getCell(i, rowNumber).getContents()=="")
				{
					hmRowData.put(sheetObject.getCell(i, rowHeader).getContents().trim(),sheetObject.getCell(i, rowNumber).getContents());
				}
				if(sheetObject.getCell(i, rowNumber).getContents()!=null && sheetObject.getCell(i,rowNumber).getContents()!="")
				{
					hmRowData.put(sheetObject.getCell(i, rowHeader).getContents().trim(),sheetObject.getCell(i, rowNumber).getContents());
				}

			}
			Log.logDebugInfo("[INFO]sucessfulCompletion:getXlsRowData");
			return hmRowData;
	}

	/**
	 * @Description This method is used to retrieve the row-wise data, 
	 * by using getCell() which returns the cell for the specified location.
	 * @param filename
	 * @param sheetName
	 * @param rowValue 
	 * @return Map<String,String> this will read the data and it even reads empty cell
	 * @throws BiffException
	 * throws IOException 
	 */
	public static Map<String,String> getXlsRowContentAsMap(String filename, String sheetName,int rowValue) throws BiffException, IOException {
		String completepath = "";

		if (!filename.contains(Platform.getFileSeparator()) && !filename.contains("/")) {
			/*getting the complete path of the file*/
			String pathAppend=Log.gsAutomationAutoSupportDocs;  //getProperty(TESTDATA_FILELOC_KEY);
			Log.logDebugInfo("PATH APPENDED-------------------------->"+pathAppend);
			/*complete path */
			completepath=pathAppend+filename+Log.gsAutomationTestDataSpreadsheetSuffix;;
		} else
			completepath=filename+Log.gsAutomationTestDataSpreadsheetSuffix;;

			Log.logDebugInfo("[STARTED]:getXlsRowData Called");
			Sheet sheetObject = null;
			Workbook workBookObj = null;
			Cell cellObj = null;
			//Cell[] celldata;
			int columnNumber = 0;
			int rowNumber = 0;

			//RowNumber of Column Headings
			int rowHeader=rowNumber;

			Map<String,String> hmRowData = new HashMap<String,String>();
			workBookObj = Workbook.getWorkbook(new File(completepath));
			Log.logDebugInfo("[INFO]WorkBook Object Created:"+workBookObj);
			sheetObject = workBookObj.getSheet(sheetName);
			Log.logDebugInfo("[INFO] Sheet Object Created:"+sheetObject);
			cellObj = sheetObject.getCell(0,rowValue);
			Log.logDebugInfo("[INFO]Cell Object Created:"+cellObj);
			columnNumber = cellObj.getColumn();
			rowNumber = cellObj.getRow(); 
			//getting no of columns in the sheet
			int totalColumns = sheetObject.getColumns();

			for (int i = columnNumber; i < totalColumns; i++)
			{

				if(sheetObject.getCell(i, rowNumber).getContents()==null || sheetObject.getCell(i, rowNumber).getContents()=="")
				{
					hmRowData.put(sheetObject.getCell(i, rowHeader).getContents().trim(),sheetObject.getCell(i, rowNumber).getContents());
				}
				if(sheetObject.getCell(i, rowNumber).getContents()!=null && sheetObject.getCell(i,rowNumber).getContents()!="")
				{
					hmRowData.put(sheetObject.getCell(i, rowHeader).getContents().trim(),sheetObject.getCell(i, rowNumber).getContents());
				}

			}
			Log.logDebugInfo("[INFO]sucessfulCompletion:getXlsRowData");
			return hmRowData;

	}


	/**
	 * @Method: isSheetExists()
	 * @Description: Checks whether the specified sheet exists in the Excel file.
	 * @Param: sExcelFileName - Excel file name passed without the extension, i.e. Transfers. The extension is 
	 * added within method by calling getCompletePathToExcelFile();
	 * @Param: sSheetName - sheet name
	 * @Return: true if the specified sheet exists in Excel file, false otherwise.
	 */
	public static boolean isSheetExists(String sExcelFileName, String sSheetName) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(getCompletePathToExcelFile(sExcelFileName)));
			String[] sheetNames = workbook.getSheetNames();
			return Arrays.asList(sheetNames).contains(sSheetName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Method: getCompletePathToFile()
	 * @Description: Returns complete path to excel file.
	 * @Param: sExcelFileName - file name. For example: Transfers.
	 * @Return: Complete path String object
	 */
	private static String getCompletePathToExcelFile(String sExcelFileName) {		

		if (!sExcelFileName.contains(Platform.getFileSeparator()) && !sExcelFileName.contains("/")) {
			// Stores file directory
			String sPathAppend=Log.gsAutomationAutoSupportDocs;  //getProperty(TESTDATA_FILELOC_KEY);
			/*complete path */
			String completePath=sPathAppend+sExcelFileName+Log.gsAutomationTestDataSpreadsheetSuffix;

			Log.logDebugInfo("PATH APPENDED-------------------------->" + sPathAppend);
			
			return completePath; // Returns complete path to file
		} 
		return sExcelFileName+Log.gsAutomationTestDataSpreadsheetSuffix;
	}
}