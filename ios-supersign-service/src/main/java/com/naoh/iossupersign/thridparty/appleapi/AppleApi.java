package com.naoh.iossupersign.thridparty.appleapi;

import cn.hutool.core.codec.Base64;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import sun.security.ec.ECPrivateKeyImpl;

import java.security.InvalidKeyException;

public abstract class AppleApi {

    public HttpHeaders getToken(String p8, String iss, String kid) {
        String s = p8.
                replace("-----BEGIN PRIVATE KEY-----", "").
                replace("-----END PRIVATE KEY-----", "");
        byte[] encodeKey = Base64.decode(s);
        String token = null;
        try {
            token = Jwts.builder().
                    setHeaderParam(JwsHeader.ALGORITHM, "ES256").
                    setHeaderParam(JwsHeader.KEY_ID,kid).
                    setHeaderParam(JwsHeader.TYPE, "JWT").

                    setIssuer(iss).
                    claim("exp", System.currentTimeMillis()/1000 +  60 * 10).
                    setAudience("appstoreconnect-v1").
                    signWith(SignatureAlgorithm.ES256, new ECPrivateKeyImpl(encodeKey)).
                    compact();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }
}
