package it.prisma.utils.mailer.content.accounting;

import it.prisma.utils.mailer.content.ContentFactorySkeleton;
import it.prisma.utils.mailer.exceptions.MailContentException;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class WorkgroupMembershipToConfirmContentFactory extends ContentFactorySkeleton {

	private static final String TEMPLATE_NAME = "WorkgroupMembershipConfirmation.vm";

	private final String route;
	private final String subject;

	public WorkgroupMembershipToConfirmContentFactory(Properties properties) throws URISyntaxException {
		super(properties);
		route = new StringBuilder().append(
				properties.getProperty("prisma.webui.accounting.workgroup-membership.confirm.route")).toString();
		subject = properties.getProperty("prisma.mailconfig.accounting.workgroup-membership.confirm.subject");
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
			String confirmationRequestURI = buildURI(route, data.get("workgroupId")).toASCIIString();
			context.put("email", data.get("email"));
			context.put("fullName", data.get("fullName"));
			context.put("workgroupName", data.get("workgroupName"));
			context.put("workgroupId", data.get("workgroupId"));
			context.put("confirmationRequestURI", confirmationRequestURI);
			context.put("support", super.support);
		} catch (Exception e) {
			context = null;
			throw new MailContentException(MailContentException.CONTEXT_ERROR_MSG);
		}
		return context;
	}

}
