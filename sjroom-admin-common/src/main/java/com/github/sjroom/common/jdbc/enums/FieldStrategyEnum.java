package com.github.sjroom.common.jdbc.enums;

/**
 * <p>
 * 字段策略枚举类
 * </p>
 *
 * @author Zhouwei
 */
public enum FieldStrategyEnum {
    NULL(0, "null"),
    NOT_NULL(1, "not null");

    /**
     * 主键
     */
    private final int key;

    /**
     * 描述
     */
    private final String desc;

    FieldStrategyEnum(final int key, final String desc) {
        this.key = key;
        this.desc = desc;
    }

    public int getKey() {
        return this.key;
    }

    public String getDesc() {
        return this.desc;
    }

    public static com.github.sjroom.common.jdbc.enums.FieldStrategyEnum getFieldStrategy(int key) {
        com.github.sjroom.common.jdbc.enums.FieldStrategyEnum[] fss = com.github.sjroom.common.jdbc.enums.FieldStrategyEnum.values();
        for (com.github.sjroom.common.jdbc.enums.FieldStrategyEnum fs : fss) {
            if (fs.getKey() == key) {
                return fs;
            }
        }
        return com.github.sjroom.common.jdbc.enums.FieldStrategyEnum.NOT_NULL;
    }

}
