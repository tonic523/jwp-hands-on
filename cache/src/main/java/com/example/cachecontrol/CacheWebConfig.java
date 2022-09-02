package com.example.cachecontrol;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class CacheWebConfig implements WebMvcConfigurer {

    private final CacheInterceptors cacheInterceptors;

    public CacheWebConfig(CacheInterceptors cacheInterceptors) {
        this.cacheInterceptors = cacheInterceptors;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(cacheInterceptors)
                .addPathPatterns("/**");
    }
}
