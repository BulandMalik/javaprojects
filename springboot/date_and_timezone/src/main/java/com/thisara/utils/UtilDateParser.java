package com.thisara.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

public class UtilDateParser {

	private static final Logger logger = Logger.getLogger(UtilDateParser.class.getName());
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date parseDate(String date) {
		
		Date formattedDate = null;
		
		try {
			
			formattedDate = dateFormat.parse(date);
			
		}catch(ParseException pe) {
			logger.severe(pe.getMessage());
		}
		
		return formattedDate;
	}
	
	public static Date parseTimestamp(String timestamp, String userTimeZone) {
		
		Date formattedTimestamp = null;
		
		try {
			
			TimeZone timeZone = TimeZone.getTimeZone(userTimeZone);

			Calendar calendar = Calendar.getInstance(timeZone);
	      
			timestampFormat.setCalendar(calendar);
  
			formattedTimestamp = timestampFormat.parse(timestamp);
			
		}catch(ParseException pe) {
			logger.severe(pe.getMessage());
		}
		
		return formattedTimestamp;
	}
	
}

/*
 
 import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import java.time.temporal.Adjusters;

public class HumanTimeDemo
{
   public static void main(String[] args)
   {
      ZoneId zid = ZoneId.systemDefault();
      System.out.printf("Zone Id = %s%n", zid);
      System.out.printf("Rules = %s%n", zid.getRules());
      System.out.printf("DST in effect: %b%n",
                        zid.getRules().isDaylightSavings(Instant.now()));

      zid = ZoneId.of("Europe/Paris");
      System.out.printf("Zone Id = %s%n", zid);

      ZoneOffset zoffset = ZoneOffset.of("+06:00");
      System.out.printf("Zone Offset = %s%n", zoffset);
      System.out.printf("Total seconds = %d%n", zoffset.getTotalSeconds());

      ZonedDateTime zonedDateTime = ZonedDateTime.now();
      System.out.printf("Zoned date and time = %s%n", zonedDateTime);
      System.out.printf("Zone = %s%n", zonedDateTime.getZone());

      zoffset = ZoneOffset.from(zonedDateTime);
      System.out.printf("Zone Offset = %s%n", zoffset);
 
      OffsetDateTime offsetDateTime = OffsetDateTime.now();
      System.out.printf("Offset date and time = %s%n", offsetDateTime);
      System.out.printf("Offset date and time = %s%n", 
                        offsetDateTime.with(Adjusters.lastDayOfMonth()));

      zonedDateTime = ZonedDateTime.of(2013, 11, 2, 3, 00, 0, 0, 
                                       ZoneId.of("America/Chicago"));
      System.out.printf("Zoned date and time = %s%n", zonedDateTime);


      zonedDateTime = ZonedDateTime.of(2013, 11, 3, 3, 00, 0, 0, 
                                       ZoneId.of("America/Chicago"));
      System.out.printf("Zoned date and time = %s%n", zonedDateTime);

      offsetDateTime = OffsetDateTime.of(2013, 11, 2, 3, 00, 0, 0, zoffset);
      System.out.printf("Offset date and time = %s%n", offsetDateTime);

      offsetDateTime = OffsetDateTime.of(2013, 11, 3, 3, 00, 0, 0, zoffset);
      System.out.printf("Offset date and time = %s%n", offsetDateTime);
   }
}
*/