package com.example.hoon_shop.log;

import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicTreeUI;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class UserLogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.example.hoon_shop.controller..*)")
    public void onRequest() {
    }

    @Around("onRequest()")
    public Object requestLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        // user

        long startTime = System.currentTimeMillis();
        try{
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } finally {
            long endTime = System.currentTimeMillis();
            long callTime = endTime - startTime;
            logger.info("Request: {}, {}: {} ({}ms)", request.getMethod(), request.getRequestURI(), paramMapToString(request.getParameterMap()), callTime);
            userInfoLogging();
        }
    }

    public void userInfoLogging() throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails session = (WebAuthenticationDetails) authentication.getDetails();

        String userInfo;
        if (authentication.getPrincipal().getClass() == User.class) {
            UserDetails userVO = (UserDetails) authentication.getPrincipal();
            userInfo = userVO.getUsername() + " " + userVO.getAuthorities();
        } else{
            userInfo = authentication.getPrincipal().toString();
        }

        logger.info("Request userinfo : {}, {}, {}", session.getRemoteAddress(), session.getSessionId(),userInfo);
    }

    private String paramMapToString(Map<String, String[]> paraStringMap) {
        return paraStringMap.entrySet()
            .stream()
            .map(entry -> String.format("%s : %s", entry.getKey(), Arrays.toString(entry.getValue())))
            .collect(Collectors.joining(", "));
    }
}
