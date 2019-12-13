package com.naoh.iossupersign.model.bo;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter.Hong
 * @date 2019/12/13
 */
@Data
@Builder
public class IpaPackageBO {

    private Long id;

    private MultipartFile file;

    private String summary;
}
