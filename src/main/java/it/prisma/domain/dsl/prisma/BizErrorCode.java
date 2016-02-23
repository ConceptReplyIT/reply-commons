package it.prisma.domain.dsl.prisma;

/**
 * BizLib error codes.
 * 
 * @author l.biava
 * 
 */
public enum BizErrorCode implements ErrorCode {
	BIZ_ACCOUNT_USER_NOT_FOUND(0, "User not found."), BIZ_ACCOUNT_USER_ALREADY_REGISTERED(
			1, "User already registered."), BIZ_PAAS_APPAAS_APPREPO_GENERIC_ERROR(
			101, "A generic error happened.");

	private final int code;
	private final String description;
	private final int httpStatusCode;
	
	private BizErrorCode(int code, String description) {
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

	public String getName() {
		return name();
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}

	public BizErrorCode lookupFromCode(int errorCode) {
		for (BizErrorCode e : values()) {
			if (e.code == errorCode) {
				return e;
			}
		}
		return null;
	}

	public BizErrorCode lookupFromName(String errorName) {
		for (BizErrorCode e : values()) {
			if (errorName.equals(e.name())) {
				return e;
			}
		}
		return null;
	}
}