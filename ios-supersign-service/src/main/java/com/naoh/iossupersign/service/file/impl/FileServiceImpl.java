package com.naoh.iossupersign.service.file.impl;

import com.naoh.iossupersign.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.ipaUploadPath}")
    private String fileUploadPath;

    public String uploadFile(File file) throws IOException {
        String pathname = fileUploadPath+file.getName();
        byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
        Path path = Paths.get(pathname);
        Files.write(path, bytes);
        return pathname;
    }

    public String uploadFile(byte[] bytes, String pathname) throws IOException {
        Path path = Paths.get(pathname);
        Files.write(path, bytes);
        return  pathname;
    }

    public boolean deleteFileIfExit(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        return Files.deleteIfExists(path);
    }
}
