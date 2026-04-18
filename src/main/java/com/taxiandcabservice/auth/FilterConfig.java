package com.taxiandcabservice.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    private final AuthFilter authFilter;

    public FilterConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> logFilter() {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authFilter);
        registration.addUrlPatterns("/api/passenger/auth/test");
        return registration;
    }
}
