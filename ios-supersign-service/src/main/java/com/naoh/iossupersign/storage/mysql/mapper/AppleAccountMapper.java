package com.naoh.iossupersign.storage.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ami.Tsai
 * @date 2019/12/10
 */
@Mapper
@Repository
public interface AppleAccountMapper extends BaseMapper<AppleAccountPO> {

    List<AppleAccountPO> getAllAppleAccount();

    List<AppleAccountPO> getLotteryAllByCondition(AppleAccountPO appleAccountPO);

    AppleAccountPO getAppleAccountByAccount(String account);

    Page<AppleAccountPO> selectAppleAccountByCondition(Page<AppleAccountPO> page, @Param("appleAccountPO") AppleAccountPO appleAccountPO);
}