package com.naoh.iossupersign.service.Ipapackage.impl;

import cn.hutool.Hutool;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.naoh.iossupersign.enums.ServiceError;
import com.naoh.iossupersign.exception.ServiceException;
import com.naoh.iossupersign.model.bo.IpaPackageBO;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageService;
import com.naoh.iossupersign.service.file.FileService;
import com.naoh.iossupersign.utils.FileUtils;
import com.naoh.iossupersign.utils.IosUrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Service
@Slf4j
public class IpaPackageBSServiceImpl implements IpaPackageBSService {

    private static final String FILE_NAME_SPLIT = ".";

    private static final String FILE_NAME = "ipa";

    private static final Long DEFAULT_PAGE_SIZE = 20L;

    private final IpaPackageService ipaPackageService;

    private final FileService fileService;

    @Value("${ipa.download.url}")
    private String ipaDownloadUrl;

    @Autowired
    public IpaPackageBSServiceImpl(IpaPackageService ipaPackageService, FileService fileService) {
        this.ipaPackageService = ipaPackageService;
        this.fileService = fileService;
    }

    @Override
    public void uploadIpa(IpaPackageBO ipaPackageBO) {

        String fileName = ipaPackageBO.getFile().getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(FILE_NAME_SPLIT) + 1);
        if (suffix.equalsIgnoreCase(FILE_NAME)) {
            // 上传的文件为ipa文件
            IpaPackagePO newIpaPackagePO = null;
            try {
                newIpaPackagePO = analyze(ipaPackageBO);
                log.info("ipa detail : {}", newIpaPackagePO);

                if(ipaPackageBO.getId() != null){

                    IpaPackagePO ipaPackage = ipaPackageService.selectIpaPackageById(ipaPackageBO.getId());
                    fileService.deleteFileIfExit(ipaPackage.getLink());
                    ipaPackageService.updateIpaPackage(newIpaPackagePO);

                }else{

                    IpaPackagePO originIpaPO = ipaPackageService.selectByDownloadId(newIpaPackagePO.getIpaDownloadId());
                    if(Objects.nonNull(originIpaPO)){
                        // 相同 BundleId update
                        ipaPackageService.updateIpaPackage(newIpaPackagePO);
                    }else{
                        ipaPackageService.insertIpaPackage(newIpaPackagePO);
                    }


                }

            } catch (Exception e) {
                // 解析失敗
                // throws exception
                try {
                    fileService.deleteFileIfExit(newIpaPackagePO.getLink());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                log.error("error" ,e);
                throw new ServiceException(ServiceError.INSERT_DATA_FAILURE);
            }
        }else {
            // 上传的文件非ipa文件
            // throws exception
            log.error("file is not ipa");
            throw new ServiceException(ServiceError.INVALID_PARAMETER);
        }
    }

    @Override
    public Page<IpaPackagePO> selectIpaByName(@NotNull Integer currentPage, String name) {

        Page<IpaPackagePO> page = new Page<>();
        page.setCurrent(currentPage.longValue());
        page.setSize(DEFAULT_PAGE_SIZE);
        page = ipaPackageService.getIpaPackagePage(page, name);

        if(!CollectionUtils.isEmpty(page.getRecords())){
            page.getRecords().forEach(packagePO -> {
                packagePO.setDownloadUrl(IosUrlUtils.getUdidViewUrl(ipaDownloadUrl,packagePO.getIpaDownloadId()));
            });
        }
        return page;
    }

    @Override
    public IpaPackagePO selectIpaByDownloadId(@NotNull String downloadId) {
        return ipaPackageService.selectByDownloadId(downloadId);
    }

    private IpaPackagePO analyze(IpaPackageBO ipaPackageBO) throws Exception{
        MultipartFile file =  ipaPackageBO.getFile();
        String summary = ipaPackageBO.getSummary();
        File excelFile = File.createTempFile(UUID.randomUUID().toString(), ".ipa");
        file.transferTo(excelFile);
        File ipa = ZipUtil.unzip(excelFile);
        File app = getAppFile(ipa);
        File info = new File(app.getAbsolutePath()+"/Info.plist");
        NSDictionary parse = (NSDictionary) PropertyListParser.parse(new FileReader(info).readBytes());
        String name = parse.get("CFBundleName").toString();
        if (parse.containsKey("CFBundleDisplayName")) {
            name = parse.get("CFBundleDisplayName").toString();
        }
        String version = parse.get("CFBundleShortVersionString").toString();
        String buildVersion = parse.get("CFBundleVersion").toString();
        String miniVersion = parse.get("MinimumOSVersion").toString();
        String bundleIdentifier = parse.get("CFBundleIdentifier").toString();

        String appLink = fileService.uploadFile(excelFile);
        if (appLink != null) {
            log.info("ipa文件上传完成");
        }

        // 下載包url標示
        String ipaDownloadId = DigestUtils.md5DigestAsHex(bundleIdentifier.getBytes());

        return IpaPackagePO.builder()
                .id(ipaPackageBO.getId())
                .name(name)
                .version(version)
                .buildVersion(buildVersion)
                .miniVersion(miniVersion)
                .bundleIdentifier(bundleIdentifier)
                .link(appLink)
                .summary(summary)
                .ipaDownloadId(ipaDownloadId)
                .build();
    }

    private File getAppFile(File ipaFile) {
        File payload = new File(ipaFile.getAbsolutePath() + "/Payload/");
        if (payload != null) {
            for (File file : payload.listFiles()) {
                if (FileUtils.getSuffixName(file).equalsIgnoreCase("app")) {
                    return file;
                }
            }
        }
        return null;
    }

}
