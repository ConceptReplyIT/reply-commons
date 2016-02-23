package it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestWSFilteringParams {

	public static final String QUERY_PARAM_FILTERBY = "search";

	private List<FilteringSpecification> filteringSpecs;
	
	public RestWSFilteringParams() {
		this.filteringSpecs = new ArrayList<FilteringSpecification>();
	}
	
	public RestWSFilteringParams(List<FilteringSpecification> filteringSpecs) {
		this();
		if (filteringSpecs == null) {
			throw new IllegalArgumentException("filteringSpecs must not be null");
		} else {
			this.filteringSpecs = filteringSpecs;
		}
	}

	public List<FilteringSpecification> getFilteringSpecs() {
		return filteringSpecs;
	}

	public String getFilteringSpecsAsCSV() {
		StringBuilder sb = new StringBuilder();
		Iterator<FilteringSpecification> it = filteringSpecs.iterator();
		while (it.hasNext()) {
			sb.append(it.next().toString());
			if (it.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
