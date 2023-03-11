package com.offcn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;


@Configuration
public class GatewayCorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //配置跨域
        corsConfiguration.addAllowedHeader("*");//允许所有请求头跨域
        corsConfiguration.addAllowedMethod("*");//允许所有请求方法跨域
        corsConfiguration.addAllowedOrigin("*");//允许所有请求来源跨域
        corsConfiguration.setAllowCredentials(true);//允许携带cookie跨域，否则跨域丢失cookie信息

        source.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(source);
    }
}
