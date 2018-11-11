package com.github.sjroom.domain.enums;

/**
 * redis 所有键值统一
 */
public enum RedisKeyEnum {
    WECHAT_ACCESS_TOKEN("weixin:accesstoken"),
    DINGDING_ACCESS_TOKEN("dingding:accesstoken");

    String moduleKey;

    private RedisKeyEnum(String modelKey) {
        this.moduleKey = modelKey;
    }

    public String key(String key) {
        return moduleKey + ":" + key;
    }
}

