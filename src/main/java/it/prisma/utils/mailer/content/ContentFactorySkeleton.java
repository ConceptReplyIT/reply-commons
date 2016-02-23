package it.prisma.utils.mailer.content;

import it.prisma.utils.mailer.exceptions.MailContentException;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Abstract class. It defines the skeletons of the algorithms that generate mail
 * content (sender address, subject, body), according to the <em>Template
 * Method</em> design pattern.
 * 
 * @author v.denotaris
 *
 */
abstract public class ContentFactorySkeleton implements ContentFactory {

	protected static final String TEMPLATES_PATH = "it/prisma/utils/mailer/templates/";
	protected static final String ENCODING = "UTF-8";
	private VelocityEngine velocityEngine;

	protected final String uriScheme;
	protected final String uriAuthority;
	protected final String from;
	protected final String support;
	protected final String subjectPrefix;

	public ContentFactorySkeleton(Properties properties) {
		this.setTemplateEngine(new VelocityEngine());

		uriScheme = properties.getProperty("prisma.webui.uri.scheme");
		uriAuthority = properties.getProperty("prisma.webui.uri.authority");
		from = new StringBuilder().append(properties.getProperty("prisma.mailconfig.accounting.address")).toString();
		support = new StringBuilder().append(properties.getProperty("prisma.mailconfig.accounting.support.address"))
				.toString();
		subjectPrefix = properties.getProperty("prisma.mailconfig.subject.prefix");
	}

	protected VelocityEngine getTemplateEngine() {
		return velocityEngine;
	}

	protected void setTemplateEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
		this.velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		this.velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		this.velocityEngine.init();
	}

	/**
	 * Adds subject prefix.
	 */
	public String getSubject() {
		return subjectPrefix + createSubject();
	}

	public String getFrom() {
		return createFrom();
	}

	// Standardize the skeletons of the algorithms in a "template" method
	public String getBody(Map<String, String> data) {

		// Set up mail template and context
		Template template = createTemplate();
		VelocityContext context = createContext(data);

		// Merge template and context into string
		try {
			return mergeTemplateAndContextIntoString(template, context);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new MailContentException(MailContentException.CONTENT_ERROR_MSG);
		}
	}

	// Hooks requiring peculiar implementation
	abstract protected String createSubject();

	protected String createFrom() {
		return this.from;
	}

	abstract protected Template createTemplate();

	abstract protected VelocityContext createContext(Map<String, String> data);

	// Common implementations of individual steps are defined in base class
	protected String mergeTemplateAndContextIntoString(Template template, VelocityContext context) throws IOException,
			NullPointerException {
		String result = null;
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		result = writer.toString();
		writer.close();
		return result;
	}

	protected URI buildURI(String route, Object... params) throws URISyntaxException {
		return new URI(uriScheme + "://" + uriAuthority + String.format("/" + route, params));
	}
}
