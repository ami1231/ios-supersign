package com.naoh.iossupersign.service.appleaccount.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.service.appleaccount.AppleAccountService;
import com.naoh.iossupersign.storage.mysql.mapper.AppleAccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppleAccountServiceImpl implements AppleAccountService {

    private final AppleAccountMapper appleAccountMapper;

    public AppleAccountServiceImpl(AppleAccountMapper appleAccountMapper) {
        this.appleAccountMapper = appleAccountMapper;
    }

    @Override
    public int insert(AppleAccountPO appleAccountPO) {
        return appleAccountMapper.insert(appleAccountPO);
    }


    @Override
    public AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO) {
        return appleAccountMapper.getAppleAccountByAccount(appleAccountPO.getAccount());
    }

    @Override
    public Page<AppleAccountPO> selectAppleAccountByCondition(Page<AppleAccountPO> page, AppleAccountPO appleAccountPO) {
       return appleAccountMapper.selectAppleAccountByCondition(page , appleAccountPO);
    }

    @Override
    public AppleAccountPO getAccountById(Long id) {
        return appleAccountMapper.getAccountById(id);
    }

    @Override
    public List<AppleAccountPO> selectEnableAppleAccounts(Integer deviceLimit , Integer sizeLimit) {
       return appleAccountMapper.selectEnableAppleAccounts(deviceLimit , sizeLimit);
    }

    @Override
    public void updateAccountDeviceCount(String account, Integer deviceCount) {
        appleAccountMapper.updateAccountDeviceCount(account,deviceCount);
    }
}