package com.naoh.iossupersign.service.udid.impl;

import com.naoh.iossupersign.model.bo.UdidBO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.model.po.DevicePO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.service.udid.UDIDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class UDIDBSServiceImpl implements UDIDBSService {

    private final String mobileConfigPath = "udid.mobileconfig";

    private String udidTemplate;

    private Integer deviceLimit = 100;

    @Autowired
    private AppleAccountBSService appleAccountBSService;

    @Autowired
    private DeviceBSService deviceBSService;

    @PostConstruct
    public void initUDIDTemplate() throws URISyntaxException, IOException {
        udidTemplate =  new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(mobileConfigPath).toURI())));
    }


    @Override
    public String getMobileConfig(){
        UdidBO udidBO =  UdidBO.builder().payloadUUID(UUID.randomUUID().toString()).build();
        String nowUdidTemplate = udidTemplate
                .replace("@@PayloadOrganization", Objects.toString(udidBO.getPayloadOrganization(),""))
                .replace("@@PayloadDisplayName",Objects.toString(udidBO.getPayloadDisplayName(),""))
                .replace("@@PayloadUUID",udidBO.getPayloadUUID());
        return nowUdidTemplate;
    }

    public void bindUdidToAppleAccount(String udid){
        DevicePO devicePO = deviceBSService.getDeviceByUdid(udid);
        if(devicePO==null){
            appleAccountBSService.selectBestAppleAccount();
        }else{
            AppleAccountPO appleAccountPO = appleAccountBSService.getAccountById(devicePO.getAppleId());
        }
    }

}