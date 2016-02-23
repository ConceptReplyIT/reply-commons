package it.prisma.utils.web.ws.rest.apiclients.prisma;

import it.prisma.domain.dsl.exceptions.accounting.UnauthorizedException;
import it.prisma.domain.dsl.prisma.OrchestratorErrorCode;
import it.prisma.domain.dsl.prisma.WSPathParam;
import it.prisma.domain.dsl.prisma.prismaprotocol.Error;
import it.prisma.domain.dsl.prisma.prismaprotocol.PrismaResponseWrapper;
import it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests.RestWSFilteringParams;
import it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests.RestWSOrderingParams;
import it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests.RestWSPaginationParams;
import it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests.RestWSParamsContainer;
import it.prisma.utils.web.ws.rest.apiclients.AbstractAPIClient;
import it.prisma.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.prisma.utils.web.ws.rest.restclient.RestClient;
import it.prisma.utils.web.ws.rest.restclient.RestClientFactory;
import it.prisma.utils.web.ws.rest.restclient.RestClientFactoryImpl;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

/**
 * This class contains an abstract Prisma Rest Protocol API Client, with utility
 * methods to create requests.
 * 
 * @author l.biava
 * 
 */
public class AbstractPrismaAPIClient extends AbstractAPIClient {

	/**
	 * This class contains the headers of the PRISMA REST client. At class
	 * instantiation time the "X-Auth-Token" header is added to the headers by
	 * default. This way any PRISMA REST call using this headers class will
	 * authenticate by default.
	 * 
	 * @author g.demusso
	 * 
	 */
	public class PrismaAuthenticatedHeader extends MultivaluedMapImpl<String, Object> {

		private static final long serialVersionUID = 1597826702715479951L;

		public PrismaAuthenticatedHeader(String authToken) {
			this.add("X-Auth-Token", authToken);
		}

	}

	/**
	 * Creates a {@link AbstractPrismaAPIClient} using the default
	 * {@link RestClientFactoryImpl}.
	 * 
	 * @param baseWSUrl
	 *            The URL of Prisma BizWS.
	 * 
	 */
	public AbstractPrismaAPIClient(String baseWSUrl) {
		this(baseWSUrl, new RestClientFactoryImpl());
	}

	/**
	 * Creates a {@link AbstractPrismaAPIClient} with the given
	 * {@link RestClientFactory}.
	 * 
	 * @param baseWSUrl
	 *            The URL of Prisma BizWS.
	 * @param restClientFactory
	 *            The custom factory for the {@link RestClient}.
	 */
	public AbstractPrismaAPIClient(String baseWSUrl, RestClientFactory restClientFactory) {
		super(baseWSUrl, restClientFactory);
	}

	/**
	 * See
	 * {@link AbstractPrismaAPIClient#addRestWSParamsToQueryParams(RestWSParamsContainer, MultivaluedMap)}
	 * , default create query params.
	 * 
	 * @param params
	 * @return
	 */
	protected MultivaluedMap<String, Object> addRestWSParamsToQueryParams(RestWSParamsContainer params) {
		if (params == null)
			return null;
		return addRestWSParamsToQueryParams(params, null);
	}

	/**
	 * Adds params contained in {@link RestWSParamsContainer} to current query
	 * parameters (if given).
	 * 
	 * @param params
	 *            the {@link RestWSParamsContainer} params.
	 * @param queryParams
	 *            the already existing query params map to add
	 *            {@link RestWSParamsContainer} params to, or <tt>null</tt> to
	 *            also create it.
	 * @return
	 */
	protected MultivaluedMap<String, Object> addRestWSParamsToQueryParams(RestWSParamsContainer params,
			MultivaluedMap<String, Object> queryParams) {
		if (queryParams == null)
			queryParams = new MultivaluedHashMap<String, Object>();
		if (!params.isDisabled()) {
			if (params.getPaginationParams() != null) {
				queryParams
						.putSingle(RestWSPaginationParams.QUERY_PARAM_LIMIT, params.getPaginationParams().getLimit());
				queryParams.putSingle(RestWSPaginationParams.QUERY_PARAM_OFFSET, params.getPaginationParams()
						.getOffset());
			}
			if (params.getOrderingParams() != null && params.getOrderingParams().getOrderSpecs() != null
					&& !params.getOrderingParams().getOrderSpecs().isEmpty()) {
				queryParams.putSingle(RestWSOrderingParams.QUERY_PARAM_ORDERBY, params.getOrderingParams()
						.getOrderSpecsAsCSV());
			}
			if (params.getFilteringParams() != null && params.getFilteringParams().getFilteringSpecs() != null
					&& !params.getFilteringParams().getFilteringSpecs().isEmpty()) {
				queryParams.putSingle(RestWSFilteringParams.QUERY_PARAM_FILTERBY, params.getFilteringParams()
						.getFilteringSpecsAsCSV());
			}
		}

		return queryParams;
	}

	/**
	 * Generic comment
	 * 
	 * @param result
	 * @param meta
	 * @return
	 * @throws APIErrorException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <RETURN_TYPE> RETURN_TYPE handleResult(BaseRestResponseResult result, PrismaMetaData meta)
			throws APIErrorException {
		if (result.getStatus().getFamily() == Status.Family.SUCCESSFUL) {
			try {
				if (meta != null) {
					meta.setMeta(((PrismaResponseWrapper<?>) result.getResult()).getMeta());
					meta.setBaseRestResponseResult(result);
					meta.setPrismaResponseWrapper(((PrismaResponseWrapper<?>) result.getResult()));
				}
				if (result.getResult() != null)
					return ((PrismaResponseWrapper<RETURN_TYPE>) result.getResult()).getResult();
				else
					return null;
			} catch (Exception e) {
				throw new APIErrorException("Unexpected response type.", e);
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	protected <REPRESENTATION_CLASS> REPRESENTATION_CLASS handleResult(BaseRestResponseResult result)
			throws APIErrorException {
		return handleResult(result, null);
	}

	/**
	 * Prefix baseWSURL.
	 * 
	 * @param url
	 * @return
	 */
	protected String buildURL(String url) {
		return baseWSUrl + url;
	}

	protected String addZone(Long zoneId) {
		return "/zones/" + zoneId;
	}

	protected String replaceZone(String url, Long zoneId) {
		return url.replace("{" + WSPathParam.PRISMA_ZONE_ID + "}", zoneId.toString());
	}

	protected String replaceWorkgroup(String url, Long workgroupId) {
		return url.replace("{" + WSPathParam.WORKGROUP_ID + "}", workgroupId.toString());
	}

	protected String replacePaaSServiceBase(String url, Long workgroupId, Long zoneId) {
		return replaceZone(replaceWorkgroup(url, workgroupId), zoneId);
	}
	// protected void selectZone(Long zoneId,
	// MultivaluedMap<String, Object> headers) {
	// headers.putSingle("X-PRISMA-ZONE", zoneId);
	// }
}
