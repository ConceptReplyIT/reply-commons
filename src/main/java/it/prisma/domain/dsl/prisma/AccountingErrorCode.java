package it.prisma.domain.dsl.prisma;

public enum AccountingErrorCode implements ErrorCode {

	ACC_SERVER_ERROR(1500, "Server error."),
	ACC_USER_SIGNUP_CREDENTIAL_ALREADY_USED_ON_IDP(1412, "Provided credentials are already used on the IdP."),
	ACC_USER_SIGNUP_EMAIL_ALREADY_USED_ON_IDP(1413, "Provided email address is already used on the IdP."),
	ACC_USER_NOT_FOUND(1404, "User credentials not found."), 
	ACC_BAD_REQUEST(1400, "Bad request."),
	ACC_TOKEN_EXPIRED(1406, "Token expired."),
	
	ACC_WG_NOT_FOUND(2404, "Workgroup not found"),
	ACC_WG_MEMBERSHIP_NOT_FOUND(3404, "Workgroup membership not found");
	
	private final int code;
	private final String description;
	private final int httpStatusCode;
	
	private AccountingErrorCode(int code, String description) {
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
		for (AccountingErrorCode e : values()) {
			if (e.code == errorCode) {
				return e;
			}
		}
		return null;
	}

	@Override
	public ErrorCode lookupFromName(String errorName) {
		for (AccountingErrorCode e : values()) {
			if (errorName.equals(e.name())) {
				return e;
			}
		}
		return null;
	}

}
