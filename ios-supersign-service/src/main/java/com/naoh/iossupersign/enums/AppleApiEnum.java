package com.naoh.iossupersign.enums;

import org.springframework.http.HttpMethod;

public enum AppleApiEnum {

    /**
     * "Deviced-API"
     */
    LIST_DEVICE_API("https://api.appstoreconnect.apple.com/v1/devices",HttpMethod.GET),
    REGISTER_NEW_DEVICE_API("https://api.appstoreconnect.apple.com/v1/devices",HttpMethod.POST),
    /**
     * BundelId-API
     */
    REGISTER_NEW_BUNDLEID_API("https://api.appstoreconnect.apple.com/v1/bundleIds",HttpMethod.POST),

    /**
     * Profile-API
     */
    CREATE_PROFILE_API("https://api.appstoreconnect.apple.com/v1/profiles", HttpMethod.POST),

    /**
     * Certificates-API
     */
    NEW_CERTIFICATES_API("https://api.appstoreconnect.apple.com/v1/certificates", HttpMethod.POST),
    ;


    private String apiPath;

    private HttpMethod httpMethod;

    AppleApiEnum(String apiPath,HttpMethod httpMethod) {

        this.apiPath = apiPath;
        this.httpMethod = httpMethod;
    }

    public String getApiPath(){
        return apiPath;
    }

    public HttpMethod getHttpMethod(){
        return httpMethod;
    }
}
