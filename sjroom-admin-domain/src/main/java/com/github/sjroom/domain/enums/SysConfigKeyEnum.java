package com.github.sjroom.domain.enums;

/**
 * redis 所有键值统一
 */
public enum SysConfigKeyEnum {
    WECHAT("WECHAT_CONFIG_KEY"),
    DINGDING("DINGDING_CONFIG_KEY");

    String moduleKey;

    private SysConfigKeyEnum(String modelKey) {
        this.moduleKey = modelKey;
    }

    public String key() {
        return moduleKey;
    }
}

