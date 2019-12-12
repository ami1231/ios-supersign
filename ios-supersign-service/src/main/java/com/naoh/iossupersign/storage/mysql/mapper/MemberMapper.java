package com.naoh.iossupersign.storage.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naoh.iossupersign.model.po.MemberPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<MemberPO> {

    MemberPO getByAccount(@Param("account") String account);

    List<MemberPO> selectMemberByCondition(@Param("memberPO") MemberPO memberPO);

    void update(MemberPO memberPO);
}
