package com.naoh.iossupersign.service.file;

import java.io.File;
import java.io.IOException;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
public interface FileService {

    String uploadFile(File file) throws IOException;

    boolean deleteFileIfExit(String pathName) throws IOException;

    String uploadFile(byte[] bytes, String pathname) throws IOException;
}
