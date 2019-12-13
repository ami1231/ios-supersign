package com.naoh.iossupersign.service.Ipapackage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.IpaPackagePO;

/**
 * @author Peter.Hong
 * @date 2019/12/11
 */
public interface IpaPackageService {

    int insertIpaPackage(IpaPackagePO ipaPackagePO);

    void updateIpaPackage(IpaPackagePO ipaPackagePO);

    Page<IpaPackagePO> getIpaPackagePage(Page<IpaPackagePO> page, String name);

    IpaPackagePO selectIpaPackageById(Long id);

    IpaPackagePO selectByDownloadId(String downloadId);
}
