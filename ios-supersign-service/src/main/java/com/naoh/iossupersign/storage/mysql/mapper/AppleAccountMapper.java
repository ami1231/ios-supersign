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

    AppleAccountPO getAppleAccountByAccount(String account);

    Page<AppleAccountPO> selectAppleAccountByCondition(Page<AppleAccountPO> page, @Param("appleAccountPO") AppleAccountPO appleAccountPO);

    AppleAccountPO getAccountById(Long id);

    List<AppleAccountPO> selectEnableAppleAccounts(@Param("deviceLimit") Integer deviceLimit, @Param("sizeLimit") Integer sizeLimit);

    void updateDeviceCountLok(@Param("account") String account);

    void updateAccountDeviceCount(@Param("account") String account, @Param("deviceCount") Integer deviceCount);
}