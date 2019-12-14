package com.naoh.iossupersign.service.appleaccount.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.cache.RedisCache;
import com.naoh.iossupersign.cache.RedisKey;
import com.naoh.iossupersign.enums.ServiceError;
import com.naoh.iossupersign.exception.ServiceException;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountBSService;
import com.naoh.iossupersign.service.appleaccount.AppleAccountService;
import com.naoh.iossupersign.service.device.DeviceBSService;
import com.naoh.iossupersign.service.file.FileService;
import com.naoh.iossupersign.thridparty.appleapi.AppleApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AppleAccountBSServiceImpl implements AppleAccountBSService {

    private final AppleApiService appleApiService;

    private final AppleAccountService appleAccountService;

    private final DeviceBSService deviceBSService;

    private final RedisCache redisCache;

    private final FileService fileService;

    @Value("${file.p12UploadPath}")
    private String fileUploadPath;

    public static final Integer DEVICE_LIMIT = 100 ;

    public static final Integer SELECT_ENABLE_LIMIT = 100 ;

    @Autowired
    public AppleAccountBSServiceImpl(AppleApiService appleApiService, AppleAccountService appleAccountService, DeviceBSService deviceBSService, RedisCache redisCache, FileService fileService) {
        this.appleApiService = appleApiService;
        this.appleAccountService = appleAccountService;
        this.deviceBSService = deviceBSService;
        this.redisCache = redisCache;
        this.fileService = fileService;
    }

    /**
     * 添加蘋果帳號
     * @param appleAccountPO
     */
    @Transactional
    @Override
    public void addAppleAccount(AppleAccountPO appleAccountPO) {
        AuthorizeBO authorizeBO = AuthorizeBO.builder()
                .iss(appleAccountPO.getIssuerId())
                .kid(appleAccountPO.getKid())
                .p8(appleAccountPO.getP8())
                .csr(appleAccountPO.getCsr()).build();
        //取得機器下的設備
        List<AppleResultDTO> appleDeviceDataList = appleApiService.getNumberOfAvailableDevices(authorizeBO);
        //移除目前bundleId
        appleApiService.removeBundleIds(authorizeBO);
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
        Long deviceNumber = new Long(appleDeviceDataList.size());
        addAppleDeviceCountToRedis(appleAccountPO.getAccount(),deviceNumber);
    }

    @Override
    public Page<AppleAccountPO> selectAppleAccountByCondition(@NotNull Integer currentPage, AppleAccountPO appleAccountPO) {
        if (Objects.isNull(currentPage) || currentPage <= 0) {
            throw new ServiceException(ServiceError.INVALID_PARAMETER);
        }
        Page<AppleAccountPO> page = new Page<>();
        page.setCurrent(currentPage.longValue());
        return appleAccountService.selectAppleAccountByCondition(page, appleAccountPO);
    }

    @Override
    public AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO) {
       return appleAccountService.getAccountByAccount(appleAccountPO);
    }

    @Override
    public List<AppleAccountPO> selectBestAppleAccount() {
        //找出可用最佳Apple账号
        List<AppleAccountPO> appleAccountPOS = appleAccountService.selectEnableAppleAccounts(DEVICE_LIMIT,SELECT_ENABLE_LIMIT);
        if(CollectionUtils.isEmpty(appleAccountPOS)){
            //无可用Apple账号
            throw new ServiceException(ServiceError.NO_ENABLE_APPLE_ACCOUNT);
        }
        return appleAccountPOS;
    }

    @Override
    public void updateAccountDeviceCount(String account, Integer deviceCount){
        appleAccountService.updateAccountDeviceCount(account,deviceCount);
    }

    @Override
    public Long addAppleDeviceCountToRedis(String appleAccount, Long deviceCount){
        return redisCache.hincr(RedisKey.APPLE_ACCOUNT_DEVICE_COUNT_KEY,appleAccount,deviceCount);
    }

    @Override
    public void uploadP12(MultipartFile p12File, String account) {
        String filePath = fileUploadPath+UUID.randomUUID().toString()+".p12";

        try{
            // TODO: 2019/12/14 上傳地址需確認
            fileService.uploadFile(p12File.getBytes(), filePath);
            appleAccountService.updateAccountP12Path(account, filePath);
        }catch (Exception e){
            log.error("Upload P12 File error", e);
            throw new ServiceException(ServiceError.INVALID_PARAMETER);
        }
    }
}