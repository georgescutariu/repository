package io.fourfinanceit.loan.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.common.base.Preconditions;

public class DateUtil {
	
    public static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    public static final LocalTime NIGHT_END = LocalTime.MIDNIGHT.plusHours(6);
	
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm";

	public static String formatDate(LocalDateTime dateTime) {
		Preconditions.checkArgument(dateTime != null);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		return dateTime.format(formatter);
	}
	
	public static String formatDateTime(LocalDateTime dateTime) {
		Preconditions.checkArgument(dateTime != null);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		return dateTime.format(formatter);
	}
}
