package com.naoh.iossupersign.assembly;

import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.DevicePO;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DeviceAssembly {

    public List<DevicePO> assembleDevicePOByAppleDeviceDTO(@NonNull final List<AppleResultDTO> deviceDTOS , Long appleId){
        List<DevicePO> devicePOS = new ArrayList<>();
        deviceDTOS.forEach(deviceDTO -> {
            Map<String,Object> attributes = deviceDTO.getAttributes();
            DevicePO devicePO = DevicePO.builder().appleId(appleId).deviceId(deviceDTO.getId()).udid(attributes.get("udid").toString())
                    .createTime(LocalDate.now()).build();
            devicePOS.add(devicePO);
        });
        return devicePOS;
    }

}
