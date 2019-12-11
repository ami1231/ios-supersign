package com.naoh.iossupersign.thridparty;

import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.junit.Test;

import java.util.List;

public class AppleApiServiceTest {

    AppleApiService appleApiService = new AppleApiService();
    @Test
    public void testGetNumberOfAvailableDevices(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("69a6de94-ff66-47e3-e053-5b8c7c11a4d1").p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg4CteTCBEHO10Zioa\n" +
                "kJ40Dem5meAQgB7d3+JwD0l/DragCgYIKoZIzj0DAQehRANCAAQ2VAOLe5wK6cHP\n" +
                "XSd74Zbm5SWE5ITdBByJ2Ib4r/yADgr2KvSTqcbR27b4tRH0SBMy4vwOmdypZbB2\n" +
                "eYJZaJfb").kid("5Y6JL3TGX8").build();
        List<AppleResultDTO> appleResultDTOS =  appleApiService.getNumberOfAvailableDevices(authorizeBO);
        System.out.println(appleResultDTOS);

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
    public void testRegisterNewBundleId(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("69a6de94-ff66-47e3-e053-5b8c7c11a4d1").p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg4CteTCBEHO10Zioa\n" +
                "kJ40Dem5meAQgB7d3+JwD0l/DragCgYIKoZIzj0DAQehRANCAAQ2VAOLe5wK6cHP\n" +
                "XSd74Zbm5SWE5ITdBByJ2Ib4r/yADgr2KvSTqcbR27b4tRH0SBMy4vwOmdypZbB2\n" +
                "eYJZaJfb").kid("5Y6JL3TGX8").build();
        appleApiService.registerNewBundleId(authorizeBO);
    }

    @Test
    public void testUdidProfile() {
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("c36c96df-84bb-4a84-9eb7-dc4e7dbe3f76")
                .p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgncr4nK751LxBgcds\n" +
                        "vz/wEJVphJwraKIP5ZC7vfdear2gCgYIKoZIzj0DAQehRANCAASE0vBhg7hKWA0l\n" +
                        "5cQQNFyGAlD+Xh5dHESYyD4EZUi2emIs7SM4DR9Al0XFkdNPlhRjHxD2zTxFsbsZ\n" +
                        "nuZD7vrp")
                .kid("NMHY2UHT97").build();
        appleApiService.getMobileprovision(authorizeBO, "C4PXFAQDJJ", "7NZ2JK6P33", "G9AF68YNV6");
    }

    @Test
    public void testInsertCertificates(){
        AuthorizeBO authorizeBO = AuthorizeBO.builder().iss("c36c96df-84bb-4a84-9eb7-dc4e7dbe3f76")
                .p8("MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgncr4nK751LxBgcds\n" +
                        "vz/wEJVphJwraKIP5ZC7vfdear2gCgYIKoZIzj0DAQehRANCAASE0vBhg7hKWA0l\n" +
                        "5cQQNFyGAlD+Xh5dHESYyD4EZUi2emIs7SM4DR9Al0XFkdNPlhRjHxD2zTxFsbsZ\n" +
                        "nuZD7vrp")
                .kid("NMHY2UHT97")
                .csr("MIICjDCCAXQCAQAwRzEkMCIGCSqGSIb3DQEJARYVcGV0ZXI1NjQ1QGhvdG1haWwu\n" +
                        "Y29tMRIwEAYDVQQDDAnmtKrptLvpjrAxCzAJBgNVBAYTAlRXMIIBIjANBgkqhkiG\n" +
                        "9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtObC55ok4UKzvLOZN+LOysgD+U1dqgKJP9zT\n" +
                        "IRV493TCV8bCsVawJsF/bx2rtnUeap+hcjXjrglKti1U5gsoFE/sikO43YF8I88b\n" +
                        "Qz5n+xX44GcjZLb2uOHAWdervQA/savjrtDcIcNbQxZATO7L5SNA8I605VVEnRxo\n" +
                        "It+6ecH2QB0mjvakdBQg6vefLybQIg2/Srgo+p0anyC6Ne2WNPgb84YHl56RifWZ\n" +
                        "rSntr8ETw96kMX7yKa5Lx/TOVjyxd3X9+OIw2I9FKf3TcITq1wjJhufjL/FIdrXk\n" +
                        "sJo0B2g2L0WAuIbHK9DjPRsbgQcLryQVddEOZjoekJN9RB1HcwIDAQABoAAwDQYJ\n" +
                        "KoZIhvcNAQELBQADggEBAHosSJ6mX4uvKj2BQCt8nhcSDyFevkr8o8KYWAKeE3yr\n" +
                        "8syHQRygv4pEj0yCGawad41RteQdi7EtDWOFV4RvFh/W+c5Bl6IGYKhb5q9wsFH0\n" +
                        "xJPco1E0rO7h/gFxqvEkpKAqUfkOfwW3cIlTmcgy+RI6BNhdHuWXBIXwZCoAhj2N\n" +
                        "3s8xW8iRLQZRO8vr52Dn5f3B+/X9aoYtqdKQWcOa4ZSbf8ynR/pnLRElMJgckE/m\n" +
                        "ta+38p/VWxzUOuT/eMZSQ95jPJ25Km0u541NPvpO196wl1khqklztx86lAdoBATn\n" +
                        "Zlx1XBf0YT3Mz8iJwRhm5DczEm65gYEBP+iKfiOiN68=").build();
        appleApiService.insertCertificates(authorizeBO);
    }

}
