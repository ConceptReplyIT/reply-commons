package it.prisma.domain.dsl.prisma;

public class WSPathParam {

    public static final String WORKGROUP_ID = "workgroupId";
    public static final String PRISMA_ZONE_ID = "prismaZoneId";
    public static final String DNS_ZONE_ID = "dnsZoneId";
    public static final String IAAS_ZONE_ID = "iaasZoneId";
    public static final String RES_ID = "resId";    
    public static final String APP_ID = "appId";
    public static final String APP_ENV_ID = "envId";

    public static final String APPAAS = "appaas";
    public static final String BIAAS = "biaas";
    public static final String DBAAS = "dbaas";
    public static final String KEYS = "keys";
    public static final String MQAAS = "mqaas";
    public static final String VMAAS = "vmaas";
    public static final String BPMAAS = "bpmaas";
    public static final String ODAAS = "odaas";
    public static final String APP_ENVIRONMENT = "environments";

    public static final String SERVICE_BASE = "/workgroups/{" + WORKGROUP_ID + "}/zones/{" + PRISMA_ZONE_ID + "}";

    public static final String SERVICE_APPAAS = SERVICE_BASE + "/paas/" + APPAAS;

    public static final String SERVICE_APP_REPO = SERVICE_BASE + "/apprepo/appsrcfiles";

    public static final String SERVICE_BIAAS = SERVICE_BASE + "/paas/" + BIAAS;
    public static final String SERVICE_DBAAS = SERVICE_BASE + "/paas/" + DBAAS;
    public static final String SERVICE_KEYAAS = SERVICE_BASE + "/iaas/" + KEYS;

    public static final String SERVICE_MQAAS = SERVICE_BASE + "/paas/mqaas";
    public static final String SERVICE_BPMAAS = SERVICE_BASE + "/paas/bpmaas";
    public static final String SERVICE_ODAAS = SERVICE_BASE + "/paas/odaas";

    public static final String SERVICE_NETWORKAAS = SERVICE_BASE + "/iaas/networks";

    public static final String SERVICE_VMAAS = SERVICE_BASE + "/iaas/vmaas";
    
    public static final String SERVICE_DNSAAS = SERVICE_BASE + "/paas/dnsaas";

    public static final String NAME_AVAILABLE = "/name/available";
    
    public static final String APPaaSEnvAppSrcFile_REST_URL = WSPathParam.SERVICE_APPAAS
			+ "/{"+APP_ID+"}/"+APP_ENVIRONMENT+"/{"+APP_ENV_ID+"}/appsrcfile";
}
