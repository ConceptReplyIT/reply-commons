package it.reply.utils.authTokenManagement;

import java.util.Date;

public class AuthTokenManagementUtil {

	
	public static boolean isTokenValid(Date expiresAtDate) {
		return (!new Date().after(expiresAtDate));
		
	}
}
