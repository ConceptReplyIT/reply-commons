package it.reply.utils.web.ws.rest.apiclients.prisma;

import com.fasterxml.jackson.databind.JavaType;

import it.reply.domain.dsl.prisma.restprotocol.PrismaResponseWrapper;
import it.reply.utils.web.ws.rest.apiencoding.RestMessage;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;

import javax.ws.rs.core.Response.StatusType;

public class PrismaRestResponseResult<APIResponseType>
    extends BaseRestResponseResult<PrismaResponseWrapper<APIResponseType>, String> {

  public PrismaRestResponseResult(StatusType status, PrismaResponseWrapper<APIResponseType> result,
      JavaType resultClass, RestMessage<String> originalRestMessage) {
    super(status, result, resultClass, originalRestMessage);
  }

  public PrismaRestResponseResult(StatusType status,
      BaseRestResponseResult<PrismaResponseWrapper<APIResponseType>, String> result,
      JavaType resultClass, RestMessage<String> originalRestMessage) {
    super(status, (result != null ? result.getResult() : null), resultClass, originalRestMessage);
  }
}
