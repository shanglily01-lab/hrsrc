package com.pmp.hrsrc.config;

import com.pmp.hrsrc.util.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置上传文件的访问路径
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration =registry.addInterceptor(new UserLoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                "/login",
                "/api/login",
                "/api/validateUser",
                "/api/captcha",
                "/api/validateCaptcha",
                "/**/*.js",
                "/**/*.css",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.ico",
                "/**/*.svg",
                "/**/*.woff",
                "/**/*.woff2",
                "/**/*.ttf",
                "/**/*.eot",
                "/css/**",
                "/js/**",
                "/images/**",
                "/fonts/**",
                "/static/**",
                "/webjars/**",
                "/bootstrap/**",
                "/sweetalert2/**"
        );
    }
} 