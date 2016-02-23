package it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestWSOrderingParams {

	public static final String QUERY_PARAM_ORDERBY = "order";

	private List<OrderSpecification> orderSpecs;

	public RestWSOrderingParams() {
		this.orderSpecs = new ArrayList<OrderSpecification>();
	}
	
	public RestWSOrderingParams(List<OrderSpecification> orderSpecs) {
		this();
		if (orderSpecs == null) {
			throw new IllegalArgumentException("orderSpecs must not be null");
		} else {
			this.orderSpecs = orderSpecs;
		}
	}
	public List<OrderSpecification> getOrderSpecs() {
		return orderSpecs;
	}
	
	public String getOrderSpecsAsCSV() {
		StringBuilder sb = new StringBuilder();
		Iterator<OrderSpecification> it = orderSpecs.iterator();
		while (it.hasNext()) {
			sb.append(it.next().toString());
			if (it.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

}
