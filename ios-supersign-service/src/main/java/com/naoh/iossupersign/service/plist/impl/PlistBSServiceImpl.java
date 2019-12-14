package com.naoh.iossupersign.service.plist.impl;

import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import com.naoh.iossupersign.service.plist.PlistBService;
import com.naoh.iossupersign.utils.IosUrlUtils;
import com.naoh.iossupersign.utils.IosUtils;
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

    @Value("${ipa.download.url}")
    private String ipaDownloadUrl;

    @Autowired
    public PlistBSServiceImpl(IpaPackageBSService ipaPackageBSService) {
        this.ipaPackageBSService = ipaPackageBSService;
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

        // 重簽回傳下載url

        // 組plist
        // FIXME: 2019/12/14 需拿重簽過後的ipaLink
        String plist = plistTemplate
//                .replace("@@IpaLink", Objects.toString(ipaPackagePO.getLink(),""))
                .replace("@@IpaLink", IosUrlUtils.getIpaUrl(ipaDownloadUrl))
                .replace("@@BundleIdentifier", Objects.toString(ipaPackagePO.getBundleIdentifier(),""))
                .replace("@@BundleVersion", Objects.toString(ipaPackagePO.getBuildVersion(),""))
                .replace("@@Name", Objects.toString(ipaPackagePO.getName(),""));
        return plist;
    }
}
