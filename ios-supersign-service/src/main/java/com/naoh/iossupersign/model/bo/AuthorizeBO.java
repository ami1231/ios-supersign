package com.naoh.iossupersign.model.bo;

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

}
