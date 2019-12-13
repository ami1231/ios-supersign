package com.naoh.iossupersign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Create by Ami.Tsai on 2019/5/9
 */
@Configuration
public class TransactionManagerConfig {

    @Bean
    public static DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
