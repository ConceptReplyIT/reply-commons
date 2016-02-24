package it.reply.utils.misc;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTrace {

	public static String getStackTraceToString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
