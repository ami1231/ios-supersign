package com.naoh.iossupersign.service.profile.impl;

import cn.hutool.core.lang.UUID;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AccountDevicePO;
import com.naoh.iossupersign.service.file.FileService;
import com.naoh.iossupersign.service.profile.ProfileBSService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Peter.Hong
 * @date 2019/12/14
 */
@Service
public class ProfileBSServiceImpl implements ProfileBSService {

    @Value("${file.profileUploadPath}")
    private String fileUploadPath;

    private final AppleApiService appleApiService;

    private final FileService fileService;

    @Autowired
    public ProfileBSServiceImpl(AppleApiService appleApiService, FileService fileService) {
        this.appleApiService = appleApiService;
        this.fileService = fileService;
    }

    @Override
    public String createNewProfile(AccountDevicePO accountDevicePO) {
        AuthorizeBO authorizeBO = AuthorizeBO.builder()
                .iss(accountDevicePO.getIssuerId())
                .p8(accountDevicePO.getP8())
                .kid(accountDevicePO.getKid())
                .build();
        AppleResultDTO appleResultDTO = appleApiService.getMobileprovision(authorizeBO, accountDevicePO.getBundleIds(),accountDevicePO.getCerId(), accountDevicePO.getUdid());

        String profileContentBase64 = (String)appleResultDTO.getAttributes().get("profileContent");

        String pathName = fileUploadPath+UUID.randomUUID().toString()+".mobileprovision";

        return uploadProfile(profileContentBase64, pathName);
    }

    @Override
    public String uploadProfile(String base64, String pathName) {
        File file = fileService.base64ToFile(base64, pathName);
        return file.getAbsolutePath();
    }
}
