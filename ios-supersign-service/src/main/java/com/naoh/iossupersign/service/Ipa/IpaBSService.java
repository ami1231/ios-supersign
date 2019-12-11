package com.naoh.iossupersign.service.Ipa;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
public interface IpaBSService {

    void uploadIpa(MultipartFile file, String summary);

}
