package com.naoh.iossupersign.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class DomainConfig {

    @Value("${url.ipa}")
    private String ipaUrlPath;

    @Value("${url.static}")
    private String staticUrlPath;

    @Value("${url.mobileConfig}")
    private String mobileConfigUrlPath;
}