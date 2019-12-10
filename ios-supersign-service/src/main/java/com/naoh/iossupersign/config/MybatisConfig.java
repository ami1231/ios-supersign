package com.naoh.iossupersign.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Configuration
@MapperScan("com.naoh.iossupersign.storage.mapper")
public class MybatisConfig {
}
