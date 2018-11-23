package com.github.sjroom.common.jdbc.entity;

import lombok.Data;

/**
 * <B>说明：用于制作枚举类的实体</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-11 17:46
 */
@Data
public class EnumMethodModel {

    /**
     * 枚举的字段
     */
    private String fieldName;
    /**
     * 枚举的code
     */
    private Integer code;
    /**
     * 枚举的消息
     */
    private String msg;
}
