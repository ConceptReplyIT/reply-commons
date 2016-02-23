package it.prisma.utils.mailer;

import it.prisma.utils.mailer.exceptions.EmailSyntaxException;
import it.prisma.utils.mailer.exceptions.MailContentException;

import java.net.URISyntaxException;

import javax.mail.MessagingException;

public interface Mailer {
	public enum UserAccountOperation {
		ENABLED, DISABLED, SUSPENDED, UNSUSPENDED
	}

	public enum WorkgroupMembershipOperation {
		APPROVED, UNAPPROVED, PRIVILEGE_CHANGED
	}

	public enum WorkgroupReferentOperation {
		REFERENT_ADDED, REFERENT_REMOVED
	}

	public enum WorkgroupStatusOperation {
		APPROVED, UNAPPROVED
	}

	public void sendEmail(final String toEmail, final String fromEmail, final String subject, final String body)
			throws MessagingException, EmailSyntaxException;

	public void sendAccountToConfirmMail(final String toEmail, final String fullName, final Long userId, final String userEmail)
			throws MessagingException, MailContentException, EmailSyntaxException, URISyntaxException;

	public void sendAccountOperationNotificationMail(final String toEmail, final String fullName,
			final UserAccountOperation operation) throws MessagingException, MailContentException,
			EmailSyntaxException, URISyntaxException;

	public void sendWorkgroupToConfirmMail(final String toEmail, final String fullName, final Long workgroupId,
			final String workgroupName) throws MessagingException, MailContentException, EmailSyntaxException,
			URISyntaxException;

	public void sendWorkgroupMembershipToConfirmMail(final String toEmail, final String fullName,
			final Long workgroupId, final String workgroupName) throws MessagingException, MailContentException,
			EmailSyntaxException, URISyntaxException;

	public void sendWorkgroupMembershipNotificationMail(final String toEmail, final String fullName,
			final String workgroupName, final WorkgroupMembershipOperation operation) throws MessagingException,
			MailContentException, EmailSyntaxException, URISyntaxException;

	public void sendWorkgroupReferentNotificationMail(final String toEmail, final String fullName,
			final String workgroupName, final WorkgroupReferentOperation operation) throws MessagingException,
			MailContentException, EmailSyntaxException, URISyntaxException;

	public void sendWorkgroupStatusNotificationMail(String toEmail, String fullName, String workgroupName,
			WorkgroupStatusOperation operation) throws MessagingException, MailContentException, EmailSyntaxException,
			URISyntaxException;

}
