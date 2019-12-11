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
    public void testRegisterNewDevice(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("69a6de94-ff66-47e3-e053-5b8c7c11a4d1").p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg4CteTCBEHO10Zioa\n" +
                "kJ40Dem5meAQgB7d3+JwD0l/DragCgYIKoZIzj0DAQehRANCAAQ2VAOLe5wK6cHP\n" +
                "XSd74Zbm5SWE5ITdBByJ2Ib4r/yADgr2KvSTqcbR27b4tRH0SBMy4vwOmdypZbB2\n" +
                "eYJZaJfb").kid("5Y6JL3TGX8").build();
        appleApiService.registerNewDevice("16dcbc19dd33a942ef5b23f15be282bee8ca6bd3",authorizeBO);

    }

    @Test
<<<<<<< HEAD
    public void testUdidProfile(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("c36c96df-84bb-4a84-9eb7-dc4e7dbe3f76")
                .p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgncr4nK751LxBgcds\n" +
                        "vz/wEJVphJwraKIP5ZC7vfdear2gCgYIKoZIzj0DAQehRANCAASE0vBhg7hKWA0l\n" +
                        "5cQQNFyGAlD+Xh5dHESYyD4EZUi2emIs7SM4DR9Al0XFkdNPlhRjHxD2zTxFsbsZ\n" +
                        "nuZD7vrp")
                .kid("NMHY2UHT97").build();
=======
    public void testRegisterNewBundleId(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("69a6de94-ff66-47e3-e053-5b8c7c11a4d1").p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg4CteTCBEHO10Zioa\n" +
                "kJ40Dem5meAQgB7d3+JwD0l/DragCgYIKoZIzj0DAQehRANCAAQ2VAOLe5wK6cHP\n" +
                "XSd74Zbm5SWE5ITdBByJ2Ib4r/yADgr2KvSTqcbR27b4tRH0SBMy4vwOmdypZbB2\n" +
                "eYJZaJfb").kid("5Y6JL3TGX8").build();
        appleApiService.registerNewBundleId(authorizeBO);

>>>>>>> 2cbb8d82503df1fd8e17b4ed6b3037c1668bb55b
    }

}
