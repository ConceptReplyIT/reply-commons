package it.reply.utils.web.ws.rest.apiencoding;

import it.reply.domain.dsl.prisma.restprotocol.Meta;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;

public class MetaData<RestResponseResult extends BaseRestResponseResult<R, O>, R, O> {

  private RestResponseResult restResponseResult;
  private Meta meta;

  public RestResponseResult getRestResponseResult() {
    return restResponseResult;
  }

  public void setRestResponseResult(RestResponseResult restResponseResult) {
    this.restResponseResult = restResponseResult;
  }

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

}