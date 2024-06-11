package com.hash.harp.global.config;

import com.hash.harp.global.config.properties.AuthProperties;
import com.hash.harp.global.jwt.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties ({JwtProperties.class, AuthProperties.class})
public class PropertiesConfig {
}
