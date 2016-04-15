package dummy.core.libs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;



/**
 * @Class Strings
 * @Description: Contains library of generic, useful string functions
 * @Since:  2011/07/22
 * @Author: avenditti
 */
public class Strings
{

	/**
	 * Global String convenience variables to simplify use of characters that require escape sequencing in Java
	 * @example instead of having to remember the escape char sequence you can use:  sDQ + "My Test" + sDQ
	 */
	public static String sDQ = "\"";  //variable to simplify use of inserting double quote char in strings
	public static String sSQ = "\'";  //variable to simplify use of inserting single quote char in strings
	public static String sNL = "\n";  //variable to simplify use of inserting next line char in strings
	public static String sTab = "\t"; //variable to simplify use of inserting a tab in a text string.
	public static String sCR = "\r";  //variable to simplify use of inserting a carriage return in the text at this point.
	
	


	/**
	* Removes whitespace from a specified string <p>
	* @param s
	* @Parameter:String s - string to remove whitespace from
	* @return String - string based on original specified string except without whitespace
	* 		 (i.e. string "I went out" is returned as "Iwentout"
	*/

	public static String removeWhiteSpace(String s)
	{
		StringTokenizer st = new StringTokenizer(s);
		int index = st.countTokens();

		StringBuffer str = new StringBuffer();

		for (int i = 0; i < index; i++)
		{
			str.append(st.nextToken().trim());
		}

		return str.toString();
	}

	
	/**
	* Removes invalid characters from a specified string <p>
	* @param s
	* @Parameter:String s - string to remove invalid characters from
	* @Parameter:String[] lsInvalidChars - list of invalid characters to remove from the original string (s) i.e. {"_","-","@","#","$","%","^","&","*","(",")","+","=","!"}
	* @Example:	removeInvalidChars("%I$ went &out@",lsInvalidChars) using lsInvalidChars list -  {"_","-","@","#","$","%","^","&","*","(",")","+","=","!"}
	* @return	String - returns string based on the originally specified string except without specified invalid characters (i.e. string "%I$ went &out@" is returned as "I went out"
	*/

	public static String removeInvalidChars(String s,String[] lsInvalidChars)
	{
		String sOutput = s;
		
		for (int x = 0; x < lsInvalidChars.length;x++){
			sOutput = replace(sOutput,lsInvalidChars[x],"");
		}
		
		return sOutput;
	}

	
	
	
	

	/**
	* Converts a specified string to a string array using default space delimeter<p>
	* @param s
	* @Parameter: String s - string to convert to string array
	* @return	String[] - the string array (i.e. string "one two three four" is returned as String [] = {"one","two","three","four"}
	*/

	public static String[] stringToStringArray(String s)
	{
		return stringToStringArray(s, " ");
	}



	/**
	* Converts a specified string to a string array using specified delimiter<p>
	 * @param s
	 * @param sDelim
	* @Parameter: 			String s - string to convert to string array
	* @return			String[] - the string array (i.e. string "one, two, three, four," is returned as String [] = {"one","two","three","four"}
	*/

	public static String[] stringToStringArray(String s, String sDelim)
	{
		String[] lines = s.split(sDelim);
		return lines;
	}


	/**
	* Strips whitespace character (160) from output
	 * @param s
	* @Parameter: String - String to strip ascii 160 characters from
	* @return String - Returns the original string stripped of any ascii 160 characters
	*/

	public static String stripWSChar(String s)
	{
		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < s.length(); x++)
		{
			//System.out.println("Character: " + s.charAt(x));

			char c = s.charAt(x);
			int i = c;

			//System.out.println(c + " ASCII Value: " + i);

			if (i != 160)
			{
				sb.append(c);
			}

		}

		return sb.toString();
	}


//	 ---------------------------------------------------------------- replace

	/**
	 * Replaces the occurences of a certain pattern in a string with a
	 * replacement String. This is the fastest replace function known to author.
	 * @param s
	 * @param sub
	 * @param with
	 *
	 * @Parameter: s      the string to be inspected
	 * @Parameter: sub    the string pattern to be replaced
	 * @Parameter: with   the string that should go where the pattern was
	 *
	 * @return the string with the replacements done
	 */
	public static String replace(String s, String sub, String with) {
		if ((s == null) || (sub == null) || (with == null)) {
			return s;
		}
		int c = 0;
		int i = s.indexOf(sub, c);
		if (i == -1) {
			return s;
		}
		StringBuffer buf = new StringBuffer(s.length() + with.length());
		do {
			 buf.append(s.substring(c,i));
			 buf.append(with);
			 c = i + sub.length();
		 } while ((i = s.indexOf(sub, c)) != -1);
		 if (c < s.length()) {
			 buf.append(s.substring(c,s.length()));
		 }
		 return buf.toString();
	}

	/**
	 * Character replacement in a string. All occurrencies of a character will be
	 * replaces.
	 * @param s
	 * @param sub
	 * @param with
	 *
	 * @Parameter: s      input string
	 * @Parameter: sub    character to replace
	 * @Parameter: with   character to replace with
	 *
	 * @return string with replaced characters
	 */
	public static String replace(String s, char sub, char with) {
		if (s == null) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces the very first occurance of a substring with suplied string.
	 * @param s
	 * @param sub
	 * @param with
	 *
	 * @Parameter: s      source string
	 * @Parameter: sub    substring to replace
	 * @Parameter: with   substring to replace with
	 *
	 * @return modified source string
	 */
	public static String replaceFirst(String s, String sub, String with) {
		if ((s == null) || (sub == null) || (with == null)) {
			return s;
		}
		int i = s.indexOf(sub);
		if (i == -1) {
			return s;
		}
		return s.substring(0, i) + with + s.substring(i + sub.length());
	}

	/**
	 * Replaces the very first occurence of a character in a String.
	 * @param s
	 * @param sub
	 * @param with
	 *
	 * @Parameter: s      string
	 * @Parameter: sub    char to replace
	 * @Parameter: with   char to replace with
	 *
	 * @return modified string
	 */
	public static String replaceFirst(String s, char sub, char with) {
		if (s == null) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == sub) {
				str[i] = with;
				break;
			}
		}
		return new String(str);
	}

	/**
	 * Replaces the very last  occurance of a substring with suplied string.
	 * @param s
	 * @param sub
	 * @param with
	 *
	 * @Parameter: s      source string
	 * @Parameter: sub    substring to replace
	 * @Parameter: with   substring to replace with
	 *
	 * @return modified source string
	 */
	public static String replaceLast(String s, String sub, String with) {
		if ((s == null) || (sub == null) || (with == null)) {
			return s;
		}
		int i = s.lastIndexOf(sub);
		if (i == -1) {
			return s;
		}
		return s.substring(0, i) + with + s.substring(i + sub.length());
	}

	/**
	 * Replaces the very last occurence of a character in a String.
	 * @param s
	 * @param sub
	 * @param with
	 *
	 * @Parameter: s      string
	 * @Parameter: sub    char to replace
	 * @Parameter: with   char to replace with
	 *
	 * @return modified string
	 */
	public static String replaceLast(String s, char sub, char with) {
		if (s == null) {
			return s;
		}
		char[] str = s.toCharArray();
		for (int i = str.length - 1; i >= 0; i--) {
			if (str[i] == sub) {
				str[i] = with;
				break;
			}
		}
		return new String(str);
	}

	/**
	 * Determines if a string is empty. If string is NULL, it returns true.
	 * @param s
	 *
	 * @Parameter: s      string
	 *
	 * @return true if string is empty or null.
	 */
	public static boolean isEmpty(String s) {
		if (s != null) {
			return (s.length() == 0);
		}
		return true;
	}

	// ---------------------------------------------------------------- split

	/**
	 * Splits a string in several parts (tokens) that are separated by delimeter.
	 * Delimeter is <b>always</b> surrounded by two strings! If there is no
	 * content between two delimeters, empty string will be returned for that
	 * token. Therefore, the length of the returned array will always be:
	 * #delimeters + 1.<br><br>
	 *
	 * Method is much, much faster then regexp <code>String.split()</code>,
	 * and a bit faster then <code>StringTokenizer</code>.
	 * @param src
	 * @param delimeter
	 *
	 * @Parameter: src       string to split
	 * @Parameter: delimeter split delimeter
	 *
	 * @return array of splitted strings
	 */
	public static String[] split(String src, String delimeter) {
		if (src == null) {
			return null;
		}
		if (delimeter == null) {
			return new String[] {src};
		}
		int maxparts = (src.length() / delimeter.length()) + 2;		// one more for the last
		int[] positions = new int[maxparts];
		int dellen = delimeter.length();

		int i = 0, j = 0;
		int count = 0;
		positions[0] = - dellen;
		while ((i = src.indexOf(delimeter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = src.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	// ---------------------------------------------------------------- count substrings

	/**
	 * Count substring occurences in a source string.
	 * @param source
	 * @param sub
	 *
	 * @Parameter: source	source string
	 * @Parameter: sub		substring to count
	 * @return			number of substring occurences
	 */
	public static int count(String source, String sub) {
		int count = 0;
		int i = 0, j = 0;
		while (true) {
			i = source.indexOf(sub, j);
			if (i == -1) {
				break;
			}
			count++;
			j = i + sub.length();
		}
		return count;
	}

	/**
	 * Repeats a specified string a specified number of times
	 * @param s
	 * @param iRepeat
	 * @Parameter: s - string to repeat
	 * @Parameter: iRepeat - number of times to repeat string.
	 * repeat("*",7); would return "*******"
	 * @return
	 */
	public static String repeat(String s, int iRepeat)
	{
	StringBuffer buf = new StringBuffer();
	for (int i=0;i<iRepeat;i++)
	{
		buf.append(s);
	}
	return buf.toString();
	}


	/**
	 * Returns specified string in proper case
	 * @param s
	 * @return
	 * @Parameter: s - string to convert to proper case (first character is initialized)
	 */
	public static String toProperCase(String s)
	{
	String sInitChar = s.substring(0,1); //get first char
	String sCapitalizedChar = sInitChar.toUpperCase();  //capitalize the first char
	String sOutStr = Strings.replaceFirst(s,sInitChar,sCapitalizedChar); //replace the existing char with the capitalized one
	return sOutStr; //return the string in proper case
	}




    /**
    * Returns the result of a text check on a string-- checks for strings being equal;  no VP performed
    * @Parameter: firstString string being checked in
    * @Parameter: secondString pattern being checked for
    * @return true if strings equal, false if not
    * 
     * @param firstString
     * @param secondString
    * */
    public static boolean doStringsMatch(String firstString, String secondString) {
        return firstString.equals(secondString);
    }


    /**
    * Returns the result of a text check on a string- checks for pattern occurring in a string; no VP perf.
    * @Parameter: searchString string being checked in
    * @Parameter: subString pattern being checked for
    * @return true if present, false if not
    * 
     * @param searchString
     * @param substring
    * */
    public static boolean isSubstring(String searchString, String substring) {
    	 Pattern p = Pattern.compile(substring);
    	 return p.matcher(searchString).matches();
//    	 Matcher m = p.matcher(searchString);
//    	 return m.matches();

//    	Regex r = new Regex(substring);
//        return r.matches(searchString);
    }

    /**
    * Returns the string with the space characters "fixed"-- this was coded for difficulties with weird spaces
    *     screwing up string matching
    * @Parameter: s string being fixed
    * @return string with fixed space characters
    * 
     * @param s
    * */
    public static String fixString(String s) {
        String t = "";
        for (int i = 0; i < s.length(); i++) {
            if (((int) s.charAt(i)) == 160) {
                t += (char) 32;
            }
            else {
                t += s.charAt(i);
            }
        }
        return t;
    }

    /**
    * Prints the character values for 2 strings
    * @Parameter: s1 1st string
    * @Parameter: s2 2nd string
    * 
     * @param s1
     * @param s2
    * */
    public static void printCharVals(String s1, String s2) {
        for (int i = 0; i < s1.length(); i++) {
            System.out.print("'" + s1.charAt(i) + "' == '" + (int) s1.charAt(i) + "'");
            System.out.print(":");
            System.out.println("'" + (int) s2.charAt(i) + "' == '" + s2.charAt(i) + "'");
        }
    }
    
    
    /**
     * Prints the content of the given String array to the console.
     * For example:
     * String[] args = {"One", "Two", "Three"}
     * call to printArray(args); results in the following console output
     * One
     * Two
     * Three 
     * <p>
     * @param String[] string array to print content of
     * 
     */
     public static void printArray(String[] sArray) {
         for (int i = 0; i < sArray.length; i++) {
                         
 			ATUReports.add(sArray[i], LogAs.PASSED, null);   
         }
     }
    


    /**
     * After replacing white space in strings, see if the target string contains the substring.
     * This is helpful when some web applications format test with non breaking spaces.
     * @param sTarget
     * @param sSub
     * @Parameter: sTarget
     * @Parameter: sSub
     * @return
     */
    public static boolean contains(String sTarget, String sSub){
    	sTarget=replaceWhiteSpace(sTarget);
    	sSub=replaceWhiteSpace(sSub);

    	return sTarget.contains(sSub);
    }


    /**
	 * Replaces the white space character (\u00a0, 160) with space character
     * @param s
	 * @Parameter: s string to replace white space
	 * @return Reformatted string
	 */
	public static String replaceWhiteSpace(String s) {
		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < s.length(); x++)
		{
			//System.out.println("Character: " + s.charAt(x));

			char c = s.charAt(x);
			int i = c;

			//System.out.println(c + " ASCII Value: " + i);

			if (i != 160)
			{
				sb.append(c);
			} else {
				sb.append(' ');
			}

		}

		return sb.toString();
	}

	
	/**
	 * removeDuplicates - removes duplicate line items from a string array list and returns a string array list of all unique line items
	 * @param sInput - String[] array to remove duplicates from
	 * @return String[] array with duplicates removed
	 */
	public static String[] removeDuplicates(String[] sInput){
	    HashSet<String> set = new HashSet<String>();
	    ArrayList<String> ret = new ArrayList<String>();
	    for(int i = sInput.length - 1; i >= 0; i--){
	        String currString = sInput[i].toLowerCase();
	        char currStringArray[] = currString.toCharArray();
	        Arrays.sort(currStringArray);
	        String sortedString = Arrays.toString(currStringArray);
	        if(!set.contains(sortedString)){
	            ret.add(sInput[i]);
	            set.add(sortedString);
	        }
	    }
	    Collections.reverse(ret);
	    return ret.toArray(new String[ret.size()]);
	}
	
	/**
	 * removeElementsFromList - Removes elements from an original list within a String Array
	 * and returns the original list as a String array without elements specified in the elementsToRemove argument.
	 * For example, if the origList contains {"one","two,"three","four"} and the elementsToReove list contains {"two","three"}
	 * the result of this method will be a String array list containing {"one", "four"}
	 * @param - String[] origList - Original list to remove items from
	 * @param - String[] elementsToRemove - items within a string array to remove from the original list
	 * @return - String[] containing the original list without the elements specified in the elementsToReove list
	 * @author - Tony Venditti
	 */
	public static String[] removeElementsFromList(String[] origList, String[] elementsToRemove){
		List<String> oList = new ArrayList<String>(Arrays.asList(origList));
		List<String> rList = new ArrayList<String>(Arrays.asList(elementsToRemove));
		
		for (int i = 0; i < oList.size(); i++) 
		{
			for (int x = 0; x < rList.size(); x++)
			{
				if (oList.get(i).indexOf(rList.get(x)) != -1)
						oList.remove(i); 
			}
		}
		
		String[] output = stringToStringArray(oList.toString(),",");
		return output;
	}
	

}
