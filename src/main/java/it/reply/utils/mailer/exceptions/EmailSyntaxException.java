/**
 * 
 */
package it.reply.utils.mailer.exceptions;

public class EmailSyntaxException extends RuntimeException {
	
	private static final long serialVersionUID = 5382009875083934207L;

	public EmailSyntaxException() {
		super("Please, check the syntax of specified email address.");
	}

}
