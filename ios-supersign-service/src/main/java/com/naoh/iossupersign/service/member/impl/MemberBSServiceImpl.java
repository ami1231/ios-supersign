package com.naoh.iossupersign.service.member.impl;

import com.naoh.iossupersign.model.po.MemberPO;
import com.naoh.iossupersign.service.member.MemberBSService;
import com.naoh.iossupersign.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Service
public class MemberBSServiceImpl implements MemberBSService {

    @Autowired
    private MemberService memberService;


    @Override
    public Optional<MemberPO> getMemberByAccount(String account) {
        return memberService.getAccountAllStatus(account);
    }

    @Override
    public List<MemberPO> selectMemberByCondition(MemberPO memberPO) {
        return memberService.selectMemberByCondition(memberPO);
    }

    @Override
    public void create(MemberPO memberPO) {
        memberPO.setCreateTime(LocalDateTime.now());
        memberService.create(memberPO);
    }

    @Override
    public void update(MemberPO memberPO) {
        memberService.update(memberPO);
    }
}
