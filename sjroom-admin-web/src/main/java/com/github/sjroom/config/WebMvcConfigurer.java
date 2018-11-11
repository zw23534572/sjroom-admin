package com.github.sjroom.config;

import com.github.sjroom.oauth2.UrlInterceptor;
import com.github.sjroom.service.ShiroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-11-10 12-49
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    protected static Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    @Autowired
    ShiroService shiroService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("添加拦截器");
        registry.addInterceptor(new UrlInterceptor(shiroService))
                .addPathPatterns("/**")
                .excludePathPatterns("/login/index")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/threadDump")
                .excludePathPatterns("/simpleMonitor")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/sys/login")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.html")
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("/plugins/**")
                .excludePathPatterns("/swagger/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/number/**")
                .excludePathPatterns("/numberApi/**")
                .excludePathPatterns("/");
    }
}
