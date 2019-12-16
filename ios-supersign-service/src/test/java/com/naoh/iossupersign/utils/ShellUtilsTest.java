package com.naoh.iossupersign.utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class ShellUtilsTest {

    @Test
    public void testRunShell() throws IOException, InterruptedException {
        URL url = this.getClass().getClassLoader().getResource("sh/");
        String comd = "openssl smime -sign -in $1 -out $2 -signer $3 -certfile $4 -outform der -nodetach";
        comd=comd.replace("$1",url.getPath()+"udid_temp.mobileconfig")
                .replace("$2",url.getPath()+"udid123.mobileconfig")
                .replace("$3",url.getPath()+"InnovCertificates.pem")
                .replace("$4",url.getPath()+"root.crt.pem")
                ;
                                
        System.out.println(comd);
        String[] env = new String[]{
                "bash", "-c",
                url.getPath()+"mobileconfig.sh",
                url.getPath()+"udid_temp.mobileconfig",
                url.getPath()+"udid123.mobileconfig",
                url.getPath()+"InnovCertificates.pem",
                url.getPath()+"root.crt.pem"

        };
        ShellUtils.run(comd,null);
    }

}
