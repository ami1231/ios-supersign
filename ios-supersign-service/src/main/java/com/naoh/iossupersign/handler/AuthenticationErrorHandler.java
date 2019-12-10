package com.naoh.iossupersign.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Component
@Slf4j
public class AuthenticationErrorHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String DEFAULT_ERROR_URL = "/login?error";

    private static final String ERROR_BASIC_MSG = "账号或密码错误";

    private static final String ERROR_MEMBER_LOCKED_MSG = "用户已被锁定";

    private static final String ERROR_MEMBER_DISABLE_MSG = "用户已被停用";

    private static final String ERROR_OTHER_MSG = "未知的错误";

    @Autowired
    public AuthenticationErrorHandler() {
        super(DEFAULT_ERROR_URL);
        super.setUseForward(true);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {

        AuthenticationException newException = new InternalAuthenticationServiceException(ERROR_OTHER_MSG);
        String account = httpServletRequest.getParameter("username");

        if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            newException = new BadCredentialsException(ERROR_BASIC_MSG);
        } else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
            newException = new DisabledException(ERROR_MEMBER_DISABLE_MSG);
        } else if (exception.getClass().isAssignableFrom(LockedException.class)) {
            newException = new LockedException(ERROR_MEMBER_LOCKED_MSG);
        }

        log.error("member login error member: {}, ,errorMsg: {}", account, newException.getMessage());

        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, newException);

    }

}

