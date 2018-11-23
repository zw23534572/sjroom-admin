package com.github.sjroom.common.jdbc.enums;

/**
 * <B>说明：BaseMapper 支持 SQL 枚举</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 23:23
 */
public enum SqlMethodEnum {
    /**
     * 查询
     */
    SELECT_BY_ID("selectById", "根据ID 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s}"),
    SELECT_BATCH_BY_IDS("selectBatchIds", "根据ID集合，批量查询数据", "SELECT %s FROM %s WHERE %s IN (%s)"),
    SELECT_ONE("selectOne", "查询满足条件一条数据", "SELECT %s FROM %s %s limit 1"),
    SELECT_LIST("selectList", "查询满足条件所有数据", "SELECT %s FROM %s %s"),
    SELECT_COUNT("selectCount", "查询满足条件总记录数", "SELECT COUNT(1) FROM %s %s"),

    /**
     * 插入
     */
    INSERT("insert", "插入一条数据", "INSERT INTO %s %s VALUES %s"),

    /**
     * 修改
     */
    UPDATE_BY_ID("updateById", "根据ID 修改数据", "UPDATE %s %s WHERE %s=#{%s}"),

    /**
     * 删除
     */
    DELETE_BY_ID("deleteById", "根据ID 删除一条数据", "DELETE FROM %s WHERE %s=#{%s}"),
    DELETE_BATCH_BY_IDS("deleteBatchByIds", "根据ID集合，批量删除数据", "DELETE FROM %s WHERE %s IN (%s)");

    private final String id;

    private final String desc;

    private final String sql;


    SqlMethodEnum(final String method, final String desc, final String sql) {
        this.id = method;
        this.desc = desc;
        this.sql = sql;
    }

    /**
     * 获取mybatis mapper Id
     * @return
     */
    public String getId() {
        return this.id;
    }


    public String getDesc() {
        return this.desc;
    }


    public String getSql() {
        return "<script>" + this.sql + "</script>";
    }


}
