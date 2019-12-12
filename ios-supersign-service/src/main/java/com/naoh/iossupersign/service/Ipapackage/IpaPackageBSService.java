package com.naoh.iossupersign.service.Ipapackage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
public interface IpaPackageBSService {

    void uploadIpa(MultipartFile file, String summary);

    Page<IpaPackagePO> selectIpaByCondition(@NotNull Integer currentPage, String name);

}
