package com.naoh.iossupersign.service.appleaccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;

import java.util.List;

public interface AppleAccountService {
    int insert(AppleAccountPO appleAccountPO);

    AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO);

    Page<AppleAccountPO> selectAppleAccountByCondition(Page<AppleAccountPO> page, AppleAccountPO appleAccountPO);

    AppleAccountPO getAccountById(Long id);

    List<AppleAccountPO> selectEnableAppleAccounts(Integer deviceLimit, Integer sizeLimit);

    void updateAccountDeviceCount(String account, Integer deviceCount);
}
