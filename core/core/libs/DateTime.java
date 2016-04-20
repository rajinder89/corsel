package core.libs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * The DateTime class contains general date and time functions.
 */
public class DateTime {

	/** Global default  date format*/
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	/** Global long for ms diff between GMT and EST */
	public static long glCalOffset = 18000000;

	/** Global long for timer start time */
	public static long glStartTime = 0;


	/**
	 * Sets clock start time
	 * @return clock start time as a long
	 */
	public static long setStartTime() {
		long lTime = System.currentTimeMillis();
		return lTime;
	}

	/**
	 * returns long data type of elapsed time
	 * @param startTime
	 * @return Long containing the elapsed time between the start time specified and the end time.
	 */
	public static long getElapsedTimeLong(long startTime) {
		// end time long format
		long endTime = System.currentTimeMillis();
		// System.out.println("End Time:   " + String.valueOf(endTime));

		// elapsed time long format
		long time = endTime - startTime;
		// System.out.println("Elapsed Time: " + String.valueOf(time));

		return time;
	}

	/**
	 * Gets elapsed time from the specified start time. Returns string in a specified format.
	 * @param startTime the start time as a long
	 * @param sFormat Time format string i.e. "HH:mm:ss:SSS"
	 * @return A string containing the time difference between the script start time and the script elapsed time in a specified time format.
	 */
	public static String getElapsedTime(long startTime, String sFormat) {
		// end time long format
		long endTime = System.currentTimeMillis();
		// System.out.println("End Time:   " + String.valueOf(endTime));

		// elapsed time long format
		long time = endTime - startTime;
		//System.out.println("ElapsedTime: " + endTime + " - " + startTime + " = " + time);

		// Format the Date time information
		//String s = getFormattedDateTime(time, sFormat);
		String s = getFormattedDateTime(time,sFormat);

		return s;
	}


	/**
	 * Gets elapsed time from the specified start time. Returns string in "HH:mm:ss:SSS" format. <p>
	 * @param startTime the starting time
	 * @return A string containing the time difference between the script start time and the script elapsed time.
	 */
	public static String getElapsedTime(long startTime) {
		//end time long format
		long endTime = System.currentTimeMillis();
		//System.out.println("End Time:   " + String.valueOf(endTime));

		//elapsed time long format
		long time = endTime - startTime;
		//System.out.println("Elapsed Time: " + String.valueOf(time));

		//Format the Date time information
		//String s = getFormattedDateTimeForElapsedTime(time, "HH:mm:ss:SSS");
		String s = getFormattedDateTimeForElapsedTime(time);

		return s;
	}


	/**
	 * returns formatted String version of date supplied as long  <p>
	 * @param dDateTime date and time in long data type
	 * @param sDateTimeFormat The string format you would like the date and time to be displayed as. (i.e. "HH:mm:ss:SSS")
	 * @return String containing the formatted date and time of the given long date and time.
	 */
	public static String getFormattedDateTime(long dDateTime, String sDateTimeFormat) {
		SimpleDateFormat tmformat = new SimpleDateFormat(sDateTimeFormat);

		Date tm = new Date(dDateTime);

		String s = tmformat.format(tm);

		return s;
	}


	/**
	 * returns formatted String version of date supplied as long  <p>
	 * @param lElapsedTime date and time in long data type of the elapsed time
	 * @return String containing the formatted date time of the given long datetime elapsed.
	 */
	public static String getFormattedDateTimeForElapsedTime(long lElapsedTime) {
		long hr = TimeUnit.MILLISECONDS.toHours(lElapsedTime);
		long min = TimeUnit.MILLISECONDS.toMinutes(lElapsedTime - TimeUnit.HOURS.toMillis(hr));
		long sec = TimeUnit.MILLISECONDS.toSeconds(lElapsedTime - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
		long ms = TimeUnit.MILLISECONDS.toMillis(lElapsedTime - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) -                   TimeUnit.SECONDS.toMillis(sec));
		return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
	}




	/**
	 * returns a long from a specified Date String
	 * Helpful in making date-based calculations
	 * @param sDate date and time in String data type (i.e. "12/25/2004")
	 * @param sFormat The string format you would like the date and time to be displayed as. (i.e.
	 *             "HH:mm:ss:SSS") 
	 * @return long containing the date time value of the specified date (i.e. getLongFromFormattedDateTime(
	 * "09/21/1964", "MM/dd/yy" ) would return the long value 1135486800000 which can then be used in date-based
	 * calculations.
	 */
	public static long getLongFromFormattedDateTime(String sDate, String sFormat) {
		Date dDate = stringToDate(sDate, sFormat);

		return dDate.getTime();
	}

	/**
	 * returns a string of the current date in MMMM dd, yyyy format
	 * @return a string of the current date in MMMM dd, yyyy format
	 */
	public static String getCurrentDate() {
		// get current date
		long currentDate = System.currentTimeMillis() - glCalOffset;
		String sCurrentDate;

		// Format the current date information to string. Check if day is single or double digit
		String sDay = getFormattedDateTime(currentDate, "dd");
		if (Integer.parseInt(sDay) <= 9)
			sCurrentDate = getFormattedDateTime(currentDate, "MMMM d, yyyy");
		else
			sCurrentDate = getFormattedDateTime(currentDate, "MMMM dd, yyyy");

		// System.out.println(sCurrentDate);

		return sCurrentDate;
	}


	/**
	 * returns a string of the current date in a specified format
	 * @param sFormat The date format to use. (i.e. "MMMM dd, yyyy"=September 21,2006, "MMM yyyy" = "Sep 2006")
	 * @return String of the current date in the specified format
	 */
	public static String getCurrentDate(String sFormat) {
		// get current date
		long currentDate = System.currentTimeMillis() - glCalOffset;
		String sCurrentDate;

		// Format the current date information to string. Check if day is single or double digit
		String sDay = getFormattedDateTime(currentDate, "dd");
		if (Integer.parseInt(sDay) <= 9)
			sCurrentDate = getFormattedDateTime(currentDate, sFormat);
		else
			sCurrentDate = getFormattedDateTime(currentDate, sFormat);

		// System.out.println(sCurrentDate);

		return sCurrentDate;
	}


	/**
	 * returns a string of the current date plus one day in a specified format
	 * @param sFormat The date format to use. (i.e. "MMMM dd, yyyy"=September 21,2006, "MMM yyyy" = "Sep 2006")
	 * @return String of the current date plus one day in the specified format
	 */
	public static String getCurrentDatePlusOne(String sFormat) {
		// get current date
		long glOneDay = 86400000;
		long currentDate = System.currentTimeMillis() - glCalOffset + glOneDay;
		String sCurrentDate;

		// Format the current date information to string. Check if day is single or double digit
		String sDay = getFormattedDateTime(currentDate, "dd");
		if (Integer.parseInt(sDay) <= 9)
			sCurrentDate = getFormattedDateTime(currentDate, sFormat);
		else
			sCurrentDate = getFormattedDateTime(currentDate, sFormat);

		// System.out.println(sCurrentDate);

		return sCurrentDate;
	}


	/**
	 * returns a string of the current date minus one day in a specified format
	 * @param sFormat The date format to use. (i.e. "MMMM dd, yyyy"=September 21,2006, "MMM yyyy" = "Sep 2006")
	 * @return String of the current date minus one day in the specified format
	 */
	public static String getCurrentDateMinusOne(String sFormat) {
		// get current date
		long glOneDay = 86400000;
		long currentDate = System.currentTimeMillis() - glCalOffset - glOneDay;
		String sCurrentDate;

		// Format the current date information to string. Check if day is single or double digit
		String sDay = getFormattedDateTime(currentDate, "dd");
		if (Integer.parseInt(sDay) <= 9)
			sCurrentDate = getFormattedDateTime(currentDate, sFormat);
		else
			sCurrentDate = getFormattedDateTime(currentDate, sFormat);

		// System.out.println(sCurrentDate);

		return sCurrentDate;

	}

	/**
	 * Extracts part of a date value from a given Date in string format. ie. getDatePartValue("11/5/04","/",0) returns 11)
	 * @param sDate Date in a String format (i.e. 11/5/04)
	 * @param sDelim Date Delimeter. In above example would be "/"
	 * @param iPlacement  integer containing placement of the date part. In above example month iPlacement equal
	 * to 0 will return the month (11). If iPlacement is set to 1 then it would return the day (5) in the
	 * above example. If the iPlacement is set to 2 then it return the year (04) in the above example.
	 * @return part of a date value from a given Date in string format as an int
	 */
	public static int getDatePartValue(String sDate, String sDelim, int iPlacement) {
		String[] lsDate = Strings.stringToStringArray(sDate, sDelim);

		return Integer.parseInt(lsDate[iPlacement]);
	}

	/**
	 * Generates String containing unique random date-based value
	 * <p>
	 * 
	 * @return a String containing a date-based unique value
	 */
	public static String genDateBasedRandVal() {
		// generates String containing unique random date-based value
		String s;
		Date d = new Date();
		// long l;

		// format
		SimpleDateFormat tmformat = new SimpleDateFormat("MMddHHmmss");

		// l = d.getTime();
		s = tmformat.format(d);

		return s;
	}

	/**
	 * Generates String containing unique random date-based value of a specified number of characters
	 * @param i is number of characters to limit output to
	 * @return a String containing a date-based unique value of a specified length
	 */
	public static String genDateBasedRandVal(int i) {
		// generates String containing unique random date-based value
		String s;
		Date d = new Date();
		// long l;

		// format
		SimpleDateFormat tmformat = new SimpleDateFormat("MMddHHmmss");

		// l = d.getTime();
		s = tmformat.format(d);

		int ilen = s.length();

		return s.substring(ilen - i);

	}

	/**
	 * Returns string as Date type
	 * @param sDate Date Text
	 * @param sFormat format to display date in
	 * @return Date
	 */
	public static Date stringToDate(String sDate, String sFormat) {
		Date dDate = null;
		try {
			ParsePosition pos = new ParsePosition(0);
			SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
			if (sDate.endsWith("PM")) {
				dDate = sdf.parse(sDate, pos);
			} else {
				dDate = sdf.parse(sDate, pos);
			}
		} catch (Exception e) {
			return dDate;
		}
		return dDate;
	}

	/**
	 * Formats a date string as specified in sFormat
	 * @param sDate String Date Text i.e. "12/05/2006"
	 * @param sFormatIn String format to display date in i.e "MM/dd/yyyy"
	 * @param sFormatOut String sFormatOut format to display date in i.e "MMMM dd, yyyy"
	 * @return String i.e. "December 5, 2006"
	 */
	public static String formatDateString(String sDate, String sFormatIn, String sFormatOut) {
		long lDate = getLongFromFormattedDateTime(sDate, sFormatIn);

		return getFormattedDateTime(lDate, sFormatOut);
	}


	/**
	 * Returns todays date in 2/23/05 format
	 * @return String - todays date
	 */
	public static String getTodaysDate() {
		Date today = new Date();

		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);

		String dateOut = dateFormatter.format(today);
		// System.out.println( dateOut);
		return dateOut;
	}


	/**
	 * Returns date string value of todays date plus the specified days, months and years (i.e. if todays date is
	 * 2/23/05 then todayPlus(7,2,10) would return 4/30/2015)
	 * @param iDays number of days to increase
	 * @param iMonths number of months to increase
	 * @param iYears number of years to increase
	 * @return String - calculated future date
	 */
	public static String todayPlus(int iDays, int iMonths, int iYears) {
		String sDate = getTodaysDate();

		// Create Calendar instance, and get current Date
		Calendar cal = Calendar.getInstance();
		String sNewDate = "";

		try {
			boolean twoDigitYr = false;
			int chA = sDate.trim().indexOf("/");
			int iMth = Integer.parseInt(sDate.substring(0, chA));
			int chB = sDate.trim().indexOf("/", chA + 1);
			int intDy = Integer.parseInt(sDate.substring(chA + 1, chB));
			int iYr = Integer.parseInt(sDate.substring(chB + 1, sDate.length()));

			if (iYr < 100) {
				twoDigitYr = true;
				iYr = iYr + 2000;
			}
			// Set the calendar object with the Year/Month/Day info passed in
			cal.set(Calendar.MONTH, iMth - 1);
			cal.set(Calendar.YEAR, iYr);
			cal.set(Calendar.DAY_OF_MONTH, intDy);

			// Increment that field accordingly.
			cal.add(java.util.Calendar.YEAR, iYears);
			cal.add(java.util.Calendar.MONTH, iMonths);
			cal.add(java.util.Calendar.DAY_OF_MONTH, iDays);

			// To return the new "Date", need to construct format
			// Have to increment the Month by 1 because it is zero based.
			if (twoDigitYr == true) {
				String str = Integer.toString(cal.get(1));
				sNewDate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + str;
			} else
				sNewDate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/"
						+ cal.get(Calendar.YEAR);

		} catch (Exception e) {

		}

		return sNewDate;

	}


	/**
	 * Returns the name of the day for the specified given date. If you specify "08/01/2014" as the sDate value the
	 * method will return "Friday"
	 * @param sDate String that contains a date in the "MM/dd/yyyy" format that you want to get the Week Day name for
	 * @return String of the Week day name for the specified date
	 */
	public static String returnDayName(String sDate) throws Exception {
		String sdateStrings[];
		if (!DateTime.isExpectedDtFormat(sDate, "MM/dd/yyyy"))
			throw new Exception("Incorrect date format");
		sdateStrings = sDate.split("/");
		Date newDate = (new GregorianCalendar(Integer.parseInt(sdateStrings[2]), Integer.parseInt(sdateStrings[0]) - 1,
				Integer.parseInt(sdateStrings[1]))).getTime();
		return new SimpleDateFormat("EEEE").format(newDate);
	}

	/**
	 * Returns the name of the next business day from the current date. 
	 * @return String of the Week day name for the next business date from the current date 
	 */
	public static String getNextBusinessDay() throws Exception {

		String sDate = DateTime.getCurrentDatePlusOne("MM/dd/yyyy");
		Calendar date = Calendar.getInstance();
		date.setTime(DateTime.stringToDate(sDate, "MM/dd/yyyy"));
		if (returnDayName(sDate).equals("Sunday")) {
			return datePlusBusinessDay(1, 0, 0, sdf.format(DateTime.stringToDate(sDate, "MM/dd/yyyy")));
		} else if (returnDayName(sDate).equals("Saturday")) {
			return datePlusBusinessDay(2, 0, 0, sdf.format(DateTime.stringToDate(sDate, "MM/dd/yyyy")));
		} else {
			return sdf.format(DateTime.stringToDate(sDate, "MM/dd/yyyy"));
		}
	}


	/**
	 * Returns the next business day name as a string for the specified date in sDate plus the specified days, months and years
	 * @param iDays number of days to increase
	 * @param iMonths number of months to increase
	 * @param iYears number of years to increase
	 * @param aDate string specifying a date in the "MM/dd/yyyy" format 
	 * @return String calculated next business day name
	 */
	public static String datePlusBusinessDay(int iDays, int iMonths, int iYears, String aDate) {

		String sDate = aDate;
		// Create Calendar instance, and get current Date
		Calendar cal = Calendar.getInstance();
		String sNewDate = "";

		try {
			boolean twoDigitYr = false;
			int chA = sDate.trim().indexOf("/");
			int iMth = Integer.parseInt(sDate.substring(0, chA));
			int chB = sDate.trim().indexOf("/", chA + 1);
			int intDy = Integer.parseInt(sDate.substring(chA + 1, chB));
			int iYr = Integer.parseInt(sDate.substring(chB + 1, sDate.length()));

			if (iYr < 100) {
				twoDigitYr = true;
				iYr = iYr + 2000;
			}
			// Set the calendar object with the Year/Month/Day info passed in
			cal.set(Calendar.MONTH, iMth - 1);
			cal.set(Calendar.YEAR, iYr);
			cal.set(Calendar.DAY_OF_MONTH, intDy);

			// Increment that field accordingly.
			cal.add(java.util.Calendar.YEAR, iYears);
			cal.add(java.util.Calendar.MONTH, iMonths);
			cal.add(java.util.Calendar.DAY_OF_MONTH, iDays);

			// To return the new "Date", need to construct format
			// Have to increment the Month by 1 because it is zero based.
			if (twoDigitYr == true) {
				String str = Integer.toString(cal.get(1));
				// str = str.substring(2);
				// String y = "1";
				sNewDate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + str;
			} else
				sNewDate = (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/"
						+ cal.get(Calendar.YEAR);

			Calendar date = Calendar.getInstance();
			date.setTime(DateTime.stringToDate(sdf.format(DateTime.stringToDate(sNewDate, "MM/dd/yyyy")), "MM/dd/yyyy"));
			if (returnDayName(sdf.format(DateTime.stringToDate(sNewDate, "MM/dd/yyyy"))).equals("Sunday")) {
				return datePlusBusinessDay(1, 0, 0, sdf.format(DateTime.stringToDate(sNewDate, "MM/dd/yyyy")));
			} else if (returnDayName(sdf.format(DateTime.stringToDate(sNewDate, "MM/dd/yyyy"))).equals("Saturday")) {
				return datePlusBusinessDay(2, 0, 0, sdf.format(DateTime.stringToDate(sNewDate, "MM/dd/yyyy")));
			}

		} catch (Exception e) {

		}

		return sdf.format(DateTime.stringToDate(sNewDate, "MM/dd/yyyy"));
	}

	/**
	 * Returns Time string value of current time plus the specified minutes
	 * @param iMinsToAdd
	 * @return String - calculated time
	 */
	public static String sGetCurrentTimePlusX(int iMinsToAdd) {
		String sHour;
		String sMins;
		String sTime;
		String sNewHour;
		String sNewMins;
		int iMins;
		int iHours;
		Date dToday = new Date();
		String sToday = dToday.toString();
		// System.out.println(sToday);
		// Fri Jun 18 10:45:50 EDT 2004
		sTime = sToday.substring(sToday.indexOf(":") - 2);
		// System.out.println(sTime);
		sTime = sTime.substring(0, sTime.indexOf(" "));
		// System.out.println(sTime);
		sHour = sTime.substring(0, sTime.indexOf(":"));
		sMins = sTime.substring(sTime.indexOf(":") + 1, sTime.lastIndexOf(":"));
		// System.out.println(sHour);
		// System.out.println(sMins);
		iMins = Integer.parseInt(sMins);
		iHours = Integer.parseInt(sHour);
		// System.out.println(iMins+3);
		// ok, now the %$#% tricky part...have to figure out what now + x is...for now let's not worry about things like
		// midnight and value greater than 60 for the argument
		// in fact might want to change the args to be two args...hours to add and mins to add
		if ((iMins + iMinsToAdd) > 59) {
			sNewMins = String.valueOf((iMins + iMinsToAdd) - 60);
			sNewHour = String.valueOf(iHours + 1);
		} else {
			sNewMins = String.valueOf(iMins + iMinsToAdd);
			sNewHour = sHour;
		}
		// bsm 12/18/2007 problem when minutes was less than 10 was returning time as 9:6 instead of 9:06
		if (sNewMins.length() == 1)
			sNewMins = "0" + sNewMins;

		return sNewHour + ":" + sNewMins;
	}

	/**
	 * Returns Time string value of current time plus the specified minutes in 12 hour format
	 * @param iMinsToAdd
	 * @return String - calculated time
	 */
	public static String sGetCurrentTimePlusX12(int iMinsToAdd) {
		String sHour;
		String sMins;
		String sTime;
		String sNewHour;
		String sNewMins;
		int iMins;
		int iHours;
		Date dToday = new Date();
		String sToday = dToday.toString();
		// System.out.println(sToday);
		// Fri Jun 18 10:45:50 EDT 2004
		sTime = sToday.substring(sToday.indexOf(":") - 2);
		// System.out.println(sTime);
		sTime = sTime.substring(0, sTime.indexOf(" "));
		// System.out.println(sTime);
		sHour = sTime.substring(0, sTime.indexOf(":"));
		sMins = sTime.substring(sTime.indexOf(":") + 1, sTime.lastIndexOf(":"));
		// System.out.println(sHour);
		// System.out.println(sMins);
		iMins = Integer.parseInt(sMins);
		iHours = Integer.parseInt(sHour);
		// System.out.println(iMins+3);
		// ok, now the %$#% tricky part...have to figure out what now + x is...for now let's not worry about things like
		// midnight and value greater than 60 for the argument
		// in fact might want to change the args to be two args...hours to add and mins to add
		if ((iMins + iMinsToAdd) > 59) {
			sNewMins = String.valueOf((iMins + iMinsToAdd) - 60);
			iHours = iHours + 1;
			// do this later
			// sNewHour=String.valueOf(iHours+1);
		} else {
			sNewMins = String.valueOf(iMins + iMinsToAdd);
		}
		// bsm 12/18/2007 problem when minutes was less than 10 was returning time as 9:6 instead of 9:06
		if (sNewMins.length() == 1)
			sNewMins = "0" + sNewMins;
		// hours greater than 12 need to set to regular time
		if (iHours > 12)
			iHours = iHours - 12;
		sNewHour = String.valueOf(iHours);
		return sNewHour + ":" + sNewMins;
	}

	/**
	 * Checks the format of a given Date string and returns true if it matches the expected format.
	 * @param sDateToValidate string representing a specific date f.ex. "05/25/2011", "03/21/11 02:21 AM", "12/31"
	 * @param sExpectedFormat string representing desired format date should be in Format must consist of Java
	 * standard date format characters, f.ex. "MM/dd/yyyy", "MM/dd/yy hh:mm a", "MM/dd"
	 * @return true if format of passed date matches the desired format false if format of
	 * passed date does NOT match the desired format 
	 */
	public static boolean isExpectedDtFormat(String sDateToValidate, String sExpectedFormat) {
		Date dtExpectedDate;
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat(sExpectedFormat);

		try {
			dtExpectedDate = sdfDateFormat.parse(sDateToValidate);
			if ((sdfDateFormat.format(dtExpectedDate).equals(sDateToValidate)) == true) {
				return true;
			} else {
				return false;
			}

		} catch (ParseException e) {
			return false;
		}
	}


	/**
	 * Checks the format of a given Date string and logs an error if it does NOT match the expected format.
	 * @param sDateToValidate string representing a specific date f.ex. "05/25/2011", "03/21/11 02:21 AM", "12/31"
	 * @param sExpectedFormat string representing desired format date should be in Format must consist of Java
	 * standard date format characters, f.ex. "MM/dd/yyyy", "MM/dd/yy hh:mm a", "MM/dd"
	 */
	public static void verifyExpectedDtFormat(String sDateToValidate, String sExpectedFormat) {
		if (isExpectedDtFormat(sDateToValidate, sExpectedFormat) == true) {
			Log.logScriptInfo("The specified date, " + sDateToValidate + ", is in the expected format: "
					+ sExpectedFormat);
		} else {
			Log.errorHandler("The specified date, " + sDateToValidate
					+ ", is NOT in the expected format. The expected format is: " + sExpectedFormat);
		}
	}


	/*
	 * Public Default Constructor to satisfy compilers need for a default constructor
	 */

	/**
	 * Constructor for: DateTime() class using system date for Todays date
	 */
	public DateTime() {
		calendar = new GregorianCalendar();
		setToday();
		setCurrentDate(dToday);
	}

	/*
	 * New Public Static Methods Start Here
	 */
	/**
	 * Returns Date object for a given String date value 
	 * @param sDate Date String to convert to a date object formated as: "MM/dd/yyyy"
	 * @return Date object
	 */
	public static Date convertStringDateToDate(String sDate) {
		return new DateTime(sDate).getDate();
	}

	/**
	 * Converts Date Parameter value to String representation of: "MM/dd/yyyy"
	 * @param dDateValue Date Type value
	 * @return String representation of Date value as: MM/dd/yyyy
	 */
	public static String convertDateToString(Date dDateValue) {
		return new DateTime(dDateValue).getDateAsString();
	}

	/**
	 * returns the Next Business Day date (Excludes Saturday and Sunday) as a Date object
	 * @param dDate Date to modify
	 * @return Next Business Day date (Excludes Saturday and Sunday)
	 */
	public static Date getNextBusinessDate(Date dDate) {
		return new DateTime(dDate).advanceDate(1);
	}

	/**
	 * Advances the date to the next Business date plus the number of days to advance the date by. 
	 * iAdvanceDays and will skip past Saturday and Sunday
	 * @param dDate Date to advance
	 * @param iDaysToAdvance days to advance
	 * @return Next Business date advanced by iAdvanceDays and will skip past Saturday and Sunday
	 */
	public static Date advanceTheDate(Date dDate, int iDaysToAdvance) {
		return new DateTime(dDate).advanceDate(iDaysToAdvance);
	}

	/**
	 * Returns the next business date on or after date advanced by iYearsToAdvance and will skip past Saturday and Sunday
	 * @param dDate Date to advance
	 * @param iYearsToAdvance years to advance
	 * @return Next Business date on or after date advanced by iYearsToAdvance and will skip past Saturday and Sunday
	 */
	public static Date advanceTheDateByYears(Date dDate, int iYearsToAdvance) {
		return new DateTime(dDate).advanceDateInYears(iYearsToAdvance);
	}

	/**
	 * Returns the next non business date
	 * @param dDate Date to begin with
	 * @return Next Non-Business date
	 */
	public static Date getNextNonBusinessDate(Date dDate) {
		return new DateTime(dDate).computeNextNonBusinessDate();
	}

	/**
	 * Returns the next non business Date As a String
	 * @param dDate Date to test
	 * @return String representation of Next Non Business Day Date as: MM/dd/yyyy
	 */
	public static String getNextNonBusinessDateAsString(Date dDate) {
		return convertDateToString(getNextNonBusinessDate(dDate));
	}

	/**
	 * Returns the next business Date As a String
	 * @param dDate Date to test
	 * @return String representation of Next Business Day Date as: MM/dd/yyyy
	 */
	public static String getNextBusinessDateAsString(Date dDate) {
		return convertDateToString(getNextBusinessDate(dDate));
	}

	/**
	 * Returns current business date object from string date representation
	 * @param sDateString Date String to convert to a date object formated like: "MM/dd/yyyy"
	 * @param sFormat Format string value to apply like: "MM/dd/yyyy"
	 * @return Current Business date object from string date representation
	 */
	public static Date convertStringDateToDate(String sDateString, String sFormat) {
		return new DateTime().StringDateToDate(sDateString, sFormat);
	}

	/**
	 * Returns the number days between a begin date and an end date as a long data type.
	 * Logs error if End Date supersedes Start Date
	 * Example: Start = 07/21/2014 End = 07/22/2014 getDaysBetween = 1
	 * @param startDate Calendar Start Date value
	 * @param endDate Calendar End Date value
	 * @return System days between Start and End dates
	 */
	public static long getDaysBetween(Calendar startDate, Calendar endDate) {
		return new DateTime().calculateDaysBetween(startDate, endDate);
	}


	/**
	 * Returns an invalid date
	 * @param dDate Current Date value
	 * @return Invalid date string of current month and year, with day value of 32
	 */
	public static String getAnInvalidDate(Date dDate) {
		return new DateTime(dDate).createInvalidDate();
	}


	/**
	 * Returns true if System date day of the week matches nDayToMatch parameter value
	 * Note: Sunday is day 1 of the week
	 * @param integer nDayToMatch the corresponding number value for day of week i.e. 1 = Sunday, 2= Monday , etc 
	 * @return true if System date day of week matches nDayToMatch parameter value
	 */
	public static boolean isCurrentDayOfWeekSame(int nDayToMatch) {
		return (new DateTime().getCurrentDayOfWeek() == nDayToMatch);
	}



	/**
	 * Returns true if system date is an even number, false if odd number
	 * @return true if System date value is an even number
	 */
	public static boolean isEvenDate() {
		return new DateTime().isDateEven();
	}


	/**
	 * Return prior business date on or before the date decremented by iDaysToDdecrement skipping past Saturday and Sunday.
	 * @param dDate Current Date to decrement
	 * @param iDaysToDdecrement int value for how many days to decrement the specified date by
	 * @return Prior Business date on or before date decremented by iDaysToDdecrement, skipping past Saturday and
	 *         Sunday.
	 */
	public static Date getDecrementedDate(Date dDate, int iDaysToDdecrement) {
		return new DateTime(dDate).decrementDate(iDaysToDdecrement);
	}

	/**
	 * Returns true if Current Date value represents last day of that month
	 * @param dDate Current Date value
	 * @return true if Current Date value represents last day of that month
	 */
	public static boolean isLastDayOfMonth(Date dDate) {
		return new DateTime(dDate).isLastdayOfMonth();
	}


	/**
	 * Returns true if the the Current Date represented as a String value represents the last day of that month
	 * @param dDate String of Current Date value represented as a string as: MM/dd/yyyy
	 * @return true if Current Date value represents last day of that month
	 */
	public static boolean isLastDayOfMonth(String sDate) {
		return isLastDayOfMonth(convertStringDateToDate(sDate));
	}


	/**
	 * Returns a String value of ToDays date (System date) formated as: MM/dd/yyyy
	 * @return String value of ToDays date (System date) formated as: MM/dd/yyyy
	 */
	public static String getToDayAsString() {
		return new DateTime().sToDay;
	}



	/**
	 * Returns a Date object for the current days date
	 * @return Date object of ToDays date (System date)
	 */
	public static Date getToDay() {
		return new DateTime().todaysDate();
	}



	/**
	 * Returns a String value of Current date object formated as: MM/dd/yyyy
	 * @param dDate Date object containing current date
	 * @return String value of Current date object formated as: MM/dd/yyyy
	 */
	public static String getCurrentDateString(Date dDate) {
		return new DateTime(dDate).sCurrentDate;
	}


	/**
	 * Get Current time based on given time zone
	 * @param timeFormat (hh:mm or hh:mm:ss or hh:mm:ss.SSS)
	 * @param TimeZone (US/Eastern,US/Central,US/Pacific)
	 */
	public static String getCurrentTime(String sTimeFormat, String sTimeZone) {
		TimeZone timezone = TimeZone.getTimeZone(sTimeZone);

		//Specifying the format
		DateFormat dateFormat = new SimpleDateFormat(sTimeFormat);

		//Setting the Timezone 
		Calendar cal = Calendar.getInstance(timezone);
		dateFormat.setTimeZone(cal.getTimeZone());

		//Picking the time value in the required Format
		String sCurrentTime = dateFormat.format(cal.getTime());
		return sCurrentTime;
	}

	/**
	 * Get Today's day based on given time zone
	 * @param dayFormat (EEEEEE - to represent the day in full and EEE - to return the abbreviated day)
	 * @param TimeZone (US/Eastern,US/Central,US/Pacific)
	 */
	public static String getTodaysDay(String sDayFormat, String sTimeZone) {
		TimeZone timezone = TimeZone.getTimeZone(sTimeZone);

		//Specifying the format
		Date date = new Date();
		DateFormat requiredFormat = new SimpleDateFormat(sDayFormat);

		//Setting the Timezone 
		requiredFormat.setTimeZone(timezone);

		//Picking the day value in the required Format
		String sCurrentDay = requiredFormat.format(date);
		return sCurrentDay;
	}


	/**
	 * Get Today's date based on given time zone
	 * @param dayFormat (EEEEEE - to represent the day in full and EEE - to return the abbreviated day)
	 * @param TimeZone - (US/Eastern,US/Central,US/Pacific)
	 */
	public static String getTodaysDate(String sDateFormat, String sTimeZone)
	{
		TimeZone timezone = TimeZone.getTimeZone(sTimeZone);

		//Specifying the format
		Date todayDate = new Date();
		DateFormat requiredFormat = new SimpleDateFormat(sDateFormat);

		// Setting the Timezone 
		requiredFormat.setTimeZone(timezone);

		// Picking the date value in the required Format 
		String sTodayDate = requiredFormat.format(todayDate);
		return sTodayDate;
	}

	/*
	 * New Private Fields Start Here
	 */
	private Calendar calendar;
	private Date dCurrentDate;
	private Date dToday;
	private boolean bIsNonBusinessDay;
	private boolean bIsSunday;
	private boolean bIsLastDayOfMonth;

	private String sToDay;
	private String sCurrentDate;

	/*
	 * New Private Methods Start Here
	 */
	/**
	 * Returns Date object from a given date String formated as: "MM/dd/yyyy".
	 * Logs error if invalid date string
	 * @param sDateString date String formated as: "MM/dd/yyyy"
	 * @return Current Business date object from string date representation.
	 */
	private Date convertStringToDate(String sDateString) {
		Date dDateValue = null;

		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			dDateValue = df.parse(sDateString);

		} catch (Exception e) {
			Log.errorHandler("Error in convertStringToDate() " + e.getMessage());
		}

		return dDateValue;
	}

	/**
	 * Sets bIsNonBusinessDay, bIsSunday, bIsSaturday, and bIsLastDayOfMonth fields based upon
	 * dCurrentDate field value
	 */
	private void setIsNonBusinessDate() {
		boolean bIsSaturday = (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK));
		bIsSunday = (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK));

		if (bIsSaturday || bIsSunday) {
			bIsNonBusinessDay = true;
		} else {
			bIsNonBusinessDay = false;
		}

		bIsLastDayOfMonth = (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	}


	/**
	 * Returns current date plus 1 day (if new date falls on Saturday or Sunday advances to Monday)
	 * @param iDaysToAdvance - days to advance
	 * @return current date plus 1 day (if new date falls on Saturday or Sunday advances to Monday)
	 */
	private Date advanceDate(int iDaysToAdvance) {
		calendar.add(Calendar.DATE, iDaysToAdvance);
		setCurrentDate(calendar.getTime());

		if (bIsNonBusinessDay) {
			if (bIsSunday) {
				calendar.add(Calendar.DATE, 1);
			} else {
				calendar.add(Calendar.DATE, 2);
			}
			setCurrentDate(calendar.getTime());
		}

		return getDate();
	}


	/**
	 * Returns current date plus 1 year (if new date falls on Saturday or Sunday advances to Monday)
	 * @param iYearsToAdvance number of years to advance current date
	 * @return current date plus 1 year (if new date falls on Saturday or Sunday advances to Monday)
	 */
	private Date advanceDateInYears(int iYearsToAdvance) {
		calendar.add(Calendar.YEAR, iYearsToAdvance);
		setCurrentDate(calendar.getTime());

		if (bIsNonBusinessDay) {
			if (bIsSunday) {
				calendar.add(Calendar.DATE, 1);
			} else {
				calendar.add(Calendar.DATE, 2);
			}
			setCurrentDate(this.calendar.getTime());
		}

		return getDate();
	}


	/**
	 * Returns the current day of the week
	 * @return Calendar DAY_OF_WEEK value
	 */
	private int getCurrentDayOfWeek() {
		Calendar calendar = new GregorianCalendar();
		Date date = new Date();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Returns the next business date on or before the date decremented by iDaysToDecrement
	 * @param iDaysToDecrement days to decrement
	 * @return Next Business date on or before the date decremented by iDaysToDecrement
	 */
	private Date decrementDate(int iDaysToDecrement) {
		calendar.add(Calendar.DATE, (iDaysToDecrement * -1));
		setCurrentDate(calendar.getTime());

		if (bIsNonBusinessDay) {
			if (bIsSunday) {
				calendar.add(Calendar.DATE, (2 * -1));
			} else {
				calendar.add(Calendar.DATE, (1 * -1));
			}
			setCurrentDate(this.calendar.getTime());
		}
		return getDate();
	}

	/**
	 * Sets dToday, dCurrentDate, and sToday values to the System date
	 */
	private void setToday() {
		dToday = new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		sToDay = dateFormat.format(dToday);
	}

	/**
	 * Sets internal Current date and Calendar to Date value parameter
	 * @param dateValue Current date value
	 */
	private void setCurrentDate(Date dateValue) {
		dCurrentDate = dateValue;
		calendar.setTime(dCurrentDate);

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		sCurrentDate = dateFormat.format(dCurrentDate);

		setIsNonBusinessDate();
	}

	/**
	 * Returns Current Date object
	 */
	private Date getDate() {
		return dCurrentDate;
	}


	/**
	 * Returns System date object
	 * @return System date object
	 */
	private Date todaysDate() {
		return dToday;
	}


	/**
	 * Returns String representation of Current Date
	 * <p>
	 * @return String representation of Current Date
	 */
	private String getDateAsString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String sDate = df.format(dCurrentDate);
		return sDate;
	}

	/**
	 * Returns true if current date object value equals the Last Day Of Month
	 * @return true if current date object value equals the Last Day Of Month
	 */
	private boolean isLastdayOfMonth() {
		return bIsLastDayOfMonth;
	}

	/**
	 * Returns true if date value is an even number
	 * @return true if date value is an even number
	 */
	private boolean isDateEven() {
		boolean bIsEven;
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		bIsEven = ((dayOfMonth & 1) == 0); // test if lowest bit is set

		return (bIsEven);
	}

	/**
	 * Returns an invalid String date based on the current date and uses 32nd as day
	 * @return an invalid String date based on the current date and uses 32nd as day
	 */
	private String createInvalidDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String sDate = df.format(dCurrentDate);

		String sMonth = sDate.substring(0, 3);
		String sYear = sDate.substring(5);
		String sInvalidDate = sMonth + "32" + sYear;

		return sInvalidDate;
	}


	/**
	 * Returns the number of days between Start and End dates using Calendar date 
	 * For example,
	 * Calendar calendarStart = new GregorianCalendar(2014,1,28,13,24,56);
	 * Calendar calendarEnd = new GregorianCalendar(2014,3,28,13,24,56);
	 * calculateDaysBetween(Calendar startDate, Calendar endDate)
	 * returns 59 (days)
	 * @param startDate Start Date value 
	 * @param endDate End Date value
	 * @return number of days between Start and End dates
	 */
	private long calculateDaysBetween(Calendar startDate, Calendar endDate) {
		Calendar date = (Calendar) startDate.clone();
		long daysBetween = 0;

		try {
			if (date.after(endDate)) {
				throw new Exception("End date before Start date.");
			}

			while (date.before(endDate)) {
				date.add(Calendar.DAY_OF_MONTH, 1);
				daysBetween++;
			}
		} catch (Exception e) {
			Log.errorHandler("Error in calculateDaysBetween(): " + e.getMessage());
		}
		return daysBetween;
	}

	/**
	 * Returns the current business date object from string date representation
	 * @param sDateString Date String to convert to a date object formated like: "MM/dd/yyyy"
	 * @param sFormat Format string value to apply like: "MM/dd/yyyy"
	 * @return Current Business date object from string date representation
	 */
	private Date StringDateToDate(String sDateString, String sFormat) {
		Date dDateValue = null;

		try {
			DateFormat df = new SimpleDateFormat(sFormat);
			dDateValue = df.parse(sDateString);
		} catch (Exception e) {
			Log.errorHandler("Error in convertStringToDate() " + e.getMessage());
		}
		return dDateValue;
	}


	/**
	 * Returns the next non-business date on or after date advanced by iAdvanceDays
	 * @return Next Non-Business date on or after date advanced by iAdvanceDays
	 */
	private Date computeNextNonBusinessDate() {
		int iDaysToAdvance = 1;

		while (!bIsNonBusinessDay) {
			calendar.add(Calendar.DATE, iDaysToAdvance);
			setCurrentDate(calendar.getTime());
		}
		return getDate();
	}


	/*
	 * Private Constructors Start Here
	 */

	/**
	 * Constructor for: DateTime() using sDate parameter for Current date
	 * @param sDate Date String to convert to a date object formated as: "MM/dd/yyyy"
	 */
	private DateTime(String sDate) {

		setCurrentDate(convertStringToDate(sDate));
	}

	/**
	 * Constructor for: DateTime() using dDate parameter for Current date
	 * @param dDate Date object
	 */
	private DateTime(Date dDate) {
		
		setCurrentDate(dDate);
	}

	/**
	 * Unit tests and example calls for all methods in the DateTime class library
	 */
	public static void DateTimeUnitTest() {
		String sSlash = "\\";
		String sDQ = "\"";
		Calendar calendarStart = new GregorianCalendar(2014,1,28,13,24,56);
		Calendar calendarEnd = new GregorianCalendar(2014,3,28,13,24,56);
		long long_09042014_210PM = 1409854252651L; //long value for approximate date and time - 09/04/2014 2:10pm

		Log.logBanner("DateTime class library Unit Test");
		Log.logScriptInfo("advanceTheDate(getToDay(), 10);   ======>  "   +	 advanceTheDate(getToDay(), 10));
		Log.logScriptInfo("advanceTheDateByYears(getToDay(), 10);   ======>  "   +	 advanceTheDateByYears(getToDay(), 10));
		Log.logScriptInfo("convertDateToString(getToDay());	======>  "   +	convertDateToString(getToDay()));
		Log.logScriptInfo("convertStringDateToDate(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ");  ======>  "   +	convertStringDateToDate("08/06/2014"));
		Log.logScriptInfo("convertStringDateToDate(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ", " + sDQ + "MM" +sSlash + "dd" +sSlash + "yyyy" + sDQ + ");     ======>  "   +  convertStringDateToDate("08/06/2014", "MM/dd/yyyy"));
		Log.logScriptInfo("datePlusBusinessDay(10, 10, 10, " + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ");     ======>  "   +     	datePlusBusinessDay(10, 10, 10, "08/06/2014"));
		Log.logScriptInfo("formatDateString(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ", " + sDQ + "MM" +sSlash+ "dd" +sSlash + "yyyy" +sDQ + ", " + sDQ + "MMMM dd, yyyy" + sDQ + ");     ======>  "   +     	formatDateString("08/06/2014", "MM/dd/yyyy", "MMMM dd, yyyy"));
		Log.logScriptInfo("genDateBasedRandVal();     ======>  "   +     	genDateBasedRandVal());
		Log.logScriptInfo("genDateBasedRandVal(6);     ======>  "   +     	genDateBasedRandVal(6));
		Log.logScriptInfo("getAnInvalidDate(getToDay());     ======>  "   +     	getAnInvalidDate(getToDay()));
		Log.logScriptInfo("getCurrentDate();     ======>  "   +     	getCurrentDate());
		Log.logScriptInfo("getCurrentDate(" + sDQ + "MMM yyyy" + sDQ + ");     ======>  "   +     	getCurrentDate("MMM yyyy"));
		Log.logScriptInfo("getCurrentDateMinusOne(" + sDQ + "MM" + sSlash + "dd" + sSlash + "yyyy" + sDQ + ");     ======>  "   +     	getCurrentDateMinusOne("MM/dd/yyyy"));
		Log.logScriptInfo("getCurrentDatePlusOne(" + sDQ + "MM" + sSlash + "dd" + sSlash + "yyyy" + sDQ + ");     ======>  "   +     	getCurrentDatePlusOne("MMMM dd, yyyy"));
		Log.logScriptInfo("getCurrentDateString(getToDay());     ======>  "   +     	getCurrentDateString(getToDay()));
		Log.logScriptInfo("getCurrentTime(" + sDQ + "HH:mm" + sDQ + "," + sDQ + "US//Pacific" + sDQ + "); ======>" + getCurrentTime("HH:mm", "US/Pacific")); 
		Log.logScriptInfo("getDatePartValue(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ", " + sSlash + ", 2);     ======>  "   +     	getDatePartValue("08/06/2014", "/", 2));
		Log.logScriptInfo("getDaysBetween(calendarStart, calendarEnd);     ======>  "   +     	getDaysBetween(calendarStart, calendarEnd));
		Log.logScriptInfo("getDecrementedDate(getToDay(), 10);     ======>  "   +     	getDecrementedDate(getToDay(), 10));
		Log.logScriptInfo("getElapsedTime(long_09042014_210PM);     ======>  "   +     	getElapsedTime(long_09042014_210PM));
		Log.logScriptInfo("getElapsedTime(long_09042014_210PM, " + sDQ + "HH:mm:ss:SSS" + sDQ + ");     ======>  "   +     	getElapsedTime(long_09042014_210PM, "HH:mm:ss:SSS"));
		Log.logScriptInfo("getElapsedTimeLong(long_09042014_210PM);     ======>  "   +     	getElapsedTimeLong(long_09042014_210PM));
		Log.logScriptInfo("getFormattedDateTime(System.currentTimeMillis(), " + sDQ + "MMMM dd, yyyy HH:mm:ss:SSS" +sDQ + ");     ======>  "   +     	getFormattedDateTime(System.currentTimeMillis(), "MM/dd/yyyy HH:mm:ss:SSS"));
		Log.logScriptInfo("getLongFromFormattedDateTime(" + sDQ + "09" + sSlash + "21" + sSlash + "1964" + sDQ + ", " + sDQ + "MM" + sSlash + "dd" + sSlash + "yy" + sDQ + ");     ======>  "   +     	getLongFromFormattedDateTime("09/21/1964", "MM/dd/yy"));
		Log.logScriptInfo("getNextBusinessDate(getToDay());     ======>  "   +     	getNextBusinessDate(getToDay()));
		Log.logScriptInfo("getNextBusinessDateAsString(getToDay());     ======>  "   +     	getNextBusinessDateAsString(getToDay()));
		try{Log.logScriptInfo("getNextBusinessDay();     ======>  "   +     	getNextBusinessDay());}catch(Exception e){};
		Log.logScriptInfo("getNextNonBusinessDate(getToDay());     ======>  "   +     	getNextNonBusinessDate(getToDay()));
		Log.logScriptInfo("getNextNonBusinessDateAsString(getToDay());     ======>  "   +     	getNextNonBusinessDateAsString(getToDay()));
		Log.logScriptInfo("getToDay();     ======>  "   +     	getToDay());
		Log.logScriptInfo("getTodaysDate(" + sDQ + "MMM dd, yyyy" + sDQ + "," + sDQ + "US//Pacific" + sDQ + "); ======> "  + getTodaysDate("MMM dd, yyyy", "US/Pacific"));
		Log.logScriptInfo("getTodaysDay(" + sDQ + "EEE" + sDQ + "," + sDQ + "US//Pacific" + sDQ + "); ======> "  + getTodaysDate("EEE", "US/Pacific"));
		Log.logScriptInfo("getToDayAsString();     ======>  "   +     	getToDayAsString());
		Log.logScriptInfo("getTodaysDate();     ======>  "   +     	getTodaysDate());
		Log.logScriptInfo("isCurrentDayOfWeekSame(6);     ======>  "   +     	isCurrentDayOfWeekSame(6));
		Log.logScriptInfo("isEvenDate();     ======>  "   +     	isEvenDate());
		Log.logScriptInfo("isExpectedDtFormat(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ", " + sDQ + "MM" + sSlash + "dd" +sDQ + ");     ======>  "   +     	isExpectedDtFormat("08/06/2014" , "MM/dd"));
		Log.logScriptInfo("isLastDayOfMonth(getToDay());     ======>  "   +     	isLastDayOfMonth(getToDay()));
		Log.logScriptInfo("isLastDayOfMonth(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ");     ======>  "   +     	isLastDayOfMonth("08/06/2014"));
		//Log.logScriptInfo("parseDate(" + sDQ + "08" + sSlash + "06" + sSlash + "2014 12:30PM" +sDQ + ");     ======>  "   +     	parseDate("08-06-2014 12:30 PM"));
		try{Log.logScriptInfo("returnDayName(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ");     ======>  "   +     	returnDayName("08/06/2014"));}catch(Exception e){};
		Log.logScriptInfo("setStartTime();     ======>  "   +     	setStartTime());
		Log.logScriptInfo("sGetCurrentTimePlusX(10);     ======>  "   +     	sGetCurrentTimePlusX(10));
		Log.logScriptInfo("sGetCurrentTimePlusX12(10);     ======>  "   +     	sGetCurrentTimePlusX12(10));
		//Log.logScriptInfo("sortDate(String[], boolean);     ======>  "   +     	sortDate(String[], boolean));
		Log.logScriptInfo("stringToDate(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ", "+ sDQ + "MM" + sSlash + "dd" + sSlash + "yy" + sDQ + ");     ======>  "   +     	stringToDate("08/06/2014", "MM/dd/yy"));
		Log.logScriptInfo("todayPlus(10, 10, 10);     ======>  "   +     	todayPlus(10, 10, 10));
		Log.logScriptInfo("verifyExpectedDtFormat(" + sDQ + "08" + sSlash + "06" + sSlash + "2014" + sDQ + ", " + sDQ + "MM" + sSlash + "dd" + sSlash + "yyyy" + sDQ + ");   ======>  ");
		verifyExpectedDtFormat("08/06/2014", "MM/dd/yyyy");
		Log.logBanner("DateTime class library Unit Test - Completed");

	}

}