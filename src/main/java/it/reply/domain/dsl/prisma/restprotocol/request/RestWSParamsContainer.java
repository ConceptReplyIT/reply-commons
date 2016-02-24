package it.reply.domain.dsl.prisma.restprotocol.request;

/**
 * This class is only a wrapper for RestWS common parameters:
 * <ul>
 * <li>
 * Pagination ({@link RestWSPaginationParams})</li>
 * <li>
 * Ordering ({@link RestWSOrderingParams})</li>
 * <li>
 * Filtering</li>
 * </ul>
 * <br/>
 * 
 * @author l.biava
 * 
 */
public class RestWSParamsContainer {
	private RestWSOrderingParams orderingParams;
	private RestWSPaginationParams paginationParams;
	private RestWSFilteringParams filteringParams;
	private boolean disabled = false;

	public RestWSParamsContainer() {

	}

	public RestWSParamsContainer(boolean disabled) {
		this.disabled = disabled;
	}

	public RestWSParamsContainer(RestWSOrderingParams orderingParams, RestWSPaginationParams paginationParams,
			RestWSFilteringParams filteringParams) {
		super();
		this.orderingParams = orderingParams;
		this.paginationParams = paginationParams;
		this.filteringParams = filteringParams;
	}

	public static RestWSParamsContainer empty() {
		return new RestWSParamsContainer(new RestWSOrderingParams(), new RestWSPaginationParams(200L, 0L),
				new RestWSFilteringParams());
	}

	public static RestWSParamsContainer disabled() {
		return new RestWSParamsContainer(true);
	}

	public boolean isDisabled() {
		return disabled;
	}

	public RestWSOrderingParams getOrderingParams() {
		return orderingParams;
	}

	public RestWSPaginationParams getPaginationParams() {
		return paginationParams;
	}

	public RestWSFilteringParams getFilteringParams() {
		return filteringParams;
	}

	public void setOrderingParams(RestWSOrderingParams orderingParams) {
		this.orderingParams = orderingParams;
	}

	public void setPaginationParams(RestWSPaginationParams paginationParams) {
		this.paginationParams = paginationParams;
	}

	public void setFilteringParams(RestWSFilteringParams filteringParams) {
		this.filteringParams = filteringParams;
	}

}