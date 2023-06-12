package com.thisara.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.logging.Logger;

public class SQLDateParser {

	private static Logger logger = Logger.getLogger(SQLDateParser.class.getName());
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date parseDate(String date) {
		
		Date parsedDate = null;
		
		try {
			
			parsedDate = new Date(dateFormat.parse(date).getTime());
			
		}catch(ParseException pe) {
			logger.severe(pe.toString());
		}
		
		return parsedDate;
	}
	
	public static Timestamp parseTimestamp(String timeStamp) {
		
		Timestamp parsedTimestamp = null;
		
		try {
			
			parsedTimestamp = new Timestamp(timeStampFormat.parse(timeStamp).getTime());
			
		}catch(ParseException pe) {
			logger.severe(pe.toString());
		}
		
		return parsedTimestamp;
	}
}
