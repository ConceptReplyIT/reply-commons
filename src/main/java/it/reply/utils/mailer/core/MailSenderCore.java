package it.reply.utils.mailer.core;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.reply.utils.mailer.exceptions.EmailSyntaxException;
import it.reply.utils.misc.PropertiesReader;
import it.reply.utils.validation.RegularExpressionList;

public class MailSenderCore {

	private static final Logger LOG = LogManager
			.getLogger(MailSenderCore.class);

	private boolean debug = false;
	private Properties properties;

	public MailSenderCore() throws IOException {
		this.setProperties(PropertiesReader.read("mailer.properties"));
	}

	public MailSenderCore(Properties properties) {
		this.setProperties(properties);
	}

	/**
	 * Sends an email to a given address with a given subject and a given
	 * content body.
	 * 
	 * @param to
	 *            Receiver address.
	 * @param from
	 *            Sender address.
	 * @param subject
	 *            Mail subject.
	 * @param body
	 *            Mail content body (plain text or rich text).
	 * @throws MessagingException
	 * @throws EmailSyntaxException
	 */
	public void send(final String to, final String from, final String subject,
			final String body) throws MessagingException, EmailSyntaxException {

		// Verify the correctness of invariants
		if (emailValidation(to) && emailValidation(from)) {

			String debugUsername = null;
			String debugPassword = null;

			// Check and set up SMTP authentication
			Authenticator authenticator = null;
			if (Boolean.valueOf(this.properties.getProperty("mail.smtp.auth"))) {
				final String username = properties
						.getProperty("mail.smtp.auth.username");
				final String pwd = this.properties
						.getProperty("mail.smtp.auth.pwd");
				debugUsername = username;
				debugPassword = pwd;
				authenticator = new Authenticator() {
					private PasswordAuthentication pa = new PasswordAuthentication(
							username, pwd);

					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return pa;
					}
				};
			}

			// Set up the session
			Session session = Session.getInstance(this.properties,
					authenticator);
			session.setDebug(debug);

			// Set up the message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject(subject);
			message.setSentDate(new Date());
			if (Boolean.valueOf(this.properties
					.getProperty("prisma.mailer.html"))) {
				// HTML content
				Multipart multipart = new MimeMultipart("alternative");
				MimeBodyPart htmlPart = new MimeBodyPart();
				String htmlContent = body;
				htmlPart.setContent(htmlContent, "text/html");
				multipart.addBodyPart(htmlPart);
				message.setContent(multipart);
			} else {
				message.setText(body);
			}

			LOG.debug(String
					.format("Sending email: [Connection Properties=%s] [Auth: Username=%s, Password=%s] [Message=%s]",
							this.properties, debugUsername, debugPassword,
							message));

			Transport.send(message);
		} else
			throw new EmailSyntaxException();
	}

	private boolean emailValidation(String email) {
		String emailPattern = RegularExpressionList.EMAIL_PATTERN;
		if (email.matches(emailPattern))
			if (Boolean.valueOf(this.properties
					.getProperty("prisma.mailer.mx.check")))
				; // if (MXRecord(email));
			else
				return true;
		return false;
	}

	// Check MX Record
	// private static boolean MXRecord(String address)
	// {
	// try {
	//
	// // Get the domain part from the address and check if it has MX records
	// return !getDomainMXRecords(getDomainFromAddress(address)).isEmpty();
	// } catch (NamingException ex) {
	// LogManager.getLogger(MailSenderCore.class.getName()).log(Level.SEVERE,
	// null, ex);
	// return false;
	// } catch (Exception ex) {
	// LogManager.getLogger(MailSenderCore.class.getName()).log(Level.SEVERE,
	// null, ex);
	// return false;
	// }
	//
	// }
	//
	// private static String getDomainFromAddress(String address) {
	//
	// // Find the separator for the domain name
	// int pos = address.indexOf('@');
	//
	// // If the address does not contain an '@', it's not valid
	// if (pos == -1) {
	// return null;
	// }// Isolate the domain/machine name
	// else {
	// return address.substring(++pos);
	// }
	// }
	//
	// private static List<String> getDomainMXRecords(String hostName) throws
	// NamingException {
	//
	// // Perform a DNS lookup for MX records in the domain
	// Properties env = new Properties();
	// env.put("java.naming.factory.initial","com.sun.jndi.dns.DnsContextFactory");
	//
	// DirContext ictx = new InitialDirContext(env);
	// Attributes attrs = ictx.getAttributes(hostName, new String[]{"MX"});
	//
	// // first query our dns cache to see if we have a record for that domain
	// // trying to save bandwidth
	//
	// Attribute attr = attrs.get("MX");
	//
	// // if we don't have an MX record, try the machine itself
	// if ((attr == null) || (attr.size() == 0)) {
	// attrs = ictx.getAttributes(hostName, new String[]{"A"});
	// attr = attrs.get("A");
	// if (attr == null) {
	// throw new NamingException("No MX records for '" + hostName + "'");
	// } else {
	// System.out.println(attr);
	// }
	//
	// }
	//
	// // If the above exception is not thrown it means we have a list of MX
	// records
	// // to try, so we return them as an array
	//
	// ArrayList<String> res = new ArrayList<String>();
	// NamingEnumeration<?> en = attr.getAll();
	//
	// while (en.hasMore()) {
	// String mailhost;
	// String x = (String) en.next();
	//
	// String f[] = x.split(" ");
	// if (f.length == 1) {
	// mailhost = f[0];
	// } else if (f[1].endsWith(".")) {
	// mailhost = f[1].substring(0, (f[1].length() - 1));
	// } else {
	// mailhost = f[1];
	// }
	// res.add(mailhost);
	// }
	//
	// return res;
	// }

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
