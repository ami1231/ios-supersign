package com.naoh.iossupersign.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

}
