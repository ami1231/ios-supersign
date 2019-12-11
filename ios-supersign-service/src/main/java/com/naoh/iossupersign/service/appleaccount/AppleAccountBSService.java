package com.naoh.iossupersign.service.appleaccount;

import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.device.DeviceService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AppleAccountBSService {

    @Autowired
    private AppleApiService appleApiService;

    @Autowired
    private AppleAccountService appleAccountService;

    @Autowired
    private DeviceService deviceService;

    /**
     * 添加蘋果帳號
     * @param appleAccountPO
     */
    public void addAppleAccount(AppleAccountPO appleAccountPO) {
        AuthorizeBO authorizeBO = AuthorizeBO.builder()
                .iss(appleAccountPO.getIssuerId())
                .kid(appleAccountPO.getKid())
                .p8(appleAccountPO.getP8()).build();
        List<AppleResultDTO> appleDeviceDTOS = appleApiService.getNumberOfAvailableDevices(authorizeBO);
        if(!CollectionUtils.isEmpty(appleDeviceDTOS)){
            appleAccountPO.setCount(appleDeviceDTOS.size());
        }

        appleAccountService.insert(appleAccountPO);

    }

    public AppleAccountPO getAccountByAccount(String account) {
        return null;
    }
}
