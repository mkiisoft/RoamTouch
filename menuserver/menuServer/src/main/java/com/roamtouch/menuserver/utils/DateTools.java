package com.roamtouch.menuserver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTools {

	public static final String inputFormat = "HH:mm";
	
	SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

	private Date date;
	private Date dateCompareOne;
	private Date dateCompareTwo;
	
	public boolean compareDates(String compareStringOne, String compareStringTwo){
	    Calendar now = Calendar.getInstance();

	    int hour = now.get(Calendar.HOUR);
	    int minute = now.get(Calendar.MINUTE);

	    date = parseDate(hour + ":" + minute);
	    dateCompareOne = parseDate(compareStringOne);
	    dateCompareTwo = parseDate(compareStringTwo);

	    if ( dateCompareOne.before( date ) && dateCompareTwo.after(date)) {
	       return true;
	    } else {
	    	return false;
	    }
	}

	private Date parseDate(String date) {

	    try {
	        return inputParser.parse(date);
	    } catch (java.text.ParseException e) {
	        return new Date(0);
	    }
	}
	
	
}
