package com.naoh.iossupersign.service.Ipapackage;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
public interface IpaPackageBSService {

    void uploadIpa(MultipartFile file, String summary);

}
