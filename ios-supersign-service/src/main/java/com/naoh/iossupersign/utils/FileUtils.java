package com.naoh.iossupersign.utils;

import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * @author Peter.Hong
 * @date 2019/12/14
 */
@UtilityClass
public class FileUtils {


    /**
     * @return 后缀名
     */
    public String getSuffixName(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
