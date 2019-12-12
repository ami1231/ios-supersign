package com.naoh.iossupersign.service.appleaccount;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.storage.mysql.mapper.AppleAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppleAccountService {

    @Autowired
    private AppleAccountMapper appleAccountMapper;

    public int insert(AppleAccountPO appleAccountPO) {
        return appleAccountMapper.insert(appleAccountPO);
    }


    public AppleAccountPO getAccountByAccount(AppleAccountPO appleAccountPO) {
        return appleAccountMapper.getAppleAccountByAccount(appleAccountPO.getAccount());
    }

    public List<AppleAccountPO> getAllAccount() {
        return appleAccountMapper.getAllAppleAccount();
    }

    public Page<AppleAccountPO> selectAppleAccountByCondition(Page<AppleAccountPO> page, AppleAccountPO appleAccountPO) {
       return appleAccountMapper.selectAppleAccountByCondition(page , appleAccountPO);
    }
}
