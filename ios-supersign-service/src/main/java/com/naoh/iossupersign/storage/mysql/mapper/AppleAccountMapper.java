package com.naoh.iossupersign.storage.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naoh.iossupersign.model.po.AppleAccountPO;
import com.naoh.iossupersign.model.po.MemberPO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ami.Tsai
 * @date 2019/12/10
 */
@Mapper
@Repository
public interface AppleAccountMapper extends BaseMapper<AppleAccountPO> {

}
