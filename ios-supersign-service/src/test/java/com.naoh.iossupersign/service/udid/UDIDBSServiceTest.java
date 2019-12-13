package com.naoh.iossupersign.service.udid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UDIDBSServiceTest {

    @Autowired
    private UDIDBSService udidbsService;

    @Test
    public void testBindUdidToAppleAccount(){
        udidbsService.bindUdidToAppleAccount("fb68121e289202737dec9001ec0b790d6a821031");
    }

}
