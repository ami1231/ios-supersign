package com.naoh.iossupersign.service.file;

import java.io.File;
import java.io.IOException;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
public interface FileService {

    String uploadFile(File file, String pathname) throws IOException;

    boolean deleteFileIfExit(String pathName) throws IOException;

    String uploadFile(byte[] bytes, String pathName) throws IOException;

    File base64ToFile(String base64, String pathName);
}
