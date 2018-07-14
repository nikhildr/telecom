package com.config.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimestampHelper {
	
	public static void main(String[] args) {
		System.out.println(canonicalFormat());
	}
    public static ZoneOffset getCurrentOffset() {
        return OffsetDateTime.now().getOffset();
    }

    public static String getCurrentDate() {
        return Instant.now().atOffset(getCurrentOffset()).toString();
    }

    public static Timestamp getCurrentTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static String getFilenameTimestamp() {
        return DateTimeFormatter.ofPattern("yyyyMMdd'T'hhmmss").format(OffsetDateTime.now());
    }

    public static String format(long raw) {
        return format(new Date(raw));
    }

    public static String format(Date raw) {
        DateFormat formatter = canonicalFormat();
        return formatter.format(raw);
    }

    public static DateFormat canonicalFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter;
    }

    public static Timestamp parseLocalTime(String raw) {
        try {
            return Timestamp.valueOf(LocalDateTime.parse(raw));
        } catch(DateTimeParseException ex) {
            return null;
        }
    }

    public static String formatLocalDateTime(LocalDateTime raw) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(raw);
    }

   
}
