package com.naoh.iossupersign.service.udid;

import com.naoh.iossupersign.model.po.IpaPackagePO;

import javax.transaction.Transactional;

public interface UDIDBSService {

    @Transactional
    Boolean bindUdidToAppleAccount(String udid);

    String getMobileConfig(IpaPackagePO ipaPackagePO);

    String uploadMobileConfig(IpaPackagePO ipaPackagePO);
}
