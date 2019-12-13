package com.naoh.iossupersign.service.device;

import com.naoh.iossupersign.model.po.DevicePO;
import com.naoh.iossupersign.storage.mysql.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    private final DeviceMapper deviceMapper;

    public DeviceService(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    public void insertList(List<DevicePO> devicePOS) {
        deviceMapper.insertList(devicePOS);
    }

    public DevicePO getDeviceByUdid(String udid) {
       return deviceMapper.getDeviceByUdid(udid);
    }

    public void insert(DevicePO devicePO){
        deviceMapper.insert(devicePO);
    }
}
