package com.chatme.chatmeapp.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class RequestsLoggingAspect {

    @Around("execution(* com.chatme.chatmeapp.controller.http.v1.*.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "anonymous";

            String requestURL = request.getRequestURL().toString();
            String strippedURL = requestURL.substring(requestURL.indexOf("/" , 8));

            log.info("Request | {} | {} | {}", request.getRemoteAddr(), strippedURL, username);

            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long time = System.currentTimeMillis() - start;

            log.info("Response | {} | {} ms", result, time);

            return result;
        } catch (Exception ex) {
            log.error("Exception during method execution: {}", ex.getMessage());
            throw ex;
        }
    }
}
