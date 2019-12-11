package com.naoh.iossupersign.thridparty;

import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.junit.Test;

public class AppleApiServiceTest {

    AppleApiService appleApiService = new AppleApiService();
    @Test
    public void testGetNumberOfAvailableDevices(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("c36c96df-84bb-4a84-9eb7-dc4e7dbe3f76")
                .p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgncr4nK751LxBgcds\n" +
                        "vz/wEJVphJwraKIP5ZC7vfdear2gCgYIKoZIzj0DAQehRANCAASE0vBhg7hKWA0l\n" +
                        "5cQQNFyGAlD+Xh5dHESYyD4EZUi2emIs7SM4DR9Al0XFkdNPlhRjHxD2zTxFsbsZ\n" +
                        "nuZD7vrp")
                .kid("NMHY2UHT97").build();
        appleApiService.getNumberOfAvailableDevices(authorizeBO);
    }

    @Test
    public void testUdidProfile(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("c36c96df-84bb-4a84-9eb7-dc4e7dbe3f76")
                .p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgncr4nK751LxBgcds\n" +
                        "vz/wEJVphJwraKIP5ZC7vfdear2gCgYIKoZIzj0DAQehRANCAASE0vBhg7hKWA0l\n" +
                        "5cQQNFyGAlD+Xh5dHESYyD4EZUi2emIs7SM4DR9Al0XFkdNPlhRjHxD2zTxFsbsZ\n" +
                        "nuZD7vrp")
                .kid("NMHY2UHT97").build();
    }

}
