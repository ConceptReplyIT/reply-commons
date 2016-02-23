package it.prisma.domain.dsl.prisma;

public enum OrganizationErrorCode implements ErrorCode {

	ORG_SERVER_ERROR(1500, "Server error."),
	ORG_ORGANIZATION_NOT_FOUND(1404, "Organization not found."), 
	ORG_BAD_REQUEST(1400, "Bad request.");

	private final int code;
	private final String description;
	private final int httpStatusCode;
	
	private OrganizationErrorCode(int code, String description) {
		this.code = code;
		this.description = description;
		this.httpStatusCode=500;
	}
	
	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	
	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorCode lookupFromCode(int errorCode) {
		for (OrganizationErrorCode e : values()) {
			if (e.code == errorCode) {
				return e;
			}
		}
		return null;
	}

	@Override
	public ErrorCode lookupFromName(String errorName) {
		for (OrganizationErrorCode e : values()) {
			if (errorName.equals(e.name())) {
				return e;
			}
		}
		return null;
	}

}
