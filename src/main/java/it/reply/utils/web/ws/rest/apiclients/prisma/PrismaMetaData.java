package it.reply.utils.web.ws.rest.apiclients.prisma;

import it.reply.domain.dsl.prisma.restprotocol.Meta;
import it.reply.domain.dsl.prisma.restprotocol.PrismaResponseWrapper;

public class PrismaMetaData extends MetaData {

    private PrismaResponseWrapper<?> prismaResponseWrapper;
    private Meta meta;

    public PrismaResponseWrapper<?> getPrismaResponseWrapper() {
	return prismaResponseWrapper;
    }

    public void setPrismaResponseWrapper(PrismaResponseWrapper<?> prismaResponseWrapper) {
	this.prismaResponseWrapper = prismaResponseWrapper;
    }

    public Meta getMeta() {
	return meta;
    }

    public void setMeta(Meta meta) {
	this.meta = meta;
    }

}