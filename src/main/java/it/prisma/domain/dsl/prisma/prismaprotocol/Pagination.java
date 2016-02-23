package it.prisma.domain.dsl.prisma.prismaprotocol;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "limit", "offset", "totalCount", "links" })
public class Pagination {

	@JsonProperty("limit")
	private Long limit;
	@JsonProperty("offset")
	private Long offset;
	@JsonProperty("totalCount")
	private Long totalCount;
	@JsonProperty("links")
	@JsonInclude(Include.NON_NULL)
	private Links links;

	/**
	 * 
	 * @return The limit
	 */
	@JsonProperty("limit")
	public Long getLimit() {
		return limit;
	}

	/**
	 * 
	 * @param limit
	 *            The limit
	 */
	@JsonProperty("limit")
	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Pagination withLimit(Long limit) {
		this.limit = limit;
		return this;
	}

	/**
	 * 
	 * @return The offset
	 */
	@JsonProperty("offset")
	public Long getOffset() {
		return offset;
	}

	/**
	 * 
	 * @param offset
	 *            The offset
	 */
	@JsonProperty("offset")
	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Pagination withOffset(Long offset) {
		this.offset = offset;
		return this;
	}

	/**
	 * 
	 * @return The totalCount
	 */
	@JsonProperty("totalCount")
	public Long getTotalCount() {
		return totalCount;
	}

	/**
	 * 
	 * @param totalCount
	 *            The totalCount
	 */
	@JsonProperty("totalCount")
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Pagination withTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	/**
	 * 
	 * @return The links
	 */
	@JsonProperty("links")
	public Links getLinks() {
		return links;
	}

	/**
	 * 
	 * @param links
	 *            The links
	 */
	@JsonProperty("links")
	public void setLinks(Links links) {
		this.links = links;
	}

	public Pagination withLinks(Links links) {
		this.links = links;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(limit).append(offset)
				.append(totalCount).append(links)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Pagination) == false) {
			return false;
		}
		Pagination rhs = ((Pagination) other);
		return new EqualsBuilder().append(limit, rhs.limit)
				.append(offset, rhs.offset).append(totalCount, rhs.totalCount)
				.append(links, rhs.links)
				.isEquals();
	}

}