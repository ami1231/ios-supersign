package com.naoh.iossupersign.service.member;

import com.naoh.iossupersign.model.po.MemberPO;

import java.util.List;
import java.util.Optional;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
public interface MemberService {

    Optional<MemberPO> getAccountAllStatus(String account);

    List<MemberPO> selectMemberByCondition(MemberPO memberPO);

    void create(MemberPO memberPO);

    void update(MemberPO memberPO);
}
