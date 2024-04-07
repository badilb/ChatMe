package com.chatme.chatmeapp.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Slf4j
@Aspect
@Component
public class RequestsLoggingAspect {
    private final MeterRegistry meterRegistry;

    public RequestsLoggingAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("execution(* com.chatme.chatmeapp.controller.http.v1.*.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Extracting class name and method name
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String className = methodSignature.getDeclaringType().getSimpleName();
            String methodName = methodSignature.getName();
            String classMethod = className + "." + methodName;

            // Getting request details
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = requestAttributes.getRequest();

            // Getting user authentication details
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication != null ? authentication.getName() : "anonymous";

            // Logging request details
            String requestURL = request.getRequestURL().toString();
            String strippedURL = requestURL.substring(requestURL.indexOf("/", 8));
            log.info("Request | Remote Address: {} | URL: {} | User: {}", request.getRemoteAddr(), strippedURL, username);

            // Timing method execution
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();
            long time = stopWatch.getTotalTimeMillis();

            // Logging response time
            log.info("Response | Method: {} | URL: {}  | Time taken: {} ms", classMethod, strippedURL, time);

            // Sending response time metric to MetricBinder
            meterRegistry.counter("http.requests.duration.milliseconds", List.of(Tag.of("time", String.valueOf(time))))
                    .increment();

            return result;
        } catch (Exception ex) {
            // Logging exceptions
            log.error("Exception during method execution: {}", ex.getMessage());
            throw ex;
        }
    }
}
