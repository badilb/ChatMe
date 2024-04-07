package com.chatme.chatmeapp.config.security;

import com.chatme.chatmeapp.models.enums.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilterChainConfig {
    private final String[] authEndpoints  = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/logout"
    };

    private final String[] actuatorEndpoints  = {
            "/metrics",
            "/actuator",
            "/actuator/prometheus"
    };

    private final String[] openApiEndpoints = {
            "/v3/api-docs",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable csrf for a while
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, authEndpoints).permitAll()
                        .requestMatchers(actuatorEndpoints).access(
                                new WebExpressionAuthorizationManager("hasIpAddress('localhost')"))
                        .anyRequest().authenticated())
                .securityContext(context -> context
                            .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository(),
                                new HttpSessionSecurityContextRepository()
                            ))
                );

        return http.build();
    }
}
