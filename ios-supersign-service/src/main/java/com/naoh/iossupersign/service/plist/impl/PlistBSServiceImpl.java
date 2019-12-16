package com.naoh.iossupersign.service.plist.impl;

import com.naoh.iossupersign.config.DomainConfig;
import com.naoh.iossupersign.model.po.AccountDevicePO;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.service.plist.PlistBService;
import com.naoh.iossupersign.service.profile.ProfileBSService;
import com.naoh.iossupersign.utils.IosUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author Peter.Hong
 * @date 2019/12/14
 */
@Service
public class PlistBSServiceImpl implements PlistBService {

    private final String plistTemplatePath = "plistTemplate";

    private String plistTemplate;

    private final IpaPackageBSService ipaPackageBSService;

    private final DeviceBSService deviceBSService;

    private final ProfileBSService profileBSService;

    private final DomainConfig domainConfig;

    @Autowired
    public PlistBSServiceImpl(IpaPackageBSService ipaPackageBSService, DeviceBSService deviceBSService, ProfileBSService profileBSService, DomainConfig domainConfig) {
        this.ipaPackageBSService = ipaPackageBSService;
        this.deviceBSService = deviceBSService;
        this.profileBSService = profileBSService;
        this.domainConfig = domainConfig;
    }

    @PostConstruct
    public void initPlistTemplate() throws URISyntaxException, IOException {
        plistTemplate =  new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(plistTemplatePath).toURI())));
    }

    @Override
    public String getPlist(String downloadId, String udid) {

        // 拿取原版ipa
        IpaPackagePO ipaPackagePO = ipaPackageBSService.selectIpaByDownloadId(downloadId);

        // 利用udid拿取appleAccount資訊
        AccountDevicePO accountDevicePO = deviceBSService.getAccountDeviceByUdid(udid);

        // 拿取新的profile(重簽需要)
        String profilePath = profileBSService.createNewProfile(accountDevicePO);
        // 重簽回傳下載url

        // 組plist
        // FIXME: 2019/12/14 需拿重簽過後的ipaLink
        String plist = plistTemplate
//                .replace("@@IpaLink", Objects.toString(ipaPackagePO.getLink(),""))
                .replace("@@IpaLink", IosUrlUtils.getIpaUrl(domainConfig.getIpaUrlPath()))
                .replace("@@BundleIdentifier", Objects.toString(ipaPackagePO.getBundleIdentifier(),""))
                .replace("@@BundleVersion", Objects.toString(ipaPackagePO.getBuildVersion(),""))
                .replace("@@Name", Objects.toString(ipaPackagePO.getName(),""));
        return plist;
    }
}
