package com.naoh.iossupersign.service.member;

import com.naoh.iossupersign.model.po.MemberPO;

import java.util.Optional;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
public interface MemberBSService {

    Optional<MemberPO> getMemberByAccount(String account);
}
