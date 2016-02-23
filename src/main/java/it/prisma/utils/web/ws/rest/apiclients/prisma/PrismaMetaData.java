package it.prisma.utils.web.ws.rest.apiclients.prisma;

import it.prisma.domain.dsl.prisma.prismaprotocol.Meta;
import it.prisma.domain.dsl.prisma.prismaprotocol.PrismaResponseWrapper;

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