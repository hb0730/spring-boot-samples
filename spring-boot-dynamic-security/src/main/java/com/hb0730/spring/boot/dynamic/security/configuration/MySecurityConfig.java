package com.hb0730.spring.boot.dynamic.security.configuration;

import com.hb0730.spring.boot.dynamic.security.model.UmsResource;
import com.hb0730.spring.boot.dynamic.security.service.DynamicSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bing_huang
 */

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends SecurityConfig {
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(
                User.withUsername("user")
                        .password(passwordEncoder().encode("123456"))
                        .authorities("ROLE_USER", "/home/**", "/brand/**", "/sku/**", "/order/**").build());
        return userDetailsManager;
    }


    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                for (UmsResource resource : getResource()) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getUrl() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
