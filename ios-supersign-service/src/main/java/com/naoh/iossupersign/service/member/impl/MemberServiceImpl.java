package com.naoh.iossupersign.service.member.impl;

import com.naoh.iossupersign.model.po.MemberPO;
import com.naoh.iossupersign.service.member.MemberService;
import com.naoh.iossupersign.storage.mysql.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Service
public class MemberServiceImpl implements MemberService {


    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Optional<MemberPO> getAccountAllStatus(String account) {
        return Optional.ofNullable(memberMapper.getByAccount(account));
    }

    @Override
    public List<MemberPO> selectMemberByCondition(MemberPO memberPO) {
        return memberMapper.selectMemberByCondition(memberPO);
    }

    @Override
    public void create(MemberPO memberPO) {
        memberMapper.insert(memberPO);
    }

    @Override
    public void update(MemberPO memberPO) {
        memberMapper.update(memberPO);
    }
}
