package com.naoh.iossupersign.service.Ipapackage.impl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ZipUtil;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import com.naoh.iossupersign.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Service
@Slf4j
public class IpaPackageBSServiceImpl implements IpaPackageBSService {

    private static final String FILE_NAME_SPLIT = ".";

    private static final String FILE_NAME = "ipa";

    @Override
    public void uploadIpa(MultipartFile file, String summary) {

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(FILE_NAME_SPLIT) + 1);
        if (suffix.equalsIgnoreCase(FILE_NAME)) {
            // 上传的文件为ipa文件
            try {
                IpaPackagePO ipaPackagePO = analyze(file, summary);
                log.info("ipa detail : {}", ipaPackagePO);

            } catch (Exception e) {
                // 解析失敗
                // throws exception
                log.error("error" ,e);
            }
        }else {
            // 上传的文件非ipa文件
            // throws exception
        }
    }

    private IpaPackagePO analyze(MultipartFile file, String summary) throws Exception{

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

        String appLink = FileUtils.uploadFile(excelFile);
        if (appLink!=null) {
            System.out.println("ipa文件上传完成");
        }
//        Package pck = packageService.getPackageByBundleIdentifier(id);
        return IpaPackagePO.builder()
                .name(name)
                .version(version)
                .buildVersion(buildVersion)
                .miniVersion(miniVersion)
                .link(appLink)
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
