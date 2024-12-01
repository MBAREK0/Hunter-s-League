package com.mbarek0.web.huntersleague.config;

import com.mbarek0.web.huntersleague.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class FilterConfig {

    private final JwtAuthenticationFilter jwtCustomFilter;

    public FilterConfig(JwtAuthenticationFilter jwtCustomFilter) {
        this.jwtCustomFilter = jwtCustomFilter;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtCustomFilter);
        registrationBean.addUrlPatterns("/api/*");  // Apply to specific URL patterns as needed
        return registrationBean;
    }

}
