package com.naoh.iossupersign.enums;

public enum AppleApiEnum {

    /**
     * "取得帳號設備"
     */
    ACCOUNT_DEVICE_API("https://api.appstoreconnect.apple.com/v1/devices"),;

    private String apiPath;

    AppleApiEnum(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getApiPath(){
        return apiPath;
    }

}
