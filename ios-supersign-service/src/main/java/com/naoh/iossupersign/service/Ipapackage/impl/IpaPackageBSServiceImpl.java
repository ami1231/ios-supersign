package com.naoh.iossupersign.service.Ipapackage.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ZipUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

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
                    ipaPackageService.insertIpaPackage(newIpaPackagePO);
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
    public Page<IpaPackagePO> selectIpaByCondition(@NotNull Integer currentPage, String name) {

        Page<IpaPackagePO> page = new Page<>();
        page.setCurrent(currentPage.longValue());
        page.setSize(DEFAULT_PAGE_SIZE);

        return ipaPackageService.getIpaPackagePage(page, name);
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
        String id = parse.get("CFBundleIdentifier").toString();

        String appLink = fileService.uploadFile(excelFile);
        if (appLink != null) {
            log.info("ipa文件上传完成");
        }
        return IpaPackagePO.builder()
                .id(ipaPackageBO.getId())
                .name(name)
                .version(version)
                .buildVersion(buildVersion)
                .miniVersion(miniVersion)
                .bundleIdentifier(id)
                .link(appLink)
                .summary(summary)
                .build();
    }

    private File getAppFile(File ipaFile) {
        File payload = new File(ipaFile.getAbsolutePath() + "/Payload/");
        if (payload != null) {
            for (File file : payload.listFiles()) {
                if (fileService.getSuffixName(file).equalsIgnoreCase("app")) {
                    return file;
                }
            }
        }
        return null;
    }


}
