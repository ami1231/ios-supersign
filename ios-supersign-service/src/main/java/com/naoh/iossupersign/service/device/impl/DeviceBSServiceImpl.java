package com.naoh.iossupersign.service.device.impl;

import com.naoh.iossupersign.assembly.DeviceAssembly;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.DevicePO;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.service.device.DeviceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeviceBSServiceImpl implements DeviceBSService {

    private final DeviceService deviceService;

    private final DeviceAssembly deviceAssembly;

    public DeviceBSServiceImpl(DeviceService deviceService, DeviceAssembly deviceAssembly) {
        this.deviceService = deviceService;
        this.deviceAssembly = deviceAssembly;
    }

    @Override
    public boolean insertList(List<AppleResultDTO> deviceDTOS, Long appleId){
        List<DevicePO> devicePOS = deviceAssembly.assembleDevicePOByAppleDeviceDTO(deviceDTOS,appleId);
        deviceService.insertList(devicePOS);
        return true;
    }

    @Override
    public DevicePO getDeviceByUdid(String udid){
       return deviceService.getDeviceByUdid(udid);
    }

    @Override
    public void insert(DevicePO devicePO){
        devicePO.setCreateTime(LocalDate.now());
        deviceService.insert(devicePO);
    }

}
