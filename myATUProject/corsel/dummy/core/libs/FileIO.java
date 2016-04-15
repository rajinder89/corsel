package dummy.core.libs;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


/**
 * @Class: FileIO 
 * @Description: The FileIO class contains general File IO functions
 */
public class FileIO
{	
	/**Buffer size (32KB) for file manipulation methods.*/
	 public final static int FILE_BUFFER_SIZE = 32 * 1024;	
	 public final static String slash = "/";
	 public final static String backslash = "\\";
	 
	     
	/**
	 * Deletes specified file or directory (if directory is specified the method 
	 * will recursively delete all files and or subdirectories within the specified parent directory. 
	 * (i.e. deleteFile("c:\\my test folder\\") will delete the directory "my test folder" and all files
	 * and subdirectories within that directory. Be extremely careful using this function<p>
	 * @param filename 
	 * @Parameter: filename = path & filename of file or directory to delete
	 */
    
	public static void deleteFile(String filename)
	{
		try
		{
			// Create a File object to represent the filename
			File f = new File(filename);

			// Make sure the file or directory exists and isn't write protected
			if (!f.exists())
			{
				//errorHandler("Delete: no such file or directory: " + filename);
				return;
			}

			if (!f.canWrite())
			{
				//errorHandler("Delete: write protected: " + filename);
				return;
			}

			// If it is a directory, recursively delete all files in the directory
			if (f.isDirectory())
			{
				String[] files = f.list();

				if (files.length > 0)
				{
					for (int x = 0; x < files.length; x++)
					{
						deleteFile(f.getAbsolutePath() + File.separator + files[x]);
					}
					//ErrorHandler("Delete: directory not empty: " + filename);
					//return;
				}
			}

			// If we passed all the tests, then attempt to delete it
			boolean success = f.delete();

			//uncomment this to see list of deleted files
			//logScriptInfo("Deleted: " + f);

			// And throw an exception if it didn't work for some (unknown) reason.
			// For example, because of a bug with Java 1.1.1 on Linux,
			// directory deletion always fails
			if (!success)
			{
				//errorHandler("Delete: delete failed");
				return;
			}
		}
		catch (IllegalArgumentException iae)
		{
			ATUReports.add("Delete: delete failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			return;
		}
	}

	
       
	/**
	 * Creates specified directories <p>
	 * @param sDirName 
	 * @Parameter: sDirName = path &  directory name to create. example - MakeDir("c:\\First\\Second\\Third");
	 * this will create all three directories nested within the parent directory
	 * @return boolean true if directory was created false if it was not
	 */
    
	public static boolean makeDirs(String sDirName)
	{
		boolean b = false;

		// Create a File object to represent the filename
		File dir = new File(sDirName);

		// If the directory doesn't already exists create a new directory
		if (!dir.exists())
		{
			b = dir.mkdirs();

			if (b == false)
			{
				
				ATUReports.add("Error creating directory: " + sDirName, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				
				return b;
			}
			else
			{
				return b;
			}
		}
		else
		{
			return true;
		}

	}


       
	/**
	 * Copies all files under source folder to destination folder. If destination folder does
	 * not exist, it will be created.
	 * @param srcDir 
	 * @param dstDir 
	 *
	 * @Parameter: srcDir source
	 * @Parameter: dstDir destination
	 *
	 * @return true if success, false otherwise
	 */
       
	public static boolean copyDir(String srcDir, String dstDir) {
		return copyDir(new File(srcDir), new File(dstDir));
	}

	
	public static boolean copyDir(File srcDir, File dstDir) {
		if (dstDir.getAbsolutePath().indexOf(srcDir.getAbsolutePath())==0)
			return false;
		if (srcDir.isDirectory()) {
			if (!dstDir.exists()) {
				dstDir.mkdir();
			}
			String[] files = srcDir.list();
			for (int i = 0; i < files.length; i++) {
				if (copyDir(new File(srcDir, files[i]), new File(dstDir, files[i])) == false) {
					return false;
				}
			}
			return true;
		}
		return copyFile(srcDir, dstDir);
	}

	
       
	/**
	 * Copies one file to another without any checkings.
	 * @param fileIn 
	 * @param fileOut 
	 *
	 * @Parameter: fileIn  source
	 * @Parameter: fileOut destination
	 *
	 * @return true if operation was successful, false otherwise
	 */
       
	public static boolean copyFile(String fileIn, String fileOut) {
		return copyFile(new File(fileIn), new File(fileOut), FILE_BUFFER_SIZE);
	}
	
	/**
	 * Copies one file to another without any checkings.
	 * @param fileIn 
	 * @param fileOut 
	 *
	 * @Parameter: fileIn  source
	 * @Parameter: fileOut destination
	 *
	 * @return true if operation was successful, false otherwise
	 */
	public static boolean copyFile(File fileIn, File fileOut) {
		return copyFile(fileIn, fileOut, FILE_BUFFER_SIZE);
	}
	
	
	/**
	 * Copies one file to another without any checkings.
	 * @param fileIn 
	 * @param fileOut 
	 * @param bufsize 
	 *
	 * @Parameter: fileIn  source
	 * @Parameter: fileOut destination
	 * @Parameter: bufsize buffer size
	 *
	 * @return true if operation was successful, false otherwise
	 */
	public static boolean copyFile(String fileIn, String fileOut, int bufsize) {
		return copyFile(new File(fileIn), new File(fileOut), bufsize);
	}

	/**
	 * Copies one file to another without any checkings.
	 * @param fileIn 
	 * @param fileOut 
	 * @param bufsize 
	 *
	 * @Parameter: fileIn  source
	 * @Parameter: fileOut destination
	 * @Parameter: bufsize buffer size
	 *
	 * @return true if operation was successful, false otherwise
	 */
	public static boolean copyFile(File fileIn, File fileOut, int bufsize) {
		FileInputStream in = null;
		FileOutputStream out = null;
		boolean result = false;
		if(!fileIn.exists()) 
			return result;
		try {
			if(!fileOut.exists()){
				File parent = new File(fileOut.getParent());
				if(!parent.exists()){
					parent.mkdirs();
				}
				fileOut.createNewFile();
			}

			in = new FileInputStream(fileIn);
			out = new FileOutputStream(fileOut);
			copyPipe(in, out, bufsize);
			result = true;
		} catch(IOException ioex) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch(IOException ioex) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch(IOException ioex) {
				}
			}
		}
		return result;
	}

	
       
	/**
	 * Reads from input and writes read data to the output, until the stream ends.
	 * @param in 
	 * @param out 
	 * @param bufSizeHint 
	 *
	 * @Parameter: in
	 * @Parameter: out
	 * @Parameter: bufSizeHint
	 *
	 * @throws IOException
	 */
       
	public static void copyPipe(InputStream in, OutputStream out, int bufSizeHint) throws IOException {
		int read = -1;
		byte[] buf = new byte[bufSizeHint];
		while ((read = in.read(buf, 0, bufSizeHint)) >= 0) {
			out.write(buf, 0, read);
		}
		out.flush();
	}


       
	/**
	 * Unpacks a zip file to the target directory.
	 * @param sZipFile 
	 * @param sDestDir 
	 *
	 * @Parameter: zipFile zip file
	 * @Parameter: destDir destination directory
	 *
	 * @throws IOException
	 */
       
	public static void unzip(String sZipFile, String sDestDir) throws IOException {
		unzip(new File(sZipFile), new File(sDestDir));
	
	}
	
	
	
	public static void unzip(File zipFile, File destDir) throws IOException {
		ZipFile zip = new ZipFile(zipFile);
		Enumeration<?> en = zip.entries();
		int bufSize = 8196;

		while (en.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) en.nextElement();
			File file = (destDir != null) ? new File(destDir, entry.getName()) : new File(entry.getName());
			if (entry.isDirectory()) {
				if (!file.mkdirs()) {
					if (file.isDirectory() == false) {
						throw new IOException("Error creating directory: " + file);
					}
				}
			} else {
				File parent = file.getParentFile();
				if (parent != null && !parent.exists()) {
					if (!parent.mkdirs()) {
						if (file.isDirectory() == false) {
							throw new IOException("Error creating directory: " + parent);
						}
					}
				}

				InputStream in = zip.getInputStream(entry);
				try {
					OutputStream out = new BufferedOutputStream(new FileOutputStream(file), bufSize);
					try {
						copyPipe(in, out, bufSize);
					} finally {
						out.close();
					}
				} finally {
					in.close();
					zip.close();
				}
			}
		}
	}
	
	
	/**
	 * Writes information to a temporary file on disk.
	 * @param sFilename 
	 * @param sKey 
	 * @param sValue 
	 * @Parameter: String sFilename
	 * @Parameter: String sKey
	 * @Parameter: String sValue
	 */
	public static void setTempVarToDisk(String sFilename, String sKey, String sValue)
	{
	try
	{
		FileOutputStream out = new FileOutputStream(sFilename);
		Properties settings = new Properties();

		settings.put(sKey, sValue);
		
		//Close out properties file
		settings.store(out, "");
		out.close();

	}
	catch (IOException ioe)
	{
		
		ATUReports.add("Error saving temporary variable from disk: " + sFilename + ioe.getMessage(), LogAs.FAILED, null);
	}
	}

	
	
	
	
	/**
	* Removes specified file extension from a given file name or path name
	 * @param sFile 
	 * @param sExt 
	* @Parameter: - String sFile - file name or path i.e. c:\\temp\\myfle.java
	* @Parameter: - String sExt - extension to be removed i.e. ".java"
	* @return  file name string without extension i.e. "c:\\temp\\myfile"
	*/
	public static String removeFileExtension(String sFile, String sExt)
	{
		String sOut = "";
		sOut = Strings.replace(sFile,sExt,"");
		return sOut;
	}
	
	
	/**
	 * updates the specified file by replacing the sSearchFor string with the sReplaceWith string
	 * and re-writing the file out with updated contents
	 * @param sFile 
	 * @param sSearchFor 
	 * @param sReplaceWith 
	 * @Parameter: String sFile - the file to be read in and updated
	 * @Parameter: String sSearchFor - the string to be replaced
	 * @Parameter: String sSearchFor - the string to be added
	 */
	public static void updateFile(String sFile, String sSearchFor, String sReplaceWith)
	{
		
		//display error if specified file does not exist
		File f = new File(sFile);
		if (!f.exists()){
			ATUReports.add("Could not find file: " + sFile, LogAs.FAILED, null);
			return;
		}
		
		String sOut = FileIO.getFileContents(sFile);
		
		if (sOut.indexOf(sSearchFor) == -1){
			ATUReports.add("Could not find search string: " + sSearchFor + " in the file " + sFile, LogAs.FAILED, null);
			return;
		}
		
		//replace search string with replace string
		sOut = Strings.replace(sOut,sSearchFor,sReplaceWith);
		
		//write out updated file
		FileIO.writeFileContents(sFile,sOut);
		
	}
	
	/**
	 * updates the specified file by replacing the sSearchFor string with the sReplaceWith string
	 * and re-writing the file out with updated contents
	 * @Parameter: String sFile - the file to be read in and updated
	 * @Parameter: String sSearchFor - the string to be replaced
	 * @Parameter: String sReplaceWith - the string to be added
	 * @Parameter: String charset - charset such as "UTF-8"
	 * @param sFile 
	 * @param sSearchFor 
	 * @param sReplaceWith 
	 * @param charset 
	 */
	public static void updateFile(String sFile, String sSearchFor, String sReplaceWith, String charset){
//		display error if specified file does not exist
		File f = new File(sFile);
		if (!f.exists()){
			
			ATUReports.add("Could not find file: " + sFile, LogAs.FAILED, null);
			return;
		}
		
		String sOut = FileIO.getFileContents(sFile,charset);
		
		if (sOut.indexOf(sSearchFor) == -1){
					
			ATUReports.add("Could not find search string: " + sSearchFor + " in the file " + sFile, LogAs.FAILED, null);
			return;
		}
		
		//replace search string with replace string
		sOut = Strings.replace(sOut,sSearchFor,sReplaceWith);
		
		//write out updated file
		FileIO.writeFileContents(sFile,sOut,charset);
	}
	
	/**
	 * updates the specified file by replacing the sSearchFor string with the sReplaceWith string. Use charset "UTF-8".
	 * and re-writing the file out with updated contents
	 * @Parameter: String sFile - the file to be read in and updated
	 * @Parameter: String sSearchFor - the string to be replaced
	 * @Parameter: String sReplaceWith - the string to be added
	 * @param sFile 
	 * @param sSearchFor 
	 * @param sReplaceWith 
	 */
	public static void updateFileByUTF8(String sFile, String sSearchFor, String sReplaceWith){
		updateFile(sFile, sSearchFor, sReplaceWith, "UTF-8");
	}
	
	/**
	 * updates the specified property file by replacing the sSearchkey value with a sNewVal
	 * and re-writes the file with updated contents
	 * @param sPropFile 
	 * @param sSearchKey 
	 * @param sNewVal 
	 * @Parameter: String sPropFile - the property file (complete path and filename) to be read in and updated
	 * @Parameter: String sSearchKey - the property key to search for
	 * @Parameter: String sNewVal - the new value for the search key property
	 */
	public static void updatePropertyFile(String sPropFile, String sSearchKey, String sNewVal)
	{
		
		//display error if specified prop file does not exist
		File f = new File(sPropFile);
		if (!f.exists()){
	
			ATUReports.add("Could not find property file: " + sPropFile, LogAs.FAILED, null);
			return;
		}
		
		//get property file content as string array
		String sTmp[] = FileIO.getFileContentsAsList(sPropFile);
		
		String sKey = sSearchKey+"=";
		String sLine = ""; 
		String s = "";
		
		//cycle through property file contents line by line
		for (int x =0;x < sTmp.length;x++)
			{
			
			//ignore empty or null lines
			if (sTmp[x] == null || sTmp[x].toString().equals("")) {
				continue;
			}
			
			//ignore comment lines
			if (sTmp[x].toString().indexOf("#") == 0)
				continue;
			
			//assign line content to string s
			s = sTmp[x].toString();
			
			//compare line s to the searchkey
			if (sKey.indexOf(s) != -1 || s.indexOf(sKey) != -1)
				{
					//if searchkey is found get line content and ssign it to sLine and break loop
					sLine = sTmp[x].toString();
					break;
				}
			}
		
		if (sLine.equals("")){
			
			ATUReports.add("Could not find property key: " + sSearchKey + " in property file " + sPropFile +" is present", LogAs.FAILED, null);
			return;
		}
		
		
		//get property file content as string
		String sOut = FileIO.getFileContents(sPropFile);	
		//VisualReporter.logScriptInfo(sOut);
		//VisualReporter.logScriptInfo("Search For: " + sSearchFor);
		//VisualReporter.logScriptInfo("Replace With: " + sReplaceWith);
		
		//replace original property key and value with new value
		sOut = Strings.replace(sOut,sLine,sKey + sNewVal);
		
		//write out updated property file
		FileIO.writeFileContents(sPropFile,sOut);
		
		
	}
	
	
	
	
	/** 	
	* Reads specified file contents and returns file contents as a string <p>
	* @Parameter: filename		Path and filename of file to read
	* @return String of specified file contents
    * @param source 
	*/
	public static String readFile(String source) {
		String file = "";
		try {
			FileInputStream fstream = new FileInputStream(source);
			BufferedReader in =
				new BufferedReader(new InputStreamReader(fstream));
			String line;

			while ((line = in.readLine()) != null) {
				file = file + line + "\n";
			}
			in.close();
		} catch (IOException e) {
			
			ATUReports.add("Error in FileIO.readFile: " + e.getMessage() , LogAs.FAILED, null);
		}
		return file;
	}

	/** 	
	* Reads specified file contents and returns file contents as a string <p>
	* @Parameter: filename 		Path and filename of file to read
	* @return   String of specified file contents
    * @param filename 
	*/
	public static String getFileContents(String filename) {

		try {
			File file = new File(filename);
			FileReader in = new FileReader(file);
			char c[] = new char[(int) file.length()];
			in.read(c);
			String fileContentsString = new String(c);
			in.close();
			return fileContentsString;
		} catch (IOException e) {
	
			ATUReports.add("Error in FileIO.getFileContents: " + e.getMessage() , LogAs.FAILED, null);
			return "";
		}
	}
	
	
	/** 	
	* Returns file content as a multidimensional String Array
	* @param: filename 		Path and filename of file to read and delimeter of data within file
	* @param: sDelimeter character that separates data in the file
	* @param: iCols number of columns of data in the file
	* @return String[][] returns multidimensional string array of data within file separated by a delimeter
	*/
	public static String[][] getFileContentsAsArray(String filename, String sDelimeter, int iRows, int iCols) {
		
		int r = iRows;
		int c = iCols;
		
		String lsCols[] = new String[iCols];
		String[][] lsData = new String [r][c];
		
				
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			for (int z =0; z < r; z++){ 
				line = in.readLine();
				if (line != null) 
				{
				for(int i = 0; i <= line.length(); i++)  {
				    // Get line values
				    lsCols = line.split(sDelimeter);
				    for(int j = 0; j < lsCols.length; j++) {
				        // Put in Matrix
				        lsData[z][j] = lsCols[j];
				    }
				}
				
				}
			}
			in.close();
		} catch (Exception e) {
		
			ATUReports.add("Error in FileIO.getFileContentsAsList: " + e.getMessage() , LogAs.FAILED, null);
		}
		return lsData;
	}
	
	
	
	/**
	 * Get file contents from specified file by given charset.
	 * @Parameter: filename - absolute path of target file
	 * @Parameter: charset - charset such as "UTF-8"
	 * @return String - content of target file or null if there is any exception occurred
	 * @param filename 
	 * @param charset 
	 */
	public static String getFileContents(String filename, String charset){
		//create reader - if charset is not specified or not supported, use default charset;
		InputStreamReader reader = null;
		if(charset==null){
			try {
				reader = new InputStreamReader(new FileInputStream(filename));
			} catch (FileNotFoundException e) {
				
				ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e.getMessage() , LogAs.FAILED, null);
				return "";
			}
		}else{
			try {
				try {
					reader = new InputStreamReader(new FileInputStream(filename),charset);
				} catch (FileNotFoundException e) {
				
					ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e.getMessage() , LogAs.FAILED, null);
					return "";
				}
			} catch (UnsupportedEncodingException e) {
				try {
					reader = new InputStreamReader(new FileInputStream(filename));
				} catch (FileNotFoundException ee) {
				
				ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + ee.getMessage() , LogAs.FAILED, null);
					return "";
				}
			}
		}
		
		//read content
		char[] c = new char[1024];
		int count = 0;
		StringBuffer buffer = new StringBuffer();
		try {
			while((count=reader.read(c))!=-1){
				buffer.append(c,0,count);
			}
		} catch (IOException e) {
		
			ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e.getMessage() , LogAs.FAILED, null); 
			return "";
		}
		
		//return
		try{
			reader.close();
		}
		catch(Exception e2){
			
			ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e2.getMessage() , LogAs.FAILED, null);
		}
		
		return buffer.toString();
	}
	
	/**
	 * Get file contents from specified file by charste "UTF-8".
	 * @Parameter: filename - absolute path of target file
	 * @return String - content of target file or null if there is any exception occurred
	 * @param filename 
	 */
	public static String getFileContentsByUTF8(String filename){
		return getFileContents(filename, "UTF-8");
	}

	
	/** 	
	* Returns File array of files contained within a given directory<P>
	* @Parameter:  sDir	Directory to read
	* @return   Returns File array of files contained within a given directory
    * @param sDir 
	*/
	public static File[] getDirContents(String sDir) {

		try {
			File fDir = new File(sDir);
			
			return fDir.listFiles();
		}
		catch (Exception e) {
			
			ATUReports.add("Error in FileIO.getDirContents: " + e.getMessage() , LogAs.FAILED, null);
			return null;
		}
	}
	
	
	/** 	
	* Returns file content as a String Array
	* @Parameter: filename 		Path and filename of file to read
	* @return file content as a String Array
    * @param filename 
	*/
	public static String[] getFileContentsAsList(String filename) {
		int z = 0;
		int n = getNumberOfLinesInFile(filename);
		String lsLines[] = new String[n];

		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = in.readLine()) != null) {
				if (z >= n)
					break;
				
				lsLines[z] = line;
				z++;
			}
			in.close();
		} catch (IOException e) {
			
			ATUReports.add("Error in FileIO.getFileContentsAsList:  " + e.getMessage() , LogAs.FAILED, null);
		}
		return lsLines;
	}
	
	/**
	 * Returns file content as a String array.
	 * @Parameter: filename - absolute path of target file
	 * @Parameter: charset - charset such as "UTF-8"
	 * @return String[] - String array which contain file content one line by one element
	 * @param filename 
	 * @param charset 
	 */
	public static String[] getFileContentsAsList(String filename, String charset) {
		int z = 0;
		int n = getNumberOfLinesInFile(filename);
		String lsLines[] = new String[n];
		
		// create reader - if charset is not specified or not supported, use default charset;
		BufferedReader reader = null;
		if(charset==null){
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			} catch (FileNotFoundException e) {
	
				ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e.getMessage() , LogAs.FAILED, null);
				return lsLines;
			}
		}else{
			try {
				try {
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),charset));
				} catch (FileNotFoundException e) {
				
					ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e.getMessage() , LogAs.FAILED, null);
					return lsLines;
				}
			} catch (UnsupportedEncodingException e) {
				try {
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
				} catch (FileNotFoundException ee) {
									
					ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + ee.getMessage() , LogAs.FAILED, null);
					
					return lsLines;
				}
			}
		}
		
		
		//read content
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if (z >= n)
					break;
				lsLines[z] = line;
				z++;
			}
			reader.close();
		} catch (IOException e) {
			
			ATUReports.add("Error in FileIO.getFileContents(String filename, String charset):  " + e.getMessage() , LogAs.FAILED, null);
			return lsLines;
		}
		
		//return
		return lsLines;
	}
	
	/**
	 * Returns file content as a String array. Use charset "UTF-8".
	 * @Parameter: filename - absolute path of target file
	 * @Parameter: charset - charset such as "UTF-8"
	 * @return String[] - String array which contain file content one line by one element
	 * @param filename 
	 */
	public static String[] getFileContentsAsListByUTF8(String filename) {
		return getFileContentsAsList( filename, "UTF-8");
	}

	/** 	
	* Returns number of lines in a specified file <p>
	* @Parameter: filename		Path and filename of file to read
	* @return number of lines in specified file
    * @param filename 
	*/
	public static int getNumberOfLinesInFile(String filename) {
		int i = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while (in.readLine() != null)
				i++;
			in.close();
		} catch (IOException e) {
			
			ATUReports.add("Error in FileIO.getNumberOfLinesInFile: " + e.getMessage() , LogAs.FAILED, null);
			
		}
		return i;
	}

	/** 
	* Writes specified string content to file <p>
	* @Parameter: filename		path and filename of file to write string to
	* @Parameter: sContents		String to write to file
    * @param filename 
    * @param sContents 
	*/
	public static void writeFileContents(String filename, String sContents) {
		//write specified string content to file	
		try {
			FileWriter out = new FileWriter(filename);
			out.write(sContents);
			out.close();
		} catch (IOException e) {

			ATUReports.add("Error in FileIO.writeFileContents: " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	
	/**
	 * Write specified string content into given file with specified charset. If the charset is null or not supported, will use default charset.
	 * @Parameter: filename - absolute path of file
	 * @Parameter: sContents - content to be written
	 * @Parameter: charset - charset such as "UTF-8"
	 * @param filename 
	 * @param sContents 
	 * @param charset 
	 */
	public static void writeFileContents(String filename, String sContents, String charset) {
		//	write specified string content to file
		OutputStreamWriter out = null;
		
		// if charset is null, use default charset
		if(charset==null){
			try {
				out = new OutputStreamWriter(new FileOutputStream(filename));
			} catch (FileNotFoundException e) {

				ATUReports.add("Error in FileIO.writeFileContents(String filename, String sContents, String charset): " + e.getMessage() , LogAs.FAILED, null);
			}
		}else{
			// if charset is specified, try use it; if it is not support, use default charset
			try {
				out = new OutputStreamWriter(new FileOutputStream(filename),charset);
			} catch (UnsupportedEncodingException e) {
				
				ATUReports.add("FileIO.writeFileContents(String filename, String sContents, String charset): '"+charset+"' is NOT SUPPORTED! Default charset will be used!" + e.getMessage() , LogAs.FAILED, null);
				try {
					out = new OutputStreamWriter(new FileOutputStream(filename));
				} catch (FileNotFoundException ee) {

					ATUReports.add("Error in FileIO.writeFileContents(String filename, String sContents, String charset): " + ee.getMessage() , LogAs.FAILED, null);
				}
			} catch (FileNotFoundException e) {
				
				ATUReports.add("Error in FileIO.writeFileContents(String filename, String sContents, String charset): " + e.getMessage() , LogAs.FAILED, null);
			}
		}
		try {
			out.write(sContents);
			out.close();
		} catch (IOException e) {

			ATUReports.add("Error in FileIO.writeFileContents(String filename, String sContents, String charset): " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	
	/**
	 * Write specified string content into give file with UTF-8 encoding.
	 * @Parameter: filename - absolute path of file
	 * @Parameter: sContents - content to be written
	 * @param filename 
	 * @param sContents 
	 */
	public static void writeFileContentsByUTF8(String filename, String sContents){
		writeFileContents(filename, sContents, "UTF-8");
	}

	/** 
	* Writes specified List content to file (CSV or TXT)
	* @param: filename		path and filename of file to write List to
	* @Param: lsContents	List to write to file
s	*/
	public static void writeListContents(String filename, List<String> lsContents, String sSeparator) {
		//write specified string content to file	
		try {
	       FileWriter fw = new FileWriter(filename);
	       PrintWriter out = new PrintWriter(fw);

			// ',' divides the word into columns and prints the Header row
			for (int i = 0;i < lsContents.size(); i++) {
				if (i < (lsContents.size()-1)) {
					out.print(lsContents.get(i) + sSeparator);// first row first column
					//out.print(",");
				}else
					out.println(lsContents.get(i));
			}
			out.flush();
			out.close();
		} catch (IOException e) {
	
			ATUReports.add("Error in FileIO.writeListContents: " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	
	/** 
	* Appends specified List content to file (CSV or TXT)
	* @Parameter: filename		path and filename of file to append List to
	* @Parameter: lsContents	List to append to the file
	*/
	public static void appendListContents(String filename, List<String> lsContents, String sSeparator) {
		//write specified string content to file	
		try {
		       FileWriter fw = new FileWriter(filename, true);
		       PrintWriter out = new PrintWriter(fw);

				// ',' divides the word into columns and prints the Header row
				for (int i = 0;i < lsContents.size(); i++) {
					if (i < (lsContents.size()-1)) {
						out.print(lsContents.get(i) + sSeparator);// first row first column
						//out.print(",");
					}else
						out.println(lsContents.get(i));
				}
				out.flush();
				out.close();
		} catch (IOException e) {
			
			ATUReports.add("Error in FileIO.appendStringToFile: " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	
	/** 
	* Appends specified string content to a file <p>
	* @Parameter: filename		path and filename of file to append string to
	* @Parameter: sContents		String to append to file
    * @param filename 
	* @param sContents 
	*/
	public static void appendStringToFile_Old(String filename, String sContents) {
		try {
			FileWriter out = new FileWriter(filename, true); //tells FileWriter to append
			out.write(System.getProperty("line.separator") + sContents);
			out.close();
		} catch (IOException e) {

			ATUReports.add("Error in FileIO.appendStringToFile: " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	/**
	 * write contents to given file except .html file using UTF-8 encoding.
	 * @Parameter: filename - name of target file 
	 * @Parameter: sContents - content will be written to 
	 * @param filename 
	 * @param sContents 
	 */
	public static void appendStringToFile(String filename, String sContents) {
		try {
			FileOutputStream out = new FileOutputStream(filename,true);//
			byte[] bytes=getbyteString(System.getProperty("line.separator") + sContents,"UTF-8");
			out.write(bytes);//contentBytes
			out.close();
		} catch (IOException e) {
	
			ATUReports.add("Error in FileIO.appendStringToFile: " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	/**
	 * This method is just used to write content to log html file
	 * @Parameter: filename - name of html log
	 * @Parameter: sContents - content will be written to html file.
	 * @param filename 
	 * @param sContents 
	 */
	public static void appendStringToHtmlFile(String filename, String sContents) {
		try {
			FileOutputStream out = new FileOutputStream(filename,true);
			byte[] bytes=getbyteString(System.getProperty("line.separator") + sContents,null);
			out.write(bytes);//contentBytes
			out.close();
		} catch (IOException e) {
			
			ATUReports.add("Error in FileIO.appendStringToFile: " + e.getMessage() , LogAs.FAILED, null);
		}
	}
	
	/**
	 * print given byte array
	 * @param banner 
	 * @param abc 
	 */
	public static void printBytes(String banner,byte[] abc){
		System.out.print(banner+"=");
		for(int i=0; i<abc.length; i++){
			System.out.print(java.lang.Integer.toHexString(abc[i])+"  ");
		}
		System.out.println();
	}
	/**
	 * 
	 * @param s 
	 * @param coding 
	 * @Parameter: s - string
	 * @Parameter: coding - encoding way. like UTF-8, UTF-16, GB2312 and so on. if coding equals null, it will use default encoding way.
	 * @return bytes of given string using specific encoding
	 */
	public static byte[] getbyteString(String s, String coding){
		if (s == null) {
			return null;
		}
		byte[] result;
		try{
			if(null==coding){
				result=s.getBytes();
			}else
				result=s.getBytes(coding);
			return result;
		}catch(Exception e){
			result=null;
		}
		return result;
	}
	
	/**
     * This method takes a path and returns the parent.  Works with either
     * backslash or slash delimiter. Examples: c:\test1\blah  returns
     * c:\test1\ <br>
     * /usr/local/bin/bash returns /usr/local/bin/
	 * @param path 
     *
     * @Parameter: path The path to get the parent of
     *
     * @return The parent path
     */
    public static String getParentPath(String path)
    {
        //break up the filename and the path
        int i = path.lastIndexOf("\\");
        int j = path.lastIndexOf("/");
        int last = (i > j) ? i : j;

        //String fn = path.substring(last+1,path.length());
        return path.substring(0, last + 1);
    }

    /**
     * This method takes a path and strips the path info.  Works with either
     * backslash or slash delimiter. Examples: c:\test1\blah  returns blah <br>
     * /usr/local/bin/bash returns bash
     * @param path 
     *
     * @Parameter: path The path to strip
     *
     * @return The final filename or directory name without path info
     */
    public static String stripPath(String path)
    {
        //break up the filename and the path
        int i = path.lastIndexOf("\\");
        int j = path.lastIndexOf("/");
        int last = (i > j) ? i : j;

        return path.substring(last + 1, path.length());

        //String path1= path.substring(0, last + 1);
    }

    /**
     * Strips trailing slash from specified path string
     * @param path 
     * @return 
     * @Parameter: String path - (i.e. "c:\\auto\\test\\one\\")
     */
    public static String stripTrailingSlash(String path)
    {
        if (path.equals(""))
        {
            return path;
        }

        int i = path.lastIndexOf("\\");
        int j = path.lastIndexOf("/");
        int last = (i > j) ? i : j;

        if (last == (path.length() - 1))
        {
            return path.substring(0, path.length() - 1);
        }
        else
        {
            return path;
        }
    }
    
    /**
     * Strips leading slash from specified path string
     * @param path 
     * @return 
     * @Parameter: String path - (i.e. "c:\\auto\\test\\one\\")
     */
    public static String stripLeadingSlash(String path){
    	if (path.startsWith("/") || path.startsWith("\\")) return path.substring(1);
    	return path;
    }

    /**
     * Returns the first part of a path.  For instance, if the input is  "/My
     * Portal/blah2/blah4", it returns "My Portal".  It does not matter if the
     * path starts with a slash or not.  Works with either backslash or slash.
     * @param path 
     *
     * @Parameter: path The path to operate on.
     *
     * @return The first part of the path.
     */
    public static String getTopParent(String path)
    {
    	
    	
        path = normalizePath(path);

        int i = path.indexOf(backslash);
        int j = path.indexOf(slash);
        String sep;

        if (i < 0)
        {
            sep = slash;
        }
        else if (j < 0)
        {
            sep = backslash;
        }
        else
        {
            sep = (i < j) ? backslash : slash;
        }

        //return path.substring(0,first);
        StringTokenizer st = new StringTokenizer(path, sep);

        try
        {
            return st.nextToken();
        }
        catch (Exception nsee)
        {
            return "";
        }
    }
	
    /**
	 * @Method: getLastChildInPath (String sDirectory)
	 * @Description: Method gets an immediate folder name from directory  path provided. 
	 *  For example, if directory passed is 'C:\folder1\folder2' OR 
	 * '/folder1/folder2/', the method will return 'folder2'. 
	 * If directory is 'C:\' OR 'C:', the method will return 'C:' 
	 * @Parameter: sDirectory - String representing directory path; it can include 
	 * the trailing slash, but does not have to.
	 * @Return: String with last directory path container. If error occurs, 
	 * empty String is returned. 
	 */
	public static String getLastChildInPath (String sDirectory){
		
		try {
			//If directory includes the trailing slash, it is stripped
			return stripPath(stripTrailingSlash(sDirectory));		
			
		}catch(Exception e) {	
			
			ATUReports.add("An exception occured in FileIO.getDirImmediateFolder(): " + e.getMessage(), LogAs.FAILED, null);
			
			return "";
		}
	}
	
    /**
     * Strips off the first part of a path. For instance, if the input is  "/My
     * Portal/blah2/blah4", it returns "/blah2/blah4".  Backslash is not
     * supported.
     * @param path 
     *
     * @Parameter: path The path to operate on
     *
     * @return The path with the first part stripped off.
     */
    public static String stripTopParent(String path)
    {
        if (path.length() <= 1)
        {
            return "";
        }

        path = normalizePath(path).substring(1);

        int first = path.indexOf(slash);
        path = path.substring((first == -1) ? path.length() : first,
                path.length()).trim();

        return path.equals("/") ? "" : path;
    }

    /**
     * Adds a leading slash, if necessary, to a path name, and removes any
     * multiple  consecutive slashes "///" if necessary.
     * @param path 
     * @return 
     *
     */
    public static String normalizePath(String path)
    {
        if (!path.startsWith("/"))
        {
            path = "/" + path;
        }

        while (path.indexOf("//") != -1)
        {
            path = Strings.replace(path, "//", "/");
        }

        return stripTrailingSlash(path);
    }

    /**
     * Deletes specified directories
     * <p>
     * 
     * @Parameter: sDirName =
     *            path & directory name to create. example -
     *            MakeDir("c:\\First\\Second\\Third"); this will create all
     *            three directories nested within the parent directory
     * @param sDirName 
     */

    public static void deleteDirs(String sDirName) {
    	// Create a File object to represent the filename
    	File dir = new File(sDirName);
    	try {
    		// If the directory doesn't already exists create a new directory
    		if (!dir.exists()) {
    			return;
    		}
    		boolean success = dir.delete();
    		if (!success) {
    			// errorHandler("Delete: delete failed");
    			return;
    		}
    	} catch (IllegalArgumentException iae) {
    		
    		ATUReports.add("deleting directory"+ iae.getMessage() , LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
    		
    		return;
    	}

    }



    public static boolean fileExists(String sFileName)
    {
    	File fileToCheck = new File(sFileName);
		return fileToCheck.exists();
    }
    
    
    public static boolean pathExists(String sPathName)
    {
    	return fileExists(sPathName);
    }

   
    /**
     * @Method: makeAbsoluteProjectPath (String sDirectory)
     * @Description: This method creates an absolute path from a given path,  
      * based on a current project path. 
      *  For example, if Project path is '/ParentFolder/folder1/folder2/' 
      *  and directory passed is either '../folder2/folder3/', 
      *  '../folder1/folder2/folder3/', '/ParentFolder/folder1/folder2/folder3' 
      *  or 'root\\ParentFolder\\folder1\\folder2\\folder3\\', 
      *  path returned is always 'root\\ParentFolder\\folder1\\folder2\\folder3\\' 
      *  where root is a current local drive i.e. C: or D:.
     * @param sDir - String representing Path to be changed into absolute directory 
     * @return String - it can hold either value of absolute path, i.e.  
      * 'C:\\ParentFolder\\folder1\\folder2\\folder3\\' or an empty string if the path 
      * passed is not related to the current project path.
     */
    public static String makeAbsoluteProjectPath(String sDir) {
    	
    	String sCurrProjPath = new File(".").getAbsolutePath().replace(".", "");
    	String sBackslash 	 = Platform.getFileSeparator();
    	String sTopDir 		 = "";
    	
    	if (sDir.contains(":")) {
			String sRoot = sDir.substring(0, sDir.lastIndexOf(":") + 1);
			sDir = sDir.replace(sRoot, sRoot.toUpperCase());
    	}
    	
    	if (sDir.contains("..")) {
    		
    		sDir = sDir.replace("..", "");    
    		sTopDir = getTopParent(sDir) + sBackslash;
    		
    		if (sCurrProjPath.contains(sTopDir)) {
    			while (sCurrProjPath.contains(sTopDir)) {
        			sDir =  stripTopParent(sDir);
        			sTopDir = getTopParent (sDir) + sBackslash;    				
        		}
    			
       			return sCurrProjPath + stripLeadingSlash(sDir).replace(slash, sBackslash) + sBackslash;
    		
    		} else
    			return "";

    	} else if (sDir.contains(slash)) {  
    		sDir = sDir.replace(slash, backslash);

    		if (sDir.contains(sCurrProjPath.substring(sCurrProjPath.lastIndexOf(":") + 1))) 
    			return sCurrProjPath.substring(0, sCurrProjPath.lastIndexOf(":") + 1) + sDir;
    		else 
    			return "";
    	} else {    		
    		if (sDir.contains(sCurrProjPath)) 	
    			return sDir;
    		else
    			return "";
    	}
    }
    
    
    
    /** 	
	* @Function: compareFiles
	* @param filename Path and filename of file1 to read ex: "D:\\1.TXT" or "D:\\1.xml"
	* @param filename Path and filename of file2 to read ex: "D:\\1.TXT" or "D:\\1.xml"
	* @return boolean true if files compare , false if files do not compare
	*/
	public static boolean compareFiles(String filename1,String filename2) {
		
		String [] array1 = null;
		String [] array2 = null;
		try{
			array1= FileIO.getFileContentsAsList(filename1);
			array2= FileIO.getFileContentsAsList(filename2);
		}catch (Exception e) {
			
			ATUReports.add("Error in FileIO.compareFile: "+ e.getMessage() , LogAs.FAILED, null);
			
		  }
			
		//Log.logDebugInfo("Comparing int array i1: " + Arrays.toString(array1) + " and i2: " + Arrays.toString(array2));
		
		if (Arrays.deepEquals(array1, array2)){
		    //Log.logScriptInfo("Contents of both files are the same: " + Strings.sDQ + filename1 + Strings.sDQ + " to " + Strings.sDQ + filename2 + Strings.sDQ);
		     return true;
		 } else{
		    //Log.logScriptInfo("Contents of files are NOT the same: " + Strings.sDQ + filename1 + Strings.sDQ + " to " + Strings.sDQ + filename2 + Strings.sDQ);
		    return false;
		 }
	}


    
    
   
}

