package com.naoh.iossupersign.enums;

import org.springframework.http.HttpMethod;

public enum AppleApiEnum {

    /**
     * "Deviced-API"
     */
    LIST_DEVICE_API("https://api.appstoreconnect.apple.com/v1/devices",HttpMethod.GET.name()),
    REGISTER_NEW_DEVICE_API("https://api.appstoreconnect.apple.com/v1/devices",HttpMethod.POST.name()),
    ;


    private String apiPath;

    private String httpMethod;

    AppleApiEnum(String apiPath,String httpMethod) {

        this.apiPath = apiPath;
        this.httpMethod = httpMethod;
    }

    public String getApiPath(){
        return apiPath;
    }

    public String getHttpMethod(){
        return httpMethod;
    }
}
