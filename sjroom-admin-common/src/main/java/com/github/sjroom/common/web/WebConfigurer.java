package com.github.sjroom.common.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


/**
 * Spring MVC 配置
 */
public class WebConfigurer extends WebMvcConfigurerAdapter {

    protected static Logger logger = LoggerFactory.getLogger(WebConfigurer.class);

    @Autowired
    JsonHttpMessageConverter jsonHttpMessageConverter;

    /**
     * json消息转换类
     * @return
     */
    @Bean
    public JsonHttpMessageConverter jsonHttpMessageConverter() {
        logger.info("MvcConfigurer JsonHttpMessageConverter spring inited");
        return new JsonHttpMessageConverter();
    }

    /**
     * 消息返回时，统一处理
     * 1.有fastjson进行处理，日期格式化等等
     * 2.封装成公司的包装类commonResponse
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("MvcConfigurer configureMessageConverters started");
        converters.add(jsonHttpMessageConverter);
    }


    /**
     * 统一异常处理
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        logger.info("MvcConfigurer configureHandlerExceptionResolvers started");
        exceptionResolvers.add(new JsonMappingExceptionResolver());
    }

}
