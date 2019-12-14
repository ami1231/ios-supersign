package com.naoh.iossupersign.service.udid.impl;

import com.naoh.iossupersign.cache.RedisCache;
import com.naoh.iossupersign.cache.RedisKey;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.bo.UdidBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.model.po.DevicePO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.service.udid.UDIDBSService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UDIDBSServiceImpl implements UDIDBSService {

    private final String mobileConfigPath = "udid.mobileconfig";

    private String udidTemplate;

    private Integer deviceLimit = 100;

    private final RedisCache redisCache;

    private final AppleAccountBSService appleAccountBSService;

    private final AppleApiService appleApiService;

    private final DeviceBSService deviceBSService;

    @Value("${ipa.download.udid-url}")
    private String udidownloadurl;

    public UDIDBSServiceImpl(RedisCache redisCache, AppleAccountBSService appleAccountBSService, AppleApiService appleApiService, DeviceBSService deviceBSService) {
        this.redisCache = redisCache;
        this.appleAccountBSService = appleAccountBSService;
        this.appleApiService = appleApiService;
        this.deviceBSService = deviceBSService;
    }

    @PostConstruct
    public void initUDIDTemplate() throws URISyntaxException, IOException {
        udidTemplate =  new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(mobileConfigPath).toURI())));
    }

    @Override
    public String getMobileConfig(String ipaId){
        UdidBO udidBO =  UdidBO.builder().payloadUUID(UUID.randomUUID().toString()).build();
        String nowUdidTemplate = udidTemplate
                .replace("@@getUDIDURL",udidownloadurl+"/udid/getUDID/"+ipaId)
                .replace("@@PayloadOrganization", Objects.toString(udidBO.getPayloadOrganization(),""))
                .replace("@@PayloadDisplayName",Objects.toString(udidBO.getPayloadDisplayName(),""))
                .replace("@@PayloadUUID",udidBO.getPayloadUUID());
        return nowUdidTemplate;
    }

    /**
     * 分配Device到苹果帐号上
     * @param udid
     */
    @Transactional
    @Override
    public Boolean bindUdidToAppleAccount(String udid){
        Boolean isBindSuccess = false;
        DevicePO devicePO = deviceBSService.getDeviceByUdid(udid);
        if(devicePO==null){
            List<AppleAccountPO> appleAccountPOS =  appleAccountBSService.selectBestAppleAccount();
            for(AppleAccountPO appleAccountPO:appleAccountPOS){
                Long deviceCount = appleAccountBSService.addAppleDeviceCountToRedis(appleAccountPO.getAccount(),1L);
                if(deviceCount>deviceLimit){
                    continue;
                }
                AuthorizeBO authorizeBO = new AuthorizeBO(appleAccountPO);
                AppleResultDTO appleResultDTO = appleApiService.registerNewDevice(udid,authorizeBO);
                devicePO = DevicePO.builder()
                        .deviceId(appleResultDTO.getId())
                        .udid(udid)
                        .appleId(appleAccountPO.getId())
                        .build();
                deviceBSService.insert(devicePO);
                //apple_account增加一台设备资料
                appleAccountBSService.updateAccountDeviceCount(appleAccountPO.getAccount(),1);
                isBindSuccess = true;
                break;
            }
        }
        return isBindSuccess;
    }

}