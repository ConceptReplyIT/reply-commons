package it.prisma.utils.mailer.exceptions;

public class MailContentException extends RuntimeException {

	public static final String CONTEXT_ERROR_MSG = "An error accoured while creating email context. "
			+ "Please, check input data for this type of message";
	public static final String TEMPLATE_ERROR_MSG = "An error accoured while creating email template.";
	public static final String CONTENT_ERROR_MSG = "An error accoured while creating email content.";

	private static final long serialVersionUID = 7810226642069590283L;
	private String message;

	public MailContentException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
