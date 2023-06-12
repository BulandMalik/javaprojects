package com.thisara.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class DateTimeParser {

	private static Logger logger = Logger.getLogger(DateTimeParser.class.getName());

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDate parseDate(String date) {

		LocalDate formattedDate = null;

		formattedDate = LocalDate.parse(date, formatter);

		return formattedDate;
	}

	public static ZonedDateTime parseTimestamp(String timestamp, String userTimeZone) {

		ZonedDateTime formattedTimestamp = null;
		
		LocalDateTime localDateTime = LocalDateTime.parse(timestamp, dateTimeFormatter);

		ZoneId zoneId = ZoneId.of(userTimeZone);

		formattedTimestamp = ZonedDateTime.of(localDateTime, zoneId);
		
		return formattedTimestamp;
	}
}
