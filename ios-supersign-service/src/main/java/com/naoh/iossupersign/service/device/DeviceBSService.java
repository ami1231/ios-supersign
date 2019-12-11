package com.naoh.iossupersign.service.device;

import com.naoh.iossupersign.assembly.DeviceAssembly;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.DevicePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceBSService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceAssembly deviceAssembly;

    public boolean insertList(List<AppleResultDTO> deviceDTOS , Long appleId){
        List<DevicePO> devicePOS = deviceAssembly.assembleDevicePOByAppleDeviceDTO(deviceDTOS,appleId);
        deviceService.insertList(devicePOS);
        return true;
    }


}
