package dummy.core.libs;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Class: Randomize
 * @Description: The Randomize class contains general randomization functions
 */
public class Randomize
{
	
/** 
* Generates String containing unique random date and time-based value.
* For example, if the current date and time is 10/06/2006 11:06:52am
* This method will return a string equal to "1006110652". <p>
* @return a String containing a date-based unique value
*/

public static String genDateBasedRandVal() {
	//generates String containing unique random date-based value	
	String s;
	Date d = new Date();
	//long l;

	//format
	SimpleDateFormat tmformat = new SimpleDateFormat("MMddHHmmss");

	//l = d.getTime();
	s = tmformat.format(d);

	return s;
}



/** 
* Generates String containing unique random date-based value of a specified number of characters	 <p>
 * @param i 
* @Parameter: int i - number of characters to limit output to
* @return a String containing a date-based unique value
*/

public static String genDateBasedRandVal(int i) {
	//generates String containing unique random date-based value	
	String s;
	Date d = new Date();
	//long l;

	//format
	SimpleDateFormat tmformat = new SimpleDateFormat("MMddHHmmss");

	//l = d.getTime();
	s = tmformat.format(d);

	
	int ilen = s.length();
	
	return s.substring(ilen-i);
	
}

/** 
* Generates String containing unique  date-based on specified format <p>
 * @param Format 
* @Parameter: String Format - M/d/y or MM/dd/yyyy or dd/MM/yyyy or any valid combination(specify Date and Year in lowercase)
* @return a String containing a date-based on specified format
*/

public static String genDateBasedonFormat(String Format) {
	//generates String containing unique random date-based value	
	String s;
	Date d = new Date();
	
	//format
	SimpleDateFormat tmformat = new SimpleDateFormat(Format);

	//l = d.getTime();
	s = tmformat.format(d);
	
	return s;
	
}


/**
 * Generates a random alphanumeric string of the size specified excluding any characters in the exclude string
 * @param length 
 * @param exclude 
 * @Parameter: length  			The length of the string that will be returned
 * @Parameter: exclude			A case-sensitive String of characters to exclude eg("Jimjones")
 * @return String			A random String of size length excluding any specified chars
 */
public static String genAlphaNumericExcluding(int length, String exclude) {

	StringBuffer sb = new StringBuffer();
	boolean add = true;
	for (int n = 0; n < length; n++) {
		String next = charset[(int) (Math.random() * 62)];
		if (exclude.length() > 0) {

			for (int j = 0; j < exclude.length() && add; j++) {
				if (next.equals(exclude.substring(j, j + 1))) {
					//System.out.println(next + "=" + exclude.substring(j, j + 1) + " on element " + n);
					add = false;
					n--;
				} else
					add = true;

			}
			if (add)
				sb = sb.append(next);
			add = true;
		} else
			sb = sb.append(next);
	}
	return sb.toString();
}

/**
 * Generates a random alphanumeric string of some length between the min and max lengths specified
 * @param miniumLength 
 * @param maximumLength 
 * @Parameter: minimumLength		The minimum length of the string that will be returned
 * @Parameter: maximumLength 	The maximum length of the string that will be returned
 * @return String			A random String of length no less than min and no more than max
 */
public static String genAlphaNumeric(int miniumLength, int maximumLength) {
	int rndSize = generateRandomInt(miniumLength, maximumLength);
	return genAlphaNumericExcluding(rndSize, "");
}

/**
* Generates a random alpha string of some length between the min and max lengths specified
 * @param miniumLength 
 * @param maximumLength 
* @Parameter: minimumLength		The minimum length of the string that will be returned
* @Parameter: maximumLength 	The maximum length of the string that will be returned
* @return String			A random String of length no less than min and no more than max
*/
public static String genAlpha(int miniumLength, int maximumLength) {
	int rndSize = generateRandomInt(miniumLength, maximumLength);
	return genAlphaNumericExcluding(rndSize, "1234567890");
}

/**
* Generates a random alphanumeric string of some length between the min and max lengths specified excluding any characters in the exclude string
 * @param miniumLength 
 * @param maximumLength 
 * @param exclude 
* @Parameter: minimumLength		The minimum length of the string that will be returned
* @Parameter: maximumLength 	The maximum length of the string that will be returned
* @Parameter: exclude			A case-sensitive String of characters to exclude eg("Jimjones")
* @return String			A random String of length no less than min and no more than max excluding any specified chars
* */
public static String genAlphaNumericExcluding(int miniumLength, int maximumLength, String exclude) {
	int rndSize = generateRandomInt(miniumLength, maximumLength);
	return genAlphaNumericExcluding(rndSize, exclude);
}

/**
* Generates a random alpha string of some length between the min and max lengths specified excluding any characters in the exclude string
 * @param miniumLength 
 * @param maximumLength 
 * @param exclude 
* @Parameter: minimumLength		The minimum length of the string that will be returned
* @Parameter: maximumLength 	The maximum length of the string that will be returned
* @Parameter: exclude			A case-sensitive String of characters to exclude eg("Jimjones")
* @return String			A random String of length no less than min and no more than max excluding any specified chars
* */
public static String genAlphaExcluding(int miniumLength, int maximumLength, String exclude) {
	int rndSize = generateRandomInt(miniumLength, maximumLength);
	return genAlphaNumericExcluding(rndSize, exclude.concat("1234567890"));
}
/**
* Generates a random numeric string of some length between the min and max lengths specified excluding any characters in the exclude string
 * @param miniumLength 
 * @param maximumLength 
* @Parameter: minimumLength		The minimum length of the string that will be returned
* @Parameter: maximumLength 	The maximum length of the string that will be returned
* @return String			A random String of length no less than min and no more than max excluding any specified chars
* */
public static String genNumeric(int miniumLength, int maximumLength) {
	int rndSize = generateRandomInt(miniumLength, maximumLength);
	return genAlphaNumericExcluding(rndSize, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
}


/**
* Generates a random numeric string of some length between the min and max lengths specified excluding any characters in the exclude string
 * @param miniumLength 
 * @param maximumLength 
 * @param exclude 
* @Parameter: minimumLength		The minimum length of the string that will be returned
* @Parameter: maximumLength 	The maximum length of the string that will be returned
* @Parameter: exclude			A case-sensitive String of characters to exclude eg("Jimjones")
* @return String			A random String of length no less than min and no more than max excluding any specified chars
* */
public static String genNumericExcluding(int miniumLength, int maximumLength, String exclude) {
	int rndSize = generateRandomInt(miniumLength, maximumLength);
	return genAlphaNumericExcluding(rndSize, exclude.concat("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
}

/**
* Generates a random integer between 0 and maximumNumber
 * @param maximumNumber 
* @Parameter: maximumNumber				The highest number that will be returned
* @return int						A random number between 0 and maximumNumber	
* */

public static int generateRandomInt(int maximumNumber) {
	return (int) Math.round((Math.random()) * maximumNumber);
}

/**
* Generates a random integer between minimumNumber and maximumNumber
* @Parameter: minimumNumber				The lowest number that will be returned
* @Parameter: maximumNumber				The highest number that will be returned
* @return int						A random number between minimumNumber and maximumNumber	
* @author jamesjon
 * @param minimumNumber 
 * @param maximumNumber 
*/
public static int generateRandomInt(int minimumNumber, int maximumNumber) {
	int num = generateRandomInt(maximumNumber);
	int count = 0;
	while (num < minimumNumber && count < 2) {
		num = generateRandomInt(minimumNumber, maximumNumber);
		count++;
	}
	return num;
}
/**
* Generates a random integer as a String between 0 and maximumNumber
 * @param maximumNumber 
* @Parameter: maximumNumber				The highest number that will be returned
* @return String					A random number between minimumNumber and maximumNumber	
* */
public static String generateRandomIntAsString(int maximumNumber) {
	return new Integer(generateRandomInt(maximumNumber)).toString();
}

/**
* Generates a random integer as a String between minimumNumber and maximumNumber
 * @param minimumNumber 
 * @param maximumNumber 
* @Parameter: minimumNumber				The lowest number that will be returned
* @Parameter: maximumNumber				The highest number that will be returned
* @return String					A random number between minimumNumber and maximumNumber	
* */
public static String generateRandomIntAsString(int minimumNumber, int maximumNumber) {
	return new Integer(generateRandomInt(minimumNumber, maximumNumber)).toString();
}

public static void example() {
	String toExclude = "Jimjones";
	String numExclude = "1357";
	System.out.println("genAlpha(10, 20)\t\t\t\t\t\t" + genAlpha(10, 20));
	System.out.println("genAlphaExcluding(10, 20, \"" + toExclude + ")\t" + genAlphaExcluding(10, 20, toExclude) + "\n");

	System.out.println("genAlphaNumeric(10, 20)\t\t\t\t\t" + genAlphaNumeric(10, 20));
	System.out.println("genAlphaNumericExcluding(10, \"" + toExclude + ")\t" + genAlphaNumericExcluding(10, 20, toExclude) + "\n");

	System.out.println("genNumeric(10, 20)\t\t\t\t\t\t" + genNumeric(10, 20));
	System.out.println("genNumericExcluding(10, \"" + numExclude + ")\t\t\t" + genNumericExcluding(10, 20, numExclude) + "\n");

	System.out.println("generateRandomInt(20)\t\t\t\t\t" + generateRandomInt(20));
	System.out.println("generateRandomInt(100, 200)\t\t\t\t" + generateRandomInt(100, 200) + "\n");
}

	
/** 
 * To obtain a random string between lo and hi number of characters.
 * String of between 10 and 40 characters, you would say: 
 * String s = obj.randomstring(10, 40);
 * @param lo 
 * @param hi 
 * @Parameter: lo the minimum number of characters
 * @Parameter: hi the maximum number of characters
 * @return String the result string
 */
public static String randomString(int lo, int hi)
{
	int n = rand(lo, hi);
	char c[] = new char[n];
	byte b[] = new byte[n];
	for(int z = 0; z < n; z++)
		c[z] = (char)rand('a','z');
		
	for (int i = 0; i < n; i++)
		b[i] = (byte) rand('a', 'z');
	
	return new String(c);
}

/**
 * Actual random numbers are obtained using nextInt(), and then 
 * knocked down to the relevant range using the modulo ("%") operator.
 * @param lo 
 * @param hi 
 * @Parameter: lo min number 
 * @Parameter: hi max number
 * @return int a number between lo and hi
 */
public static int rand(int lo, int hi)
{
	Random rd = new Random();
	int n = hi - lo + 1;
	int i = rd.nextInt() % n;
	if (i < 0)
		i = -i;
	return lo + i;
}

private static String[] charset =
{
	"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l",
	"m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H",
	"I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z" 
};


}
