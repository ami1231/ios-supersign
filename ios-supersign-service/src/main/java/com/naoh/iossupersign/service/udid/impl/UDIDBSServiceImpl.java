package com.naoh.iossupersign.service.udid.impl;

import com.naoh.iossupersign.cache.RedisCache;
import com.naoh.iossupersign.config.DomainConfig;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.bo.UdidBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.model.po.DevicePO;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.service.file.FileService;
import com.naoh.iossupersign.service.udid.UDIDBSService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import com.naoh.iossupersign.utils.IosUrlUtils;
import com.naoh.iossupersign.utils.ShellUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UDIDBSServiceImpl implements UDIDBSService {

    private final String mobileConfigPath = "udid.mobileconfig";

    private String udidTemplate;

    private Integer deviceLimit = 100;

    private final RedisCache redisCache;

    private final AppleAccountBSService appleAccountBSService;

    private final AppleApiService appleApiService;

    private final DeviceBSService deviceBSService;

    private final DomainConfig domainConfig;

    private final FileService fileService;

    @Value("${file.mobileconfigUploadPath}")
    private String mobileconfigUploadPath;

    @Value("${shPath}")
    private String shPath;

    @Autowired
    public UDIDBSServiceImpl(RedisCache redisCache, AppleAccountBSService appleAccountBSService, AppleApiService appleApiService, DeviceBSService deviceBSService, DomainConfig domainConfig, FileService fileService) {
        this.redisCache = redisCache;
        this.appleAccountBSService = appleAccountBSService;
        this.appleApiService = appleApiService;
        this.deviceBSService = deviceBSService;
        this.domainConfig = domainConfig;
        this.fileService = fileService;
    }

    @PostConstruct
    public void initUDIDTemplate() throws URISyntaxException, IOException {
        udidTemplate =  new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(mobileConfigPath).toURI())));
    }

    @Override
    public String getMobileConfig(IpaPackagePO ipaPackagePO){
        UdidBO udidBO =  UdidBO.builder().payloadDisplayName(ipaPackagePO.getSummary())
                .payloadUUID(UUID.randomUUID().toString()).build();
        String nowUdidTemplate = udidTemplate
                .replace("@@getUDIDURL", IosUrlUtils.getUDIDUrl(domainConfig.getMobileConfigUrlPath(),"/udid/getUDID/",ipaPackagePO.getIpaDownloadId()))
                .replace("@@PayloadDisplayName",Objects.toString(udidBO.getPayloadDisplayName(),udidBO.getPayloadDisplayName()))
                .replace("@@PayloadUUID",udidBO.getPayloadUUID());

        String fileName = "temp-udid-"+ipaPackagePO.getIpaDownloadId() + ".mobileconfig";
        String filePath = shPath+fileName;
        try {
            fileService.uploadFile(nowUdidTemplate.getBytes(), filePath);
        } catch (IOException e) {
            log.error("uploadMobileConfig error", e);
        }

        return filePath;
    }

    @Override
    public String uploadMobileConfig(IpaPackagePO ipaPackagePO) {
        String path = getMobileConfig(ipaPackagePO);
        try{
            String comd = "openssl smime -sign -in $1 -out $2 -signer $3 -certfile $4 -outform der -nodetach";
            comd=comd.replace("$1", path)
                    .replace("$2",mobileconfigUploadPath+ipaPackagePO.getMobileFileName())
                    .replace("$3",shPath+"InnovCertificates.pem")
                    .replace("$4",shPath+"root.crt.pem");
            ShellUtils.run(comd);
        }catch (Exception e){
            log.error("uploadMobileConfig error", e);
        }
        return path;
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
        }else{
            isBindSuccess = true;
        }
        return isBindSuccess;
    }



}