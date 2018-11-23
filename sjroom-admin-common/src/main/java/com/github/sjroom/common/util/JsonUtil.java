
package com.github.sjroom.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 *  fastjson工具类
 *  @author zisong.wang
 *  @updateDate 2018/01/15
 */
public class JsonUtil {
    private JsonUtil() {
    }

    /**
     * SerializerFeature.PrettyFormat -json 格式化
     * SerializerFeature.WriteDateUseDateFormat -时间格式转化
     * SerializerFeature.WriteMapNullValue -保留空的字段
     * SerializerFeature.WriteNullStringAsEmpty -String null -> ""
     * SerializerFeature.WriteNullNumberAsZero -Number null -> 0
     */
    private static SerializerFeature[] serializerFeatures = new SerializerFeature[]{
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullNumberAsZero};

    public static SerializerFeature[] getSerializerFeatures() {
        return serializerFeatures;
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, serializerFeatures);
    }

    public static String toJson(Object object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

    public static Object parse(String json) {
        return JSON.parse(json);
    }

    public static <T> T parse(String json, Class<T> tClass) {
        return JSON.parseObject(json, tClass);
    }

    public static String toJsonSuccess(String msg, Object obj) {
        Map<String, Object> mp = new HashMap<>(4);
        mp.put("status", 1);
        mp.put("state", "success");
        mp.put("msg", msg);
        mp.put("result", obj);
        return toJson(mp);
    }

    public static String toJsonError(String msg, Object obj) {
        Map<String, Object> mp = new HashMap<>(4);
        mp.put("status", 0);
        mp.put("state", "error");
        mp.put("msg", msg);
        mp.put("result", obj);
        return toJson(mp);
    }

}
