package it.prisma.utils.mailer;

import it.prisma.utils.mailer.content.ContentFactory;
import it.prisma.utils.mailer.content.accounting.AccountStatusNotificationContentFactory;
import it.prisma.utils.mailer.content.accounting.AccountToConfirmContentFactory;
import it.prisma.utils.mailer.content.accounting.WorkgroupMembershipStatusNotificationContentFactory;
import it.prisma.utils.mailer.content.accounting.WorkgroupMembershipToConfirmContentFactory;
import it.prisma.utils.mailer.content.accounting.WorkgroupReferentStatusNotificationContentFactory;
import it.prisma.utils.mailer.content.accounting.WorkgroupStatusNotificationContentFactory;
import it.prisma.utils.mailer.content.accounting.WorkgroupToConfirmContentFactory;
import it.prisma.utils.mailer.core.MailSenderCore;
import it.prisma.utils.mailer.exceptions.EmailSyntaxException;
import it.prisma.utils.mailer.exceptions.MailContentException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

public class MailerImpl implements Mailer {

	private final MailSenderCore mailSender;

	public MailerImpl() throws IOException {
		mailSender = new MailSenderCore();
	}

	public MailerImpl(Properties properties) {
		mailSender = new MailSenderCore(properties);
	}

	@Override
	public void sendAccountToConfirmMail(final String toEmail, final String fullName, final Long userId, final String userEmail)
			throws MessagingException, MailContentException, EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", userEmail);
		data.put("fullName", fullName);
		data.put("userId", userId.toString());
		ContentFactory contentGenerator = new AccountToConfirmContentFactory(this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}
	

	@Override
	public void sendWorkgroupToConfirmMail(final String toEmail, final String fullName, final Long workgroupId, final String workgroupName)
			throws MessagingException, MailContentException, EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", toEmail);
		data.put("fullName", fullName);
		data.put("workgroupId", workgroupId.toString());
		data.put("workgroupName", workgroupName);
		ContentFactory contentGenerator = new WorkgroupToConfirmContentFactory(this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}
	
	@Override
	public void sendWorkgroupMembershipToConfirmMail(final String toEmail, final String fullName, final Long workgroupId, final String workgroupName)
			throws MessagingException, MailContentException, EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", toEmail);
		data.put("fullName", fullName);
		data.put("workgroupId", workgroupId.toString());
		data.put("workgroupName", workgroupName);
		ContentFactory contentGenerator = new WorkgroupMembershipToConfirmContentFactory(this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}

	@Override
	public void sendAccountOperationNotificationMail(final String toEmail, final String fullName,
			final UserAccountOperation operation) throws MessagingException, MailContentException,
			EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", toEmail);
		data.put("fullName", fullName);
		String status = "";
		switch (operation) {

		case DISABLED:
			status = "disattivato";
			break;

		case ENABLED:
			status = "attivato";
			break;
		case SUSPENDED:
			status = "sospeso";
			break;
		case UNSUSPENDED:
			status = "riattivato";
			break;
		}
		data.put("status", status);
		ContentFactory contentGenerator = new AccountStatusNotificationContentFactory(this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}

	@Override
	public void sendWorkgroupMembershipNotificationMail(String toEmail, String fullName, String workgroupName,
			WorkgroupMembershipOperation operation) throws MessagingException, MailContentException,
			EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", toEmail);
		data.put("fullName", fullName);
		data.put("workgroupName", workgroupName);
		String status = "";
		switch (operation) {
		case PRIVILEGE_CHANGED:
			status = "modificato, controlla i nuovi permessi.";
			break;
		case APPROVED:
			status = "approvato";
			break;

		case UNAPPROVED:
			status = "sospeso";
			break;
		}
		data.put("status", status);
		ContentFactory contentGenerator = new WorkgroupMembershipStatusNotificationContentFactory(this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}

	@Override
	public void sendWorkgroupReferentNotificationMail(String toEmail, String fullName, String workgroupName,
			WorkgroupReferentOperation operation) throws MessagingException, MailContentException,
			EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", toEmail);
		data.put("fullName", fullName);
		data.put("workgroupName", workgroupName);
		String _operation = "";
		switch (operation) {
		case REFERENT_ADDED:
			_operation = "sei stato aggiunto ai referenti del workgroup";
			break;
		case REFERENT_REMOVED:
			_operation = "sei stato rimosso dai referenti del workgroup";
			break;

		}
		data.put("operation", _operation);
		ContentFactory contentGenerator = new WorkgroupReferentStatusNotificationContentFactory(
				this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}
	
	@Override
	public void sendWorkgroupStatusNotificationMail(String toEmail, String fullName, String workgroupName,
			WorkgroupStatusOperation operation) throws MessagingException, MailContentException,
			EmailSyntaxException, URISyntaxException {
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", toEmail);
		data.put("fullName", fullName);
		data.put("workgroupName", workgroupName);
		String status = "";
		switch (operation) {
		case APPROVED:
			status = "approvato";
			break;

		case UNAPPROVED:
			status = "sospeso";
			break;
		}
		data.put("status", status);
		ContentFactory contentGenerator = new WorkgroupStatusNotificationContentFactory(this.getMailSenderProperties());
		mailSender.send(toEmail, contentGenerator.getFrom(), contentGenerator.getSubject(),
				contentGenerator.getBody(data));
	}

	@Override
	public void sendEmail(final String toEmail, final String fromEmail, final String subject, final String body)
			throws MessagingException, EmailSyntaxException {
		mailSender.send(toEmail, fromEmail, subject, body);
	}

	protected Properties getMailSenderProperties() {
		return mailSender.getProperties();
	}

}