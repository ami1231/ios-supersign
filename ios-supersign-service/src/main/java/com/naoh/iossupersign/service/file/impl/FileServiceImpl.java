package com.naoh.iossupersign.service.file.impl;

import cn.hutool.core.codec.Base64;
import com.naoh.iossupersign.service.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
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

    public String uploadFile(File file, String pathname) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
        Path path = Paths.get(pathname);
        Files.write(path, bytes);
        return pathname;
    }

    /**
     * 回傳路徑
     * @param bytes
     * @param pathname
     * @return
     * @throws IOException
     */
    public String uploadFile(byte[] bytes, String pathname) throws IOException {
        Path path = Paths.get(pathname);
        Files.write(path, bytes);
        return  pathname;
    }

    @Override
    public File base64ToFile(String base64, String pathName) {
        File file = null;
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.decode(base64);
            file=new File(pathName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public boolean deleteFileIfExit(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        return Files.deleteIfExists(path);
    }
}
