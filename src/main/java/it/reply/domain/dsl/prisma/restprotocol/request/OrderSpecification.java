package it.reply.domain.dsl.prisma.restprotocol.request;

import java.util.Objects;

public class OrderSpecification {

	public enum OrderType {
		ASC, DESC
	}

	private String field;
	private OrderType order;

	public OrderSpecification(String field, OrderType order) {
		super();
		this.field = field;
		this.order = order;
	}

	public String getField() {
		return field;
	}

	public OrderType getOrder() {
		return order;
	}

	@Override
	public String toString() {
		String retString = "";
		if (order == OrderType.DESC) {
			retString = "-";
		}
		return retString + field;
	}

	public void setField(String field) {
		Objects.requireNonNull(field, "The field must not be null");
		this.field = field;
	}
}
