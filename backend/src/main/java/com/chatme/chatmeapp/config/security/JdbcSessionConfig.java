package com.chatme.chatmeapp.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@Configuration(proxyBeanMethods = false)
@EnableJdbcHttpSession
public class JdbcSessionConfig {
}
