package it.prisma.utils.mailer.content.accounting;

import it.prisma.utils.mailer.content.ContentFactorySkeleton;
import it.prisma.utils.mailer.exceptions.MailContentException;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class AccountStatusNotificationContentFactory extends ContentFactorySkeleton {

	private static final String TEMPLATE_NAME = "AccountStatus.vm";

	private final String subject;

	public AccountStatusNotificationContentFactory(Properties properties) throws URISyntaxException {
		super(properties);
		subject = properties.getProperty("prisma.mailconfig.accounting.notification.account-status.subject");
	}

	@Override
	protected String createSubject() {
		return this.subject;
	}

	@Override
	protected Template createTemplate() {
		return super.getTemplateEngine().getTemplate(ContentFactorySkeleton.TEMPLATES_PATH + TEMPLATE_NAME,
				ContentFactorySkeleton.ENCODING);
	}

	@Override
	protected VelocityContext createContext(Map<String, String> data) throws MailContentException {
		VelocityContext context = new VelocityContext();
		try {
			context.put("email", data.get("email"));
			context.put("fullName", data.get("fullName"));
			context.put("status", data.get("status"));
			context.put("support", super.support);
		} catch (Exception e) {
			context = null;
			throw new MailContentException(MailContentException.CONTEXT_ERROR_MSG);
		}
		return context;
	}

}
