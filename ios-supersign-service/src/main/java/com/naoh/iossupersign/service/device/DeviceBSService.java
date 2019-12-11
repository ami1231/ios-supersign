package com.naoh.iossupersign.service.device;

import com.naoh.iossupersign.model.dto.AppleResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceBSService {

    @Autowired
    private DeviceService deviceService;

    public boolean insertList(List<AppleResultDTO> deviceDTOS){


        return false;

    }


}
