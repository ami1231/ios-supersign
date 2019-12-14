package com.naoh.iossupersign.service.device;

import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AccountDevicePO;
import com.naoh.iossupersign.model.po.DevicePO;

import java.util.List;

public interface DeviceBSService {
    boolean insertList(List<AppleResultDTO> deviceDTOS, Long appleId);

    DevicePO getDeviceByUdid(String udid);

    void insert(DevicePO devicePO);

    AccountDevicePO getAccountDeviceByUdid(String udid);
}
