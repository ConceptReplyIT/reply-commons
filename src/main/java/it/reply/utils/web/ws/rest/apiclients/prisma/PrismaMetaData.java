package it.reply.utils.web.ws.rest.apiclients.prisma;

import it.reply.domain.dsl.prisma.restprotocol.Meta;
import it.reply.domain.dsl.prisma.restprotocol.PrismaResponseWrapper;
import it.reply.utils.web.ws.rest.apiencoding.MetaData;

public class PrismaMetaData<APIResponseType> extends
    MetaData<PrismaRestResponseResult<APIResponseType>, PrismaResponseWrapper<APIResponseType>, String> {

  public PrismaResponseWrapper<APIResponseType> getPrismaResponseWrapper() {
    return this.getRestResponseResult() != null ? this.getRestResponseResult().getResult() : null;
  }

  @Override
  public Meta getMeta() {
    return getPrismaResponseWrapper() != null ? getPrismaResponseWrapper().getMeta() : null;
  }

  @Override
  public void setMeta(Meta meta) {
    throw new UnsupportedOperationException("Meta are retrieved from ResponseWrapper");
  }

}