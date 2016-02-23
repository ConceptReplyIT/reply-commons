package it.prisma.utils.mailer.content;

import java.util.Map;

public interface ContentFactory {
	
	public String getSubject();
	
	public String getFrom();

	public String getBody(Map<String, String> data);

}
