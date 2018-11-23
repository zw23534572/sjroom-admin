package com.github.sjroom.common.jdbc.entity;

import com.github.sjroom.common.jdbc.entity.DbTableFieldInfo;
import lombok.Data;

import java.util.List;

/**
 * <B>说明：对应的表实体信息</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 22:36
 */
@Data
public class DbTableInfo {
    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表主键ID 属性名
     */
    private String keyProperty;

    /**
     * 表主键ID 字段名
     */
    private String keyColumn;

    /**
     * 描述
     */
    private String comment;

    /**
     * 字段
     */
    private List<DbTableFieldInfo> dbTableFieldInfoList;

}
