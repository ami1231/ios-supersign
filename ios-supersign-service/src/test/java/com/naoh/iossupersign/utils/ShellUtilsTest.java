package com.naoh.iossupersign.utils;

import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import com.naoh.iossupersign.service.udid.UDIDBSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShellUtilsTest {

    @Autowired
    private IpaPackageBSService ipaPackageBSService;

    @Autowired
    private UDIDBSService udidbsService;

    @Test
    public void testRunShell() throws IOException, InterruptedException {

       IpaPackagePO ipaPackagePO =  ipaPackageBSService.selectIpaByDownloadId("a77de2b1767f1d92919c7e5f6bf1a753");
        String filePath  = udidbsService.getMobileConfig(ipaPackagePO);

        URL url = this.getClass().getClassLoader().getResource("sh/");
        String comd = "openssl smime -sign -in $1 -out $2 -signer $3 -certfile $4 -outform der -nodetach";
        comd=comd.replace("$1",filePath)
                .replace("$2",url.getPath()+"udid123.mobileconfig")
                .replace("$3",url.getPath()+"InnovCertificates.pem")
                .replace("$4",url.getPath()+"root.crt.pem");

        System.out.println(comd);
        ShellUtils.run(comd);
    }

}
