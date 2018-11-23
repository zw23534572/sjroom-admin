package com.github.sjroom.common.web;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.sjroom.common.IResult;
import com.github.sjroom.common.response.DataResponse;
import com.github.sjroom.common.util.JsonUtil;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 将消息实体转换成json
 * <p>
 * Created by Zhouwei
 *
 * @date 2018-1-10
 */
public class JsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    /**
     * 申明fastjson转换的格式
     */
    public JsonHttpMessageConverter() {
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(JsonUtil.getSerializerFeatures());
        this.setFastJsonConfig(config);
        this.setDefaultCharset(Charset.forName("UTF-8"));

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        this.setSupportedMediaTypes(supportedMediaTypes);
    }

    /**
     * spring mvc 请求返回时，会经过此方法进行实体转换
     *
     * @param object        返回实体
     * @param type
     * @param contentType
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    public void write(Object object, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // 如果返回的已经是包装类(是CommonResponse子孙类),则直接返回
        if (object != null && object instanceof IResult) {
            logger.debug("JsonHttpMessageConverter object:");
            super.write(object, contentType, outputMessage);
            return;
        }

        /**
         * 封装成包装类
         */
        super.write(new DataResponse(object), contentType, outputMessage);
    }
}
