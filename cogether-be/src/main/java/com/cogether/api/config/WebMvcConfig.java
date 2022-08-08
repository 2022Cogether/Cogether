package com.cogether.api.config;

import com.cogether.api.config.jwt.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtTokenInterceptor jwtTokenInterceptor;

    private final String [] INTERCEPTOR_WHITE_LIST=
            {"/api/sign/*"};

    private final String [] INTERCEPTOR_LIST={
            "/api/til/*","/api/project/*","/api/study/*","/api/hunting/*","/api/livecoop/*","/api/follow"
            ,"/api/follower","/api/following","/api/chat/*","api/user/*"
    };

    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("인터셉터 등록");
        registry.addInterceptor(jwtTokenInterceptor).addPathPatterns(INTERCEPTOR_LIST)
                .excludePathPatterns(INTERCEPTOR_WHITE_LIST);
    }
}
