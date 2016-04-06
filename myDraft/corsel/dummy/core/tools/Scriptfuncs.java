package dummy.core.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.SeleneseTestBase;

import dummy.core.libs.FileIO;
import dummy.core.libs.Log;
import dummy.core.libs.Platform;
import dummy.core.libs.Strings;


/**
 * @TestScript: Automation Tool Library
 * @Description: This tool provides a library of methods to gather automation and script metrics. 
 * @Author: Tony Venditti
 */
public class Scriptfuncs extends SeleneseTestBase 
	{	
	
	@Before
	/**
	 * @Function: setUp() 
	 * @Description: This function initializes the automation for testing
	 */
	public void setUp() throws Exception {
	
		Log.gsScriptName = this.getClass().getName().toString();
		Log.gsScriptDescription = "Scripting Tools";
		Log.gsScriptAuthor = "Tony Venditti";
		Log.gsScriptTestArea = Platform.getCurrentProjectPath() + Log.gsScriptName.replace(".","\\"); 	
		
		Log.initialize();
		
		Log.logBanner("Run script tool...");
		
		
		//Browser.start();
		//load Google page and wait for search button to appear
		//Browser.loadURL("www.google.com");
		//Platform.sleep(Log.giAutomationShortTO);
		//Browser.click("Images");
		
		
		
		
	}
	
	
	
	
	

	@Test
	public void testRunScriptTool(){	
				
		try{
		
			   

			   String sParentDir = Log.gsAutomationAutoPath + "eb\\scripts\\";
			   System.out.println(sParentDir);
			   //String sParentDir = Log.AutoPath;

			   //prints scriptname and all assocaited script details to the console
			   //printLWPScriptLibraryDetails(sParentDir);

			   //prints script name only from the specified directory to the console.
			   //printScriptList(sParentDir,1);

			   //prints out all scripts from specified directory in a "full file path format" to console
			   //printScriptList(sParentDir,2);

			   //prints out all script directories in specified parent directory in a "full directory path format" to console
			   //printScriptList(sParentDir,5);

			   //prints out all script directories in specified parent directory in a "full file path format" to console
			   //with javadoc code
			   //printScriptList(sParentDir,6);



			   //prints out all scripts from specified directory in the "RFT suite path format" to console
			   printScriptList(sParentDir,3);

			   //prints out all BVT scripts from specified directory to console
			   //printScriptList(sParentDir,"BVT_",3);

			   //prints out all scripts in specified sParentDir - that match the string argument - to console
			   //printScriptList(sParentDir,"FVT_CalForm",3);

			   //prints out all scripts by Tony Venditti to console
			   //printScriptListByAuthor(sParentDir,"Tony Venditti");


		}
		catch(Exception e){
			Log.errorHandler("Error occurred during Scriptfuncs execution.",e);
		}
		
		
	}
	
	
	public static String gsExt = ".java";
	public static String gsContrib = ".java.contrib";
	public static String sOrigResultDir;
	public static String sNewResultDir;



	

		/**
		 * Recursively walk a directory tree and return a List of all
		 * Directories found.
		 *
		 * @param aStartingDir is a valid directory, which can be read.
		 */
		public static ArrayList<File> getDirListing( File aStartingDir )
		{
			ArrayList<File> result = new ArrayList<File>();

			try{
				File[] filesAndDirs = aStartingDir.listFiles();
				List<File> filesDirs = Arrays.asList(filesAndDirs);
				Iterator<File> filesIter = filesDirs.iterator();
				File file = null;
				while ( filesIter.hasNext() ) {
					file = (File)filesIter.next();
					if (file.isDirectory()) {
						//recursive call!  
						result.add(file); //add directory  
						ArrayList<File> deeperList = getDirListing(file);
						result.addAll(deeperList);
					}

				}
			}
			catch(Exception e)
			{
				Log.errorHandler("Error loading component list for directory: " + aStartingDir.toString(),e);
			}

			return result;
		}


		/**
		 * gets all script files, by a given author, in the script directory and returns the script names in a string array.
		 * @param sAuthor - script author
		 */
		public static String[] getScriptLibraryByAuthor(String sScriptLibDir,String sAuthor)
		{
			String sOutList = "";
			String sDQ = "\"";

			String[] lsOut = getScriptLibrary(sScriptLibDir,null,2);

			//String sName = "";
			String sTmp = "";
			String sRFT = "";

			//we have a complete script list now let's get only the scripts by the specified author
			for (int z = 0; z < lsOut.length; z++)
			{
				//sName = lsOut[z];

				//read file contents into sTmp variable
				sTmp = FileIO.getFileContents(lsOut[z]);

				if (sTmp.indexOf("gsScriptAuthor = " + sDQ + sAuthor + sDQ) != -1)
				{
					sOutList = sOutList + "," + lsOut[z];
					//Log.logDebug(sOutList);
				}
			}



			//now convert the file path formatted script out put to RFT suite list format
			String[] lsFiles = Strings.stringToStringArray(sOutList,",");

			for (int x = 0; x < lsFiles.length; x++)
			{
				sRFT = sRFT + "," + convertScriptPathToRFTPath(lsFiles[x]);
			}

			return Strings.stringToStringArray(sRFT,",");
		}



		/**
		 * gets all script files from the script directory and returns the script names (including path) in a string array.
		 * @param sScriptLibDir - string containing the script library directory (i.e. c:\\myscripts\\wcs\\admin\\")  
		 */
		public static String[] getScriptLibrary(String sScriptLibDir)
		{
			File fDirs = new File(sScriptLibDir);
			ArrayList<File> Dirs = getDirListing(fDirs);
			String sScriptsList = "";

			//if there is only one directory, the parent directory, then set it here
			if (Dirs.size() < 1)
			{
				Dirs.add(fDirs);
			}

			//otherwise cycle through all sub directories
			for (int y = 0; y < Dirs.size();y++)
			{			
				File fDir = new File(Dirs.get(y).toString());	

				File lsScripts[] = fDir.listFiles();

				for (int x=0;x < lsScripts.length;x++)
				{
					try{
						if (lsScripts[x].isFile())
						{
							//get script name
							if (lsScripts[x].toString().indexOf(gsExt) != -1 && lsScripts[x].toString().indexOf(gsContrib) == -1)
								sScriptsList = sScriptsList + "," + lsScripts[x].getPath().toString();
						}		
					}
					catch(Exception e){

						e.getMessage();
						return null;
					}


				}

			}

			//Log.logDebug(sScriptsList);
			String[] lsOut = Strings.stringToStringArray(sScriptsList,",");
			return lsOut;	
		}


		/**
		 * Coverts a standard script file path string into one that can be executed as a script in RFT.
		 * i.e. converts "c:\\auto\\LWP\\testcases\\teamspaces\\BVT_Teamspace.java" to "tescases.teamspaces.BVT_Teamspace"
		 * @param sScriptPath
		 * @return
		 */
		public static String convertScriptPathToRFTPath(String sScriptPath)
		{
			String sOut = "";

			int i = sScriptPath.indexOf("scripts");
			sOut = sScriptPath.substring(i);
			sOut = Strings.replace(sOut,"\\",".");
			sOut = Strings.replace(sOut,gsExt,"");

			return sOut;
		}


		/**
		 * Removes specified file extension from a given file name or path name
		 * @param - String sFile - file name or path i.e. c:\\temp\\myfle.java
		 * @param - String sExt - extension to be removed i.e. ".java"
		 * @return  file name string without extension i.e. "c:\\temp\\myfile"
		 */
		public static String removeFileExtension(String sFile, String sExt)
		{
			String sOut = "";
			sOut = Strings.replace(sFile,sExt,"");
			return sOut;
		}



		/**
		 * gets all script files from the script directory and returns the script names, 
		 * in the format specified in the iFormat argument, in a string array.
		 * @param sScriptLibDir - string containing the script library directory (i.e. c:\\myscripts\\wcs\\admin\\")
		 * @param iFormat - integer corresponding to different file formats:
		 * 	1 = Gets script name only -i.e.  BVT_Authentication.java
		 * 	2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
		 *   3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication
		 * 	4 = Gets script name with extension i.e. BVT_Authentication
		 */
		public static String[] getScriptLibrary(String sScriptLibDir, int iFormat)
		{
			String sOutList = "";

			String[] lsOut = getScriptLibrary(sScriptLibDir);
			
			

			//we have a complete script list now let's format the output specified by the iFormat argument
			for (int z = 0; z < lsOut.length; z++)
			{
				if (lsOut[z].isEmpty()){
					System.out.println("");
				}	
				else{
					
				
				
				switch (iFormat)
				{
				case 1: //1 = Gets script name only -i.e.  BVT_Authentication.java
					sOutList = sOutList + "," + new File(lsOut[z]).getName().toString();
					break;
				case 2:	//2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
					sOutList = sOutList +  "," + new File(lsOut[z]).getPath().toString();
					break;
				case 3:	//3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication	
					sOutList = sOutList + "," + convertScriptPathToRFTPath(new File(lsOut[z]).getPath().toString());
					break;
				case 4: //4 = Gets script name without extension i.e. BVT_Authentication
					sOutList = sOutList +  "," + removeFileExtension(new File(lsOut[z]).getName().toString(),".java");
					break;
				case 5:	//5 = Gets script directory name i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\
					sOutList = sOutList +  "," + new File(lsOut[z]).getParent().toString();
					break;
				case 6:	//6 = Gets script directory name for use with javadoc i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\
					if (lsOut[z]!= null){
					sOutList = sOutList +  "," + "cd " + new File(lsOut[z]).getParent().toString();
					sOutList = sOutList +  "," + "javadoc -d %resdir% *.java";
					break;
					}
				default:
					sOutList = sOutList + "," + new File(lsOut[z]).getName().toString();
				break;
				}
				}
			}



			//Log.logDebug(sScriptsList);
			String[] lsOutList = Strings.stringToStringArray(sOutList,",");
			return lsOutList;	
		}



		/**
		 * gets all script files from the script directory, then extracts only the ones 
		 * containing the sSearchBy string and returns only those script names in a string array
		 * @param sScriptLibDir - the path of the script directory i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\"
		 * @param sSearchBy - text to search for in file name. i.e. "BVT_", "GVT", "teamspace", etc. 
		 */
		public static String[] getScriptLibrary(String sScriptLibDir, String sSearchBy)
		{
			String sOutList = "";
			String[] lsOut = getScriptLibrary(sScriptLibDir);

			if (sSearchBy != null)
			{
				//we have a complete script list now let's get only the ones specified by the sSearchBy argument
				for (int z = 0; z < lsOut.length; z++)
				{
					if (lsOut[z].indexOf(sSearchBy) != -1)
					{
						//build new list comtaining only scripts that contain the sSearchBy arg
						sOutList = sOutList + "," + lsOut[z];
					}
				}

				return Strings.stringToStringArray(sOutList,",");
			}
			else
			{
				return lsOut;
			}


		}


		/**
		 * gets all script files from the script directory, then extracts only the ones 
		 * containing the sSearchBy string, then formats the output and returns only those script names in a string array
		 * @param sScriptLibDir - the path of the script directory i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\"
		 * @param sSearchBy - text to search for in file name. i.e. "BVT_", "GVT", "teamspace", etc.
		 * @param iFormat - integer corresponding to different file formats:
		 * 	1 = Gets script name only -i.e.  BVT_Authentication.java
		 * 	2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
		 *   3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication
		 * 	4 = Gets script name with extension i.e. BVT_Authentication 
		 */
		public static String[] getScriptLibrary(String sScriptLibDir, String sSearchBy, int iFormat)
		{
			String sOutList = "";
			String[] lsOut = getScriptLibrary(sScriptLibDir,sSearchBy);

			//		we have a complete script list now let's format the output specified by the iFormat argument
			for (int z = 0; z < lsOut.length; z++)
			{
				switch (iFormat)
				{
				case 1: //1 = Gets script name only -i.e.  BVT_Authentication.java
					sOutList = sOutList + "," + new File(lsOut[z]).getName().toString();
					break;
				case 2:	//2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
					sOutList = sOutList +  "," + new File(lsOut[z]).getPath().toString();
					break;
				case 3:	//3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication	
					sOutList = sOutList + "," + convertScriptPathToRFTPath(new File(lsOut[z]).getPath().toString());
					break;
				case 4: //4 = Gets script name without extension i.e. BVT_Authentication
					sOutList = sOutList +  "," + removeFileExtension(new File(lsOut[z]).getName().toString(),".java");
					break;
				default:
					sOutList = sOutList + "," + new File(lsOut[z]).getName().toString();
				break;
				}
			}

			//Log.logDebug(sScriptsList);
			String[] lsOutList = Strings.stringToStringArray(sOutList,",");
			return lsOutList;		
		}



		/**
		 * Extracts and returns specified line item from specified file
		 * @param sLineItem - partial text of line item (i.e. "Script Name = ")
		 * @return sLineItem - complete line (i.e. Script Name = MyTest1.java")
		 */public static String getLineItemFromFile(String sLineItem, String sFile)
		 {

			 String sTmp = "";

			 sTmp = FileIO.getFileContents(sFile);

			 String[] lsResults = Strings.stringToStringArray(sTmp,Log.gsNewline);

			 for (int x = 0;x < lsResults.length; x++)
			 {
				 if (lsResults[x].indexOf(sLineItem) != -1)
				 {
					 return lsResults[x].trim();
				 }
			 }

			 return "";		

		 }


		 /**
		  * Extracts and returns specified line item from specified block of text
		  * @param sLineItem - partial text of line item (i.e. "Script Name = ")
		  * @param sContent - text block
		  * @return sLineItem - complete line (i.e. Script Name = MyTest1.java")
		  */public static String getLineItemFromBlock(String sLineItem, String sContent)
		  {

			  String[] lsResults = Strings.stringToStringArray(sContent,Log.gsNewline);

			  for (int x = 0;x < lsResults.length; x++)
			  {
				  if (lsResults[x].indexOf(sLineItem) != -1)
				  {
					  return lsResults[x].trim();
				  }
			  }

			  return "";		

		  }



		  /**
		   * Extracts and returns the number of items found in the specified file
		   * @param sLineItem - partial text of the item to find (i.e. "Script Name = ")
		   * @return int - integer containing the number of items found (i.e. returns 3 if "Script Name = " appears 3 times in the specified file)
		   */public static int getCountOfItemsInFile(String sLineItem, String sFile)
		   {

			   int iCount = 0;

			   String sTmp = "";

			   sTmp = FileIO.getFileContents(sFile);

			   String[] lsResults = Strings.stringToStringArray(sTmp,Log.gsNewline);

			   for (int x = 0;x < lsResults.length; x++)
			   {
				   if (lsResults[x].indexOf(sLineItem) != -1)
				   {
					   iCount++;
				   }
			   }

			   return iCount;		

		   } 


		   /**
		    * Extracts and returns the number of items found in the specified string
		    * @param sLineItem - partial text of the item to find (i.e. "Script Name = ")
		    * @return int - integer containing the number of items found (i.e. returns 3 if "Script Name = " appears 3 times in the specified string)
		    */
		   public static int getCountOfItemsInString(String sLineItem,String sBlock)
		   {

			   int iCount = 0;

			   String sTmp = "";

			   sTmp = sBlock;

			   String[] lsResults = Strings.stringToStringArray(sTmp,Log.gsNewline);

			   for (int x = 0;x < lsResults.length; x++)
			   {
				   if (lsResults[x].indexOf(sLineItem) != -1)
				   {
					   iCount++;
				   }
			   }

			   return iCount;		

		   } 





		   /**
		    * prints the line items found in the specified file
		    * @param sLineItem - partial text of the item to find (i.e. "Script Name = ")
		    * @return String[] - String containing the list of items found items found (i.e. returns  {Testcase="My case 1", Testcase="My case 2"} 
		    * if "Testcase=" is the line item searched for in the specified file)
		    */
		   public static void printListOfItemsInFile(String sLineItem, String sFile)
		   {
			   String sTmp = "";
			   //ArrayList lst = null;
			   sTmp = FileIO.getFileContents(sFile);

			   //Log.logDebug(sTmp);

			   String[] lsResults = Strings.stringToStringArray(sTmp,Log.gsNewline);

			   for (int x = 0;x < lsResults.length; x++)
			   {

				   //Log.logDebug(lsResults[x]);

				   if (lsResults[x].indexOf(sLineItem) != -1)
				   {
					  Log.logScriptInfo("*************  " + lsResults[x]);
				   }
			   }				
		   }	 



		   /**
		    * Extracts and returns the items found in the specified file
		    * @param sLineItem - partial text of the item to find (i.e. "Script Name = ")
		    * @return String[] - String containing the list of items found items found (i.e. returns  {Testcase="My case 1", Testcase="My case 2"} 
		    * if "Testcase=" is the line item searched for in the specified file)
		    */
		   public static String[] getListOfItemsInFile(String sLineItem, String sFile)
		   {
			   String sTmp = "";
			   sTmp = FileIO.getFileContents(sFile);

			   //Log.logDebug(sTmp);

			   String[] lsResults = Strings.stringToStringArray(sTmp,Log.gsNewline);

			   //Log.logDebug("Length: " + lsResults.length);

			   String[] lst = new String[getCountOfItemsInFile(sLineItem,sFile)];

			   int z = 0;
			   for (int x = 0;x < lsResults.length; x++)
			   {

				   //Log.logDebug(lsResults[x]);

				   if (lsResults[x].indexOf(sLineItem) != -1)
				   {
					   lst[z]=(lsResults[x]);
					   z++;
				   }
			   }


			   return lst;
		   }	 		 




		   /**
		    * prints all scripts and script information from the given script directory in 
		    * a semicolon deleimted format 
		    */
		   public static void printLWPScriptLibraryDetails(String sScriptLibDir)
		   {
			   String sDesc = "";
			   String sTA = "";
			   String sAuthor = "";
			   int iNumOfTestCases = 0;
			   int iNumOfScripts = 0;
			   int iTotalTCCount = 0;

			   File fDirs = new File(sScriptLibDir);
			   ArrayList<File> Dirs = getDirListing(fDirs);
			   //String sScriptsList = "";

			   //if there is only one directory, the parent directory, then set it here
			   if (Dirs.size() < 1)
			   {
				   Dirs.add(fDirs);
			   }

			   //otherwise cycle through all sub directories
			   for (int y = 0; y < Dirs.size();y++)
			   {			
				   File fDir = new File(Dirs.get(y).toString());	

				   File lsScripts[] = fDir.listFiles();

				   String sDirectory = Dirs.get(y).toString();

				   String sComponentArea = new File(sDirectory).getName();

				   //Log.logDebug("================================================================");
				   //Log.logDebug(sDirectory);
				   //Log.logDebug("================================================================");

				   int z=0;
				   for (int x=0;x < lsScripts.length;x++)
				   {
					   try{
						   if (lsScripts[x].isFile())
						   {

							   if (lsScripts[x].toString().indexOf(".java") != -1 && lsScripts[x].toString().indexOf(".java.contrib") == -1)
							   {	


								   z++;	//num of scripts in each component

								   iNumOfScripts++; //script counter - total

								   //Log.logDebug(lsScripts[x].toString());


								   sDesc = getLineItemFromFile("gsScriptDescription", lsScripts[x].toString());
								   //sDesc = sDesc.substring(23,sDesc.length()-2);
								   sTA = getLineItemFromFile("gsScriptTestArea", lsScripts[x].toString());
								   //sTA = sTA.substring(20,sTA.length() -2);
								   sAuthor = getLineItemFromFile("gsScriptAuthor", lsScripts[x].toString());
								   //sAuthor = sAuthor.substring(17,sAuthor.length() -2);
								   iNumOfTestCases = getCountOfItemsInFile("startTestCase", lsScripts[x].toString());


								   iTotalTCCount = iTotalTCCount + iNumOfTestCases; 

								   //					print script info out to console
								  Log.logScriptInfo(sComponentArea + " ; " + z + " ; " + lsScripts[x].getName().toString() + " ; " + sDesc + " ; " + sTA + " ; " + sAuthor + " ; " + iNumOfTestCases + " ; " + sDirectory);

								   //print testcase associated with above script
								   printListOfItemsInFile("startTestCase", lsScripts[x].toString());



							   }


						   }

					   }
					   catch(Exception e){

						   e.getMessage();
					   }


				   }

			   }


			  Log.logScriptInfo("Total Number of Scripts in Datastore: " + iNumOfScripts);
			  Log.logScriptInfo("Total Number of Testcases in Datastore: " + iTotalTCCount);
		   }


		   /**
		    * prints all scripts from the given script directory.
		    * @param sScriptLibDir - the path of the script directory i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\"
		    */
		   public static void printScriptList(String sScriptLibDir)
		   {

			   String[] lsScripts = getScriptLibrary(sScriptLibDir);

			   for (int x=0;x < lsScripts.length;x++)
			   {
				  Log.logScriptInfo(lsScripts[x]);
			   }

		   }


		   /**
		    * prints all scripts from the given script directory.
		    * @param sScriptLibDir - the path of the script directory i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\"
		    * @param sSearchBy - text to search for in file name. i.e. "BVT_", "GVT", "teamspace", etc.
		    */
		   public static void printScriptList(String sScriptLibDir, String sSearchBy)
		   {

			   String[] lsScripts = getScriptLibrary(sScriptLibDir,sSearchBy);

			   for (int x=0;x < lsScripts.length;x++)
			   {
				  Log.logScriptInfo(lsScripts[x]);
			   }

		   }


		   /**
		    * prints all scripts by the specified script author.
		    */
		   public static void printScriptListByAuthor(String sScriptLibDir,String sAuthor)
		   {

			   String[] lsScripts = getScriptLibraryByAuthor(sScriptLibDir,sAuthor);

			   for (int x=0;x < lsScripts.length;x++)
			   {
				  Log.logScriptInfo(lsScripts[x]);
			   }

		   }



		   /**
		    * prints all scripts from the given script directory.
		    * @param sScriptLibDir - the path of the script directory i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\"
		    * @param sSearchBy - text to search for in file name. i.e. "BVT_", "GVT", "teamspace", etc.
		    * @param iFormat - integer corresponding to different file formats:
		    * 	1 = Gets script name only -i.e.  BVT_Authentication.java
		    * 	2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
		    *   3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication
		    * 	4 = Gets script name with extension i.e. BVT_Authentication 
		    */
		   public static void printScriptList(String sScriptLibDir, String sSearchBy, int iFormat)
		   {

			   String[] lsScripts = getScriptLibrary(sScriptLibDir,sSearchBy,iFormat);

			   for (int x=0;x < lsScripts.length;x++)
			   {
				  Log.logScriptInfo(lsScripts[x]);
			   }

		   }


		   /**
		    * prints all scripts from the given script directory and formats the output in one of many ways.
		    * @param sScriptLibDir - the path of the script directory i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\"
		    * @param iFormat - integer corresponding to different file formats:
		    * 	1 = Gets script name only -i.e.  BVT_Authentication.java
		    * 	2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
		    *   3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication
		    * 	4 = Gets script name with extension i.e. BVT_Authentication
		    */
		   public static void printScriptList(String sScriptLibDir, int iFormat)
		   {

			   String[] lsScripts = getScriptLibrary(sScriptLibDir,iFormat);

			   for (int x=0;x < lsScripts.length;x++)
			   {
				  Log.logScriptInfo(lsScripts[x],3);
				   
			   }

		   }


		   /**
		    * Returns script name in the given script name format.
		    * @param sScript - The script name i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java"
		    * @param iFormat - integer corresponding to different file formats:
		    * 	1 = Gets script name only -i.e.  BVT_Authentication.java
		    * 	2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
		    *   3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication
		    * 	4 = Gets script name without file extension i.e. BVT_Authentication
		    */
		   public static String getScriptName(String sScript, int iFormat)
		   {
			   //get script file name from complete path string
			   File f = new File(sScript);
			   //String sOut = "";

			   switch (iFormat)
			   {
			   case 1: //1 = Gets script name only -i.e.  BVT_Authentication.java
				   return f.getName();
			   case 2:	//2 = Gets script name with path i.e. c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java
				   return f.getPath();
			   case 3:	//3 = Gets script name with RFT pathing i.e. testcases.wcs.authentication.BVT_Authentication	
				   return convertScriptPathToRFTPath(f.getPath());
			   case 4: //4 = Gets script name without extension i.e. BVT_Authentication
				   return removeFileExtension(f.getName(),".java");			
			   default:
				   return f.getName();

			   }		
		   }



		   /**
		    * Returns script category of a given script.
		    * @param sScript - The script name i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java"
		    */
		   public static String getScriptCategory(String sScript)
		   {
			   String sName = getScriptName(sScript, 3);

			   return parseCategory(sName);	
		   }

		   /**
		    * Gets Category by parsing out "testcases." portion of RFT script path
		    * @param sCategory
		    * @return
		    */
		   public static String parseCategory(String sCategory)
		   {
			   String sOut = "";

			   //remove "testcases." prefix from MainFunction string
			   sOut = Strings.replace(sCategory,"testcases.","");

			   int i = sOut.indexOf(".");

			   //replace "." separators with "\" separators
			   sOut = sOut.substring(0,i);

			   //set to proper case
			   sOut = Strings.toProperCase(sOut);

			   return sOut;
		   }

		   /**
		    * Returns script description of a given script.
		    * @param sScript - The script name i.e. "c:\\auto_dir\\LWPServerGUI\\LWP\\testcases\\authentication\\BVT_Authentication.java"
		    */
		   public static String getScriptDescription(String sScript)
		   {
			   String sDQ = "\"";

			   //Log.logDebug(sScript);

			   String sDesc = getLineItemFromFile("gsScriptDescription",sScript);

			   //Log.logDebug(sDesc);

			   //remove gsScriptDescription text from string
			   sDesc = Strings.replace(sDesc,"gsScriptDescription","");

			   //Log.logDebug(sDesc);

			   //remove "=" from script description string
			   sDesc = Strings.replace(sDesc,"=","");

			   //Log.logDebug(sDesc);

			   //remove ";"
			   sDesc = Strings.replaceLast(sDesc,";","");


			   //Log.logDebug(sDesc);
			   //remove double quotes from script description
			   sDesc = Strings.replace(sDesc,sDQ,"");

			   //Log.logDebug(sDesc.trim());

			   return sDesc.trim();
		   }




		  


		   /** Parses text file to get list of testcases to run
		    *  File format uses # as first character for comment lines
		    *  Line starting with CYCLE= contains name of test cycle or identifier for future use entering results into test doc datastore
		    * @param sFileName String - path to file.  If only filename is specified, default location is user's home directory
		    * @return array of String containing list of scripts to run, first item will be "ERROR" if error occurs in parseScriptNames
		    * @author jstaruch
		    * @since 2009-11-20
		    */
		   public String[] parseScriptNames(String sFileName, boolean bDebug){
			   String[] asNull = {"ERROR", ""};
			   String sFirst = "";
			   String sCycle = "";
			   String sCYCLE="CYCLE";
			   String sLine = "";
			   String sCycleID = "";
			   int iTestLineIndex = 0;

		
			   if (bDebug){
				  Log.logDebugInfo("starting parseScriptNames - script is "+sFileName);
			   }
			   //Confirm filename is not empty
			   if (sFileName.length()<1){
				   asNull[1] = "Filename too short to be real - looking for file "+sFileName;
				   return asNull;
			   }
			   
			   //Get path for filename - if only file name provided, default to home directory
			   if (sFileName.indexOf(File.separator)<0){
				   sFileName = System.getProperty("user.home")+File.separator+sFileName;
				   Log.logScriptInfo("File location defaulting to user home  ["+System.getProperty("user.home")+"] directory");
			   }

			   //Confirm file exists
			   File fTxt = new File(sFileName);
			   if (!fTxt.exists()){
				   asNull[1]="File not found - looking for file "+sFileName;
				   return asNull;
			   }

			   //Get contents of file
			   String[] asFileContent =    FileIO.getFileContentsAsList(sFileName);
			   int iLength = asFileContent.length;

			   //Confirm file has contents
			   if (iLength == 0){
				   asNull[1]=("File was empty - using file "+sFileName);
				   return asNull;
			   }

			   //Compute number of testcases in file - do not count comment (#) lines or cycle (CYCLE) line
			   //create array to hold indices of lines with tests, initialize to -1
			   int [] iTestLines = new int[iLength];
			   for (int i = 0; i<iLength;i++){iTestLines[i] = -1;}

			   //Parse text file to identify cycle & count number of testcases
			   for (int i=0;i<iLength;i++){
				   sLine = asFileContent[i];

				   if (sLine.length()>1){sFirst = sLine.substring(0,1);
				   }else {sFirst = "#"; //default to comment for empty line
				   }

				   if (sLine.length()>5){sCycle = sLine.substring(0,5);
				   }else {sCycle = " "; //default to not CYCLE if line not long enough
				   }

				   if (sFirst.equals("#")){//Log.logInfo("Comment line found "+sLine;
				   }else if (sCycle.equals(sCYCLE)){
					   //cycle identifier
					   sCycleID = sLine.substring(sLine.indexOf("=")+1);
					   Log.logScriptInfo("Cycle id is "+sCycleID);				
				   }else {//neither comment nor cycle, must be testcase - add index to iTestLines
					   iTestLines[iTestLineIndex]=i;				
					   iTestLineIndex ++;
				   }
			   }
			   //Log.logInfo("Test Line Count = "+iTestLineIndex);

			   //File has been parsed - build array of testcase lines
			   String[] sTestCases = new String [iTestLineIndex];
			   for (int i = 0; i<sTestCases.length;i++){
				   sTestCases[i]=asFileContent[iTestLines[i]];
			   }
			   
			   if (bDebug){
				  Log.logDebugInfo("Cycle id= "+sCycleID);
			   		for (int i = 0; i<sTestCases.length;i++){
			   			Log.logDebugInfo("Testcase #"+i+": "+sTestCases[i]);
			   		}
			   }
			   return sTestCases;

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







