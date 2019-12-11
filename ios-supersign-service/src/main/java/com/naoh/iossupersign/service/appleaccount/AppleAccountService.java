package com.naoh.iossupersign.service.appleaccount;

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


}
