package com.naoh.iossupersign.config;

import com.naoh.iossupersign.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    private final AuthenticationFailureHandler authenticationErrorHandler;

    @Autowired
    public WebSecurityConfig(AuthenticationService authenticationService, AuthenticationFailureHandler authenticationErrorHandler) {
        this.authenticationService = authenticationService;
        this.authenticationErrorHandler = authenticationErrorHandler;
    }

    @Override
    public void configure(WebSecurity web) {
        //解決靜態資源被攔截的問題
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/metisMenu/**");
        web.ignoring().antMatchers("/front-awesome/**");
        web.ignoring().antMatchers("/bootstrap/**");
        //解決服務註冊url被攔截的問題
        web.ignoring().antMatchers("/ipa/uploadPackage/**");
        web.ignoring().antMatchers("/actuator/*");
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
        web.ignoring().antMatchers("/mobileconfig/*");
        web.ignoring().antMatchers("/ipa/*");
        web.ignoring().antMatchers("/apps/download/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login")
                .failureHandler(authenticationErrorHandler)
//                .defaultSuccessUrl("/changePwd", true)
                .permitAll()
            .and()
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
    }

}
