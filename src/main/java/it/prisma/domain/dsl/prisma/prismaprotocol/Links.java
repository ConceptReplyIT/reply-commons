package it.prisma.domain.dsl.prisma.prismaprotocol;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "first", "last", "next", "previous" })
public class Links {

	@JsonProperty("first")
	private Object first;
	@JsonProperty("last")
	private Object last;
	@JsonProperty("next")
	private Object next;
	@JsonProperty("previous")
	private Object previous;
	
	/**
	 * 
	 * @return The first
	 */
	@JsonProperty("first")
	public Object getFirst() {
		return first;
	}

	/**
	 * 
	 * @param first
	 *            The first
	 */
	@JsonProperty("first")
	public void setFirst(Object first) {
		this.first = first;
	}

	public Links withFirst(Object first) {
		this.first = first;
		return this;
	}

	/**
	 * 
	 * @return The last
	 */
	@JsonProperty("last")
	public Object getLast() {
		return last;
	}

	/**
	 * 
	 * @param last
	 *            The last
	 */
	@JsonProperty("last")
	public void setLast(Object last) {
		this.last = last;
	}

	public Links withLast(Object last) {
		this.last = last;
		return this;
	}

	/**
	 * 
	 * @return The next
	 */
	@JsonProperty("next")
	public Object getNext() {
		return next;
	}

	/**
	 * 
	 * @param next
	 *            The next
	 */
	@JsonProperty("next")
	public void setNext(Object next) {
		this.next = next;
	}

	public Links withNext(Object next) {
		this.next = next;
		return this;
	}

	/**
	 * 
	 * @return The previous
	 */
	@JsonProperty("previous")
	public Object getPrevious() {
		return previous;
	}

	/**
	 * 
	 * @param previous
	 *            The previous
	 */
	@JsonProperty("previous")
	public void setPrevious(Object previous) {
		this.previous = previous;
	}

	public Links withPrevious(Object previous) {
		this.previous = previous;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(first).append(last).append(next)
				.append(previous).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Links) == false) {
			return false;
		}
		Links rhs = ((Links) other);
		return new EqualsBuilder().append(first, rhs.first)
				.append(last, rhs.last).append(next, rhs.next)
				.append(previous, rhs.previous)
				.isEquals();
	}

}