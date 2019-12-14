package com.naoh.iossupersign.service.profile;

import com.naoh.iossupersign.model.po.AccountDevicePO;

/**
 * @author Peter.Hong
 * @date 2019/12/14
 */
public interface ProfileBSService {

    String createNewProfile(AccountDevicePO accountDevicePO);

    String uploadProfile(String base64, String pathName);
}
