package it.prisma.domain.dsl.prisma;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "emailRecipient", "name", "email", "subject", "body" })
public class EmailRequest {

	@JsonProperty("emailRecipient")
	private String emailRecipient;
	@JsonProperty("name")
	private String name;
	@JsonProperty("email")
	private String email;
	@JsonProperty("subject")
	private String subject;
	@JsonProperty("body")
	private String body;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("emailRecipient")
	public String getEmailRecipient() {
		return emailRecipient;
	}

	@JsonProperty("emailRecipient")
	public void setEmailRecipient(String emailRecipient) {
		this.emailRecipient = emailRecipient;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("subject")
	public String getSubject() {
		return subject;
	}

	@JsonProperty("subject")
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty("body")
	public String getBody() {
		return body;
	}

	@JsonProperty("body")
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(emailRecipient).append(name).append(email).append(subject).append(body)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof EmailRequest) == false) {
			return false;
		}
		EmailRequest rhs = ((EmailRequest) other);
		return new EqualsBuilder().append(emailRecipient, rhs.emailRecipient).append(name, rhs.name)
				.append(email, rhs.email).append(subject, rhs.subject).append(body, rhs.body)
				.append(additionalProperties, rhs.additionalProperties).isEquals();
	}

}