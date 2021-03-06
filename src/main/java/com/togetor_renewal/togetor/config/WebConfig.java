package com.togetor_renewal.togetor.config;

import com.togetor_renewal.togetor.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/post/write", "/user/info/modify/{userId}","/post/modify/{postId}",
                        "/user/bookmarkList/{userId}", "/user/myPostList/{userId}");
    }
}
