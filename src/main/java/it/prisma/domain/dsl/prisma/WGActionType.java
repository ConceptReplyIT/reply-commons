package it.prisma.domain.dsl.prisma;

//@formatter:off
public enum WGActionType {
	EMAIL_SEND,
	EMAIL_ACCOUNT_CREATE,
	EMAIL_ACCOUNT_READ,
	EMAIL_ACCOUNT_DELETE,
	EMAIL_ACCOUNT_LIST,
	EMAIL_ACCOUNT_EDIT_CREDENTIALS,
	SMS_STATUS_READ,
	SMS_STATUS_EDIT,
	SMS_LIST,
	SMS_SEND,
	SMS_BILLING_READ,
	SMS_NOTIFICATIONS_READ,
	SMS_NOTIFICATIONS_EDIT,
	CA_LIST,
	CA_CREATE,
	CA_REVOKE,
	CA_READ,
	KEY_LIST,
	KEY_READ,
	KEY_CREATE,
	KEY_DELETE,
	IMAGE_LIST,
	IMAGE_READ,
	IMAGE_CREATE,
	IMAGE_DELETE,
	NETWORK_LIST,
	NETWORK_READ,
	NETWORK_CREATE,
	NETWORK_DELETE,
	OBJ_STORAGE_LIST,
	OBJ_STORAGE_READ,
	OBJ_STORAGE_CREATE,
	OBJ_STORAGE_DELETE,
	PAAS_SVC_LIST,
	PAAS_SVC_READ,
	PAAS_SVC_CREATE,
	PAAS_SVC_DELETE,
	PAAS_SVC_UPDATE,
	TOKEN_READ,
	TOKEN_LIST,
	TOKEN_CREATE,
	TOKEN_DELETE,
	ACCOUNTING_WG_MEMBERSHIP_ADD,
	ACCOUNTING_WG_MEMBERSHIP_APPLICATION_LIST, 
	ACCOUNTING_WG_REFERENT_ADD, 
	ACCOUNTING_WG_MEMBERSHIP_APPROVE,
	ACCOUNTING_WG_MEMBERSHIP_UNAPPROVE,
	ACCOUNTING_WG_MEMBERSHIP_APPROVED_LIST, 
	ACCOUNTING_WG_MEMBERSHIP_LIST, 
	ACCOUNTING_WG_MEMBERSHIP_READ,
	ACCOUNTING_WG_PRIVILEGE_READ, 
	ACCOUNTING_WG_PRIVILEGE_UPDATE, 
	ACCOUNTING_WG_READ(true), 
	ACCOUNTING_WG_REFERENT_DELETE, 
	ACCOUNTING_WG_REFERENT_LIST, 
	ACCOUNTING_WG_MEMBERSHIP_REMOVE, 
	ACCOUNTING_WG_UPDATE, 
	ACCOUNTING_WG_USER_DELETE, 
	ACCOUNTING_WG_USER_LIST,
	VM_DELETE, 
	VM_RESTART, 
	VM_START, 
	VM_STOP, 
	APP_REPO_PRIVATE_APPS_CREATE, 
	APP_REPO_PRIVATE_APPS_READ, 
	PAAS_SVC_RESTART, 
	PAAS_SVC_START, 
	PAAS_SVC_STOP, 
	SMS_DISABLE_USER, 
	SMS_TOKEN_EDIT,
	SMS_TOKEN_READ,
	PAAS_SVC_REFRESH,
	PAAS_SVCES_REFRESH;
	
	private boolean ignoreWorkgroupApprovation = false;
	private WGActionType() {
	
	}
	
	private WGActionType(boolean ignoreWorkgroupApprovation) {
		this.ignoreWorkgroupApprovation = ignoreWorkgroupApprovation;
	}
	
	public boolean ignoreWorkgroupApprovation() {
		return ignoreWorkgroupApprovation;
	}
	
}
//@formatter:on