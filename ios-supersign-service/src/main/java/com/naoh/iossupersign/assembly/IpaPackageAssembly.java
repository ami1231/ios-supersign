package com.naoh.iossupersign.assembly;

import com.naoh.iossupersign.model.bo.IpaPackageBO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter.Hong
 * @date 2019/12/13
 */
@Component
public class IpaPackageAssembly {

    public IpaPackageBO assembleDeviceBO(Long id, MultipartFile file, String summary){

        return IpaPackageBO.builder()
                .id(id)
                .summary(summary)
                .file(file)
                .build();
    }
}
