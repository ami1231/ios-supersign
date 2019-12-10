package com.naoh.iossupersign.service.authentication;

import com.naoh.iossupersign.model.po.MemberPO;
import com.naoh.iossupersign.sercuity.MemberUserDetails;
import com.naoh.iossupersign.service.member.MemberBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private MemberBSService memberBSService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        MemberPO member = memberBSService.getMemberByAccount(account).orElseThrow(() -> new UsernameNotFoundException("找不到資料"));

//        //  檢查狀態
//        if (LoginStatus.LOCK.name().equals(member.getStatus())) {
//            return MemberUserDetails.getLockUser();
//        } else if (LoginStatus.SUSPEND.name().equals(member.getStatus())) {
//            return MemberUserDetails.getDisableUser();
//        }

        return new MemberUserDetails(member.getAccount(),
                member.getPasswd(), new ArrayList<>());
    }

}
