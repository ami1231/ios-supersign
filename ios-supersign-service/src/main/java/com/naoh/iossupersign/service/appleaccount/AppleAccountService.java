package com.naoh.iossupersign.service.appleaccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.storage.mysql.mapper.AppleAccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppleAccountService {

    private final AppleAccountMapper appleAccountMapper;

    public AppleAccountService(AppleAccountMapper appleAccountMapper) {
        this.appleAccountMapper = appleAccountMapper;
    }

    public int insert(AppleAccountPO appleAccountPO) {
        return appleAccountMapper.insert(appleAccountPO);
    }


    public AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO) {
        return appleAccountMapper.getAppleAccountByAccount(appleAccountPO.getAccount());
    }

    public Page<AppleAccountPO> selectAppleAccountByCondition(Page<AppleAccountPO> page, AppleAccountPO appleAccountPO) {
       return appleAccountMapper.selectAppleAccountByCondition(page , appleAccountPO);
    }

    public AppleAccountPO getAccountById(Long id) {
        return appleAccountMapper.getAccountById(id);
    }

    public List<AppleAccountPO> selectEnableAppleAccounts(Integer deviceLimit , Integer sizeLimit) {
       return appleAccountMapper.selectEnableAppleAccounts(deviceLimit , sizeLimit);
    }

    public void updateAccountDeviceCount(String account, Integer deviceCount) {
        appleAccountMapper.updateDeviceCountLok(account);
        appleAccountMapper.updateAccountDeviceCount(account,deviceCount);
    }
}