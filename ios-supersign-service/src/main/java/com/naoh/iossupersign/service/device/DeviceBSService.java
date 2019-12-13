package com.naoh.iossupersign.service.device;

import com.naoh.iossupersign.assembly.DeviceAssembly;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.DevicePO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeviceBSService {

    private final DeviceService deviceService;

    private final DeviceAssembly deviceAssembly;

    public DeviceBSService(DeviceService deviceService, DeviceAssembly deviceAssembly) {
        this.deviceService = deviceService;
        this.deviceAssembly = deviceAssembly;
    }

    public boolean insertList(List<AppleResultDTO> deviceDTOS , Long appleId){
        List<DevicePO> devicePOS = deviceAssembly.assembleDevicePOByAppleDeviceDTO(deviceDTOS,appleId);
        deviceService.insertList(devicePOS);
        return true;
    }

    public DevicePO getDeviceByUdid(String udid){
       return deviceService.getDeviceByUdid(udid);
    }

    public void insert(DevicePO devicePO){
        devicePO.setCreateTime(LocalDate.now());
        deviceService.insert(devicePO);
    }

}
