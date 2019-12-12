package com.naoh.iossupersign.sercuity;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Data
public class MemberUserDetails extends User {

    public MemberUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
