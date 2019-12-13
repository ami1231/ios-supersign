package com.naoh.iossupersign.service.udid;

import javax.transaction.Transactional;

public interface UDIDBSService {
    String getMobileConfig();

    @Transactional
    Boolean bindUdidToAppleAccount(String udid);
}
