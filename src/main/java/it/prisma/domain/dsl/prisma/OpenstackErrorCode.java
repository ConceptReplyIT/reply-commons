package it.prisma.domain.dsl.prisma;

/**
 * Openstack error codes.
 * 
 * @author m.bassi
 * 
 */
public enum OpenstackErrorCode implements ErrorCode {

	OPENSTACK_CONNECTION_ERROR(000, "CONNECTION ERROR"),

	OPENSTACK_GENERIC_ERROR(001, "GENERIC ERROR"),

	OPENSTACK_BAD_REQUEST(400, "BAD REQUEST"), OPENSTACK_UNAUTHORIZED(401,
			"UNAUTHORIZED"), OPENSTACK_FORBIDDEN(403, "FORBIDDEN"), OPENSTACK_TIMEOUT(
			408, "TIMEOUT"), OPENSTACK_CONFLICT(409, "CONFLICT");

	private final int code;
	private String description;
	private final int httpStatusCode;
	
	private OpenstackErrorCode(int code, String description) {
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

	public OpenstackErrorCode lookupFromCode(int errorCode) {
		for (OpenstackErrorCode e : values()) {
			if (e.code == errorCode) {
				return e;
			}
		}
		return null;
	}

	public OpenstackErrorCode lookupFromName(String errorName) {
		for (OpenstackErrorCode e : values()) {
			if (errorName.equals(e.name())) {
				return e;
			}
		}
		return null;
	}
}