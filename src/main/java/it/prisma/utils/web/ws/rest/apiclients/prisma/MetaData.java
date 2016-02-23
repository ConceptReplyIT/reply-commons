package it.prisma.utils.web.ws.rest.apiclients.prisma;

import it.prisma.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;

public class MetaData {

    private BaseRestResponseResult baseRestResponseResult;

    public BaseRestResponseResult getBaseRestResponseResult() {
	return baseRestResponseResult;
    }

    public void setBaseRestResponseResult(BaseRestResponseResult baseRestResponseResult) {
	this.baseRestResponseResult = baseRestResponseResult;
    }

}