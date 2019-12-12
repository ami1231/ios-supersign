package com.naoh.iossupersign.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPO implements Serializable {

    // id
    private Long id;

    // 帐号
    private String account;

    // 密码
    private String password;

    // 创建时间
    private LocalDateTime createTime;


}
