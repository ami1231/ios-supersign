package com.naoh.iossupersign.model.bo;

import com.naoh.iossupersign.model.po.AppleAccountPO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthorizeBO {

    private String csr;

    private String p8;

    private String iss;

    private String kid;

    public AuthorizeBO(AppleAccountPO appleAccountPO){
        this.iss = appleAccountPO.getIssuerId();
        this.kid = appleAccountPO.getKid();
        this.p8 = appleAccountPO.getP8();
    }

}
