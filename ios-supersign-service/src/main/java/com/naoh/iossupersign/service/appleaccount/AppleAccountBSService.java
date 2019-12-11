package com.naoh.iossupersign.service.appleaccount;

import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppleAccountBSService {

    @Autowired
    private AppleApiService appleApiService;

    @Autowired
    private AppleAccountService appleAccountService;

    @Autowired
    private DeviceBSService deviceBSService;

    /**
     * 添加蘋果帳號
     * @param appleAccountPO
     */
    public void addAppleAccount(AppleAccountPO appleAccountPO) {
        AuthorizeBO authorizeBO = AuthorizeBO.builder()
                .iss(appleAccountPO.getIssuerId())
                .kid(appleAccountPO.getKid())
                .p8(appleAccountPO.getP8()).build();
        //取得機器下的設備
        List<AppleResultDTO> appleDeviceDataList = appleApiService.getNumberOfAvailableDevices(authorizeBO);
        //創建apple bundleId
        AppleResultDTO bundleIdData = appleApiService.registerNewBundleId(authorizeBO);
        //TODO 創建新證書
        AppleResultDTO cerData = new AppleResultDTO();

        if(!CollectionUtils.isEmpty(appleDeviceDataList)){
            appleAccountPO.setCount(appleDeviceDataList.size());
        }
        appleAccountPO.setCerId(cerData.getId());
        appleAccountPO.setBundleIds(bundleIdData.getId());
        appleAccountPO.setCreateTime(LocalDateTime.now());
        //創建蘋果帳號
        appleAccountService.insert(appleAccountPO);

        //同步帳號下的機器資訊
        deviceBSService.insertList(appleDeviceDataList,appleAccountPO.getId());
    }

    public AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO) {
        return appleAccountService.getAccountByAccount(appleAccountPO);
    }

    public List<AppleAccountPO> getAllAccount() {
        return appleAccountService.getAllAccount();
    }
}
