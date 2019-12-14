package com.naoh.iossupersign.service.udid;

import javax.transaction.Transactional;

public interface UDIDBSService {

    @Transactional
    Boolean bindUdidToAppleAccount(String udid);

    String getMobileConfig(String ipaId);
}
