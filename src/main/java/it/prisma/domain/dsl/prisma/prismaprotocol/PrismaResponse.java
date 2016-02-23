package it.prisma.domain.dsl.prisma.prismaprotocol;

import it.prisma.domain.dsl.prisma.ErrorCode;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.StatusType;

public class PrismaResponse {

	public static PrismaResponseBuilder status(StatusType status) {
		return new PrismaResponseBuilder(status);
	}
	
	public static <ResultType> PrismaResponseBuilder status(StatusType status,
			ResultType result) {
		return new PrismaResponseBuilder(status, result);
	}

	public static PrismaResponseBuilder status(StatusType status, Error error) {
		return new PrismaResponseBuilder(status, error);
	}

	public static PrismaResponseBuilder status(StatusType status,
			ErrorCode errorCode, String verbose) {
		return new PrismaResponseBuilder(status, errorCode, verbose);
	}

	public static class PrismaResponseBuilder {

		private final ResponseBuilder responseBuilder;

		public <ResultType> PrismaResponseBuilder(StatusType status) {
			PrismaResponseWrapper<?> prismaResponseWrapper = PrismaResponseWrapper
					.status(status).build();
			responseBuilder = Response.status(status).entity(
					prismaResponseWrapper);
		}
		
		public <ResultType> PrismaResponseBuilder(StatusType status,
				ResultType result) {
			PrismaResponseWrapper<?> prismaResponseWrapper = PrismaResponseWrapper
					.status(status, result).build();
			responseBuilder = Response.status(status).entity(
					prismaResponseWrapper);
		}

		public PrismaResponseBuilder(StatusType status, ErrorCode errorCode,
				String verbose) {
			PrismaResponseWrapper<?> prismaResponseWrapper = PrismaResponseWrapper
					.status(status, errorCode, verbose).build();
			responseBuilder = Response.status(status).entity(
					prismaResponseWrapper);
		}

		public PrismaResponseBuilder(StatusType status, Error error) {
			PrismaResponseWrapper<?> prismaResponseWrapper = PrismaResponseWrapper
					.status(status, error).build();
			responseBuilder = Response.status(status).entity(
					prismaResponseWrapper);
		}

		public ResponseBuilder build() {
			return responseBuilder;
		}

	}
}