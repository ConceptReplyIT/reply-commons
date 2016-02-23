package it.prisma.utils.datetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

	public static String convertAsISO8601(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
	    df.setTimeZone(tz);
	    return df.format(date);
	}
	
}
