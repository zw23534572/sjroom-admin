package com.github.sjroom.domain.enums;

/**
 * YN
 */
public enum YnEnum {

    /**
     * 有效
     */
    YES(1)

    /** 无效 */
    , NO(0);

    private int code;

    YnEnum(int code) {
        this.code = code;
    }

    public byte getCode() {
        return (byte) code;
    }
}
