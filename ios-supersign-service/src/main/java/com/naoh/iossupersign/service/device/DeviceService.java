package com.naoh.iossupersign.service.device;

import com.naoh.iossupersign.model.po.AccountDevicePO;
import com.naoh.iossupersign.model.po.DevicePO;

import java.util.List;

public interface DeviceService {
    void insertList(List<DevicePO> devicePOS);

    DevicePO getDeviceByUdid(String udid);

    void insert(DevicePO devicePO);

    AccountDevicePO getAccountDeviceByUdid(String udid);
}
