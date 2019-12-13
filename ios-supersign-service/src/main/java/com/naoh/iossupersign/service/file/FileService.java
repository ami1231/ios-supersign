package com.naoh.iossupersign.service.file;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Component
@Slf4j
public class FileService {

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    /**
     * @return 后缀名
     */
    public String getSuffixName(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String uploadFile(File file) throws IOException {
        String pathname = fileUploadPath+file.getName();
        byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
        Path path = Paths.get(pathname);
        Files.write(path, bytes);
        return pathname;
    }

    public boolean deleteFileIfExit(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        return Files.deleteIfExists(path);
    }
}
