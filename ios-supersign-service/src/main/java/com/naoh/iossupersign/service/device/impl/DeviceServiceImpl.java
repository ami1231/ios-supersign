package com.naoh.iossupersign.service.device.impl;

import com.naoh.iossupersign.model.po.AccountDevicePO;
import com.naoh.iossupersign.model.po.DevicePO;
import com.naoh.iossupersign.service.device.DeviceService;
import com.naoh.iossupersign.storage.mysql.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    @Override
    public void insertList(List<DevicePO> devicePOS) {
        deviceMapper.insertList(devicePOS);
    }

    @Override
    public DevicePO getDeviceByUdid(String udid) {
       return deviceMapper.getDeviceByUdid(udid);
    }

    @Override
    public void insert(DevicePO devicePO){
        deviceMapper.insert(devicePO);
    }

    @Override
    public AccountDevicePO getAccountDeviceByUdid(String udid) {
        return deviceMapper.getAccountDeviceByUdid(udid);
    }
}
