package it.reply.domain.dsl.prisma.restprotocol.request;

import java.util.Objects;

public class FilteringSpecification {

	public enum OperatorType {
		// @formatter:off
		NOT_EQUAL("!="), GREATER(">="), GREATER_EQUAL(">="), LESS("<="), LESS_EQUAL("<="), EQUALS("="), CONTAINS("%");
		// @formatter:on

		private String operator;

		private OperatorType(String operator) {
			this.operator = operator;
		}

		public String getOperator() {
			return operator;
		}

	}

	private String field;
	private OperatorType operator;
	private String value;

	public FilteringSpecification(String field, OperatorType operator, Object value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value.toString();
	}
	
	public FilteringSpecification(String field, OperatorType operator, String value) {
		super();
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		Objects.requireNonNull(field, "The field must not be null");
		this.field = field;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return field + operator.getOperator() + value;
	}
}
