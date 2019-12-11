package com.naoh.iossupersign.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
@UtilityClass
public class FileUtils {

    private static final String fileUploadRootDir = "/Users/hongyi/Desktop/test/";

    /**
     * @return 后缀名
     */
    public static String getSuffixName(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String uploadFile(File file) throws IOException {
        String pathname = fileUploadRootDir+file.getName();
        byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
        Path path = Paths.get(pathname);
        Files.write(path, bytes);
        return pathname;
    }
}
