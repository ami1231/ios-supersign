package com.naoh.iossupersign.service.appleaccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AppleAccountBSService {
    @Transactional
    void addAppleAccount(AppleAccountPO appleAccountPO);

    Page<AppleAccountPO> selectAppleAccountByCondition(@NotNull Integer currentPage, AppleAccountPO appleAccountPO);

    AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO);

    List<AppleAccountPO> selectBestAppleAccount();

    void updateAccountDeviceCount(String account, Integer deviceCount);

    Long addAppleDeviceCountToRedis(String appleAccount, Long deviceCount);

    void uploadP12(MultipartFile p12File, String account);
}
