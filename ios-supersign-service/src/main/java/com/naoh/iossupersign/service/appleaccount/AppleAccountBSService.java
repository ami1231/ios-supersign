package com.naoh.iossupersign.service.appleaccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.enums.ServiceError;
import com.naoh.iossupersign.exception.ServiceException;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AppleAccountBSService {

    private final AppleApiService appleApiService;

    private final AppleAccountService appleAccountService;

    private final DeviceBSService deviceBSService;

    public AppleAccountBSService(AppleApiService appleApiService, AppleAccountService appleAccountService, DeviceBSService deviceBSService) {
        this.appleApiService = appleApiService;
        this.appleAccountService = appleAccountService;
        this.deviceBSService = deviceBSService;
    }

    /**
     * 添加蘋果帳號
     * @param appleAccountPO
     */
    public void addAppleAccount(AppleAccountPO appleAccountPO) {
        AuthorizeBO authorizeBO = AuthorizeBO.builder()
                .iss(appleAccountPO.getIssuerId())
                .kid(appleAccountPO.getKid())
                .p8(appleAccountPO.getP8())
                .csr(appleAccountPO.getCsr()).build();
        //取得機器下的設備
        List<AppleResultDTO> appleDeviceDataList = appleApiService.getNumberOfAvailableDevices(authorizeBO);
        //創建apple bundleId
        AppleResultDTO bundleIdData = appleApiService.registerNewBundleId(authorizeBO);
        List<AppleResultDTO> certificates = appleApiService.selectCertificates(authorizeBO);
        AppleResultDTO cerData = null;
        if(CollectionUtils.isEmpty(certificates)){
            //創建新證書
            cerData = appleApiService.insertCertificates(authorizeBO);
        }else{
            cerData = certificates.stream().findFirst().get();
        }

        if(!CollectionUtils.isEmpty(appleDeviceDataList)){
            appleAccountPO.setCount(appleDeviceDataList.size());
        }else{
            appleAccountPO.setCount(0);
        }
        appleAccountPO.setCerId(cerData.getId());
        appleAccountPO.setBundleIds(bundleIdData.getId());
        appleAccountPO.setCreateTime(LocalDateTime.now());
        //創建蘋果帳號
        appleAccountService.insert(appleAccountPO);

        //同步帳號下的機器資訊
        deviceBSService.insertList(appleDeviceDataList,appleAccountPO.getId());
    }

    public Page<AppleAccountPO> selectAppleAccountByCondition(@NotNull Integer currentPage , AppleAccountPO appleAccountPO) {
        if (Objects.isNull(currentPage) || currentPage <= 0) {
            throw new ServiceException(ServiceError.INVALID_PARAMETER);
        }
        Page<AppleAccountPO> page = new Page<>();
        page.setCurrent(currentPage.longValue());
        return appleAccountService.selectAppleAccountByCondition(page, appleAccountPO);
    }

    public AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO) {
       return appleAccountService.getAccountByAccount(appleAccountPO);
    }
}