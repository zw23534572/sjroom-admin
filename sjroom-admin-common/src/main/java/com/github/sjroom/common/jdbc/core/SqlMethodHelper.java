package com.github.sjroom.common.jdbc.core;

import com.github.sjroom.common.util.SqlReservedWordsUtil;
import com.github.sjroom.common.jdbc.entity.DbTableFieldInfo;
import com.github.sjroom.common.jdbc.entity.DbTableInfo;
import com.github.sjroom.common.jdbc.enums.FieldStrategyEnum;
import com.github.sjroom.common.jdbc.enums.SqlMethodEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * sql 帮助类
 *
 * @author manson.zhou
 * @data 2018/2/11.
 */
public class SqlMethodHelper {

    private static Logger logger = LoggerFactory.getLogger(com.github.sjroom.common.jdbc.core.SqlMethodHelper.class);

    /**
     * <p>
     * SQL 查询条件
     * </p>
     *
     * @param space 是否为空判断
     * @return
     */
    private static String scriptSqlWhere(DbTableInfo dbTableInfo, boolean space) {
        StringBuilder where = new StringBuilder();
        if (space) {
            where.append("\n<if test=\"ew!=null\">");
        }
        where.append("\n<where>");
        where.append("\n<if test=\"").append(dbTableInfo.getKeyProperty()).append("!=null\">\n");
        where.append(dbTableInfo.getKeyColumn()).append("=#{").append(dbTableInfo.getKeyProperty()).append("}");
        where.append("\n</if>");
        List<DbTableFieldInfo> fieldList = dbTableInfo.getDbTableFieldInfoList();
        for (DbTableFieldInfo fieldInfo : fieldList) {
            where.append(sqlIfStart(fieldInfo, ""));
            where.append(" AND ").append(fieldInfo.getColumn()).append("=#{").append(fieldInfo.getProperty()).append("}");
            where.append(sqlIfEnd());
        }
        where.append("\n</where>");
        if (space) {
            where.append("\n</if>");
        }
        return where.toString();
    }

    /**
     * <p>
     * SQL 查询所有表字段
     * </p>
     *
     * @param dbTableInfo
     * @return
     */
    private static String scriptSqlSelectColumns(DbTableInfo dbTableInfo) {
        //主键
        StringBuilder columns = new StringBuilder();
        columns.append(SqlReservedWordsUtil.convert(dbTableInfo.getKeyColumn()));
        columns.append(" AS ").append(dbTableInfo.getKeyProperty());

        //其它字段赋值
        List<DbTableFieldInfo> dbTableFieldInfoList = dbTableInfo.getDbTableFieldInfoList();
        for (DbTableFieldInfo fieldInfo : dbTableFieldInfoList) {
            columns.append(",").append(fieldInfo.getColumn());
            if (fieldInfo.isRelated()) {
                columns.append(" AS ").append(SqlReservedWordsUtil.convert(fieldInfo.getProperty()));
                continue;
            }

            if (!fieldInfo.getProperty().equals(fieldInfo.getColumn())) {
                columns.append(" AS ").append(fieldInfo.getProperty());
            }
        }

        /*
         * 返回所有查询字段内容
         */
        return columns.toString();
    }

    /**
     * sql 批次ID 的拼接
     *
     * @return
     */
    private static String scriptSqlIds() {
        StringBuilder ids = new StringBuilder();
        ids.append("\n<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">");
        ids.append("#{item}");
        ids.append("\n</foreach>");
        return ids.toString();
    }

    /**
     * <p>
     * IF 条件转换方法
     * </p>
     *
     * @param prefix 条件前缀
     * @return
     */
    private static String sqlIfStart(DbTableFieldInfo dbTableFieldInfo, String prefix) {
        /* 前缀处理 */
        String property = dbTableFieldInfo.getProperty();
        if (null != prefix) {
            property = prefix + property;
        }

        // 验证逻辑
        FieldStrategyEnum fieldStrategy = dbTableFieldInfo.getFieldStrategyEnum();
//        if (fieldStrategy == FieldStrategyEnum.NOT_EMPTY) {
//            return String.format("\n\t<if test=\"%s!=null and %s!=''\">", property, property);
//        } else {
        // FieldStrategyEnum.NOT_NULL
        return String.format("\n\t<if test=\"%s!=null\">", property);
//        }
    }

    private static String sqlIfStart(DbTableFieldInfo dbTableFieldInfo) {
        return sqlIfStart(dbTableFieldInfo, "");
    }

    private static String sqlIfEnd() {
        return "</if>";
    }

    /**
     * <p>
     * SQL 更新 set 语句
     * </p>
     *
     * @param dbTableInfo
     * @param prefix      前缀
     * @return
     */
    private static String sqlSet(DbTableInfo dbTableInfo, String prefix) {
        StringBuilder set = new StringBuilder();
        set.append("<trim prefix=\"SET\" suffixOverrides=\",\">");
        List<DbTableFieldInfo> fieldList = dbTableInfo.getDbTableFieldInfoList();
        for (DbTableFieldInfo dbTableFieldInfo : fieldList) {
            set.append(sqlIfStart(dbTableFieldInfo, null));
            set.append(dbTableFieldInfo.getColumn()).append("=#{");
            if (null != prefix) {
                set.append(prefix);
            }
            set.append(dbTableFieldInfo.getProperty()).append("},");
            set.append(sqlIfEnd());
        }
        set.append("\n</trim>");
        return set.toString();
    }


    /**
     * <p>
     * 注入插入 SQL 语句
     * <p>
     * </p>
     * sql样例
     * INSERT INTO table
     * <trim prefix="(" suffix=")" suffixOverrides=",">
     * <if test="xx != null">xx,</if>
     * </trim>
     * <trim prefix="values (" suffix=")" suffixOverrides=",">
     * <if test="xx != null">#{xx},</if>
     * </trim>
     *
     * @param dbTableInfo
     * @return 返回sql
     */
    public static String sqlInsert(DbTableInfo dbTableInfo) {

        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder placeholderBuilder = new StringBuilder();
        fieldBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        placeholderBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        /** 主键 */
        fieldBuilder.append(dbTableInfo.getKeyColumn()).append(",");
        placeholderBuilder.append("#{").append(dbTableInfo.getKeyProperty()).append("},");

        List<DbTableFieldInfo> fieldList = dbTableInfo.getDbTableFieldInfoList();
        for (DbTableFieldInfo fieldInfo : fieldList) {
            fieldBuilder.append(sqlIfStart(fieldInfo));
            fieldBuilder.append(fieldInfo.getColumn()).append(",");
            fieldBuilder.append(sqlIfEnd());

            placeholderBuilder.append(sqlIfStart(fieldInfo));
            placeholderBuilder.append("#{").append(fieldInfo.getProperty()).append("},");
            placeholderBuilder.append(sqlIfEnd());
        }
        fieldBuilder.append("\n</trim>");
        placeholderBuilder.append("\n</trim>");
        String sql = String.format(SqlMethodEnum.INSERT.getSql(), dbTableInfo.getTableName(),
                fieldBuilder.toString(), placeholderBuilder.toString());
        logger.debug("sqlInsert result:{}", sql);
        return sql;
    }

    /**
     * <p>
     * 注入删除 SQL 语句
     * </p>
     */
    public static String sqlDeleteById(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.DELETE_BY_ID.getSql(), dbTableInfo.getTableName(),
                dbTableInfo.getKeyColumn(), dbTableInfo.getKeyColumn());
        logger.debug("{}.deleteById result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }


    /**
     * <p>
     * 注入删除 SQL 语句
     * </p>
     */
    public static String sqlDeleteBatchByIds(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.DELETE_BATCH_BY_IDS.getSql(), dbTableInfo.getTableName(),
                dbTableInfo.getKeyColumn(), scriptSqlIds());
        logger.debug("{}.deleteBatchByIds result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }


    /**
     * <p>
     * 注入-根据主键进行更新
     * </p>
     */
    public static String sqlUpdateById(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.UPDATE_BY_ID.getSql(), dbTableInfo.getTableName(),
                sqlSet(dbTableInfo, null), dbTableInfo.getKeyColumn(), dbTableInfo.getKeyProperty());
        logger.debug("{}.updateById result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }


    /**
     * <p>
     * 注入查询 SQL 语句
     * </p>
     */
    public static String sqlSelectById(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.SELECT_BY_ID.getSql(), scriptSqlSelectColumns(dbTableInfo),
                dbTableInfo.getTableName(), dbTableInfo.getKeyColumn(), dbTableInfo.getKeyProperty());
        logger.debug("{}.selectById result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }

    /**
     * <p>
     * 注入实体查询 SQL 语句
     * </p>
     */
    public static String sqlSelectOne(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.SELECT_ONE.getSql(), scriptSqlSelectColumns(dbTableInfo),
                dbTableInfo.getTableName(), scriptSqlWhere(dbTableInfo, false));
        logger.debug("{}.selectOne result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }

    /**
     * <p>
     * 注入实体查询 SQL 语句
     * </p>
     */
    public static String sqlSelectList(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.SELECT_LIST.getSql(), scriptSqlSelectColumns(dbTableInfo),
                dbTableInfo.getTableName(), scriptSqlWhere(dbTableInfo, false));
        logger.debug("{}.selectList result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }

    /**
     * <p>
     * 注入实体查询 SQL 语句
     * </p>
     */
    public static String sqlSelectCount(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.SELECT_COUNT.getSql(), dbTableInfo.getTableName(),
                scriptSqlWhere(dbTableInfo, false));
        logger.debug("{}.selectCount result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }


    /**
     * <p>
     * 注入批量查询 SQL 语句
     * </p>
     */
    public static String sqlSelectBatchByIds(DbTableInfo dbTableInfo) {
        String sql = String.format(SqlMethodEnum.SELECT_BATCH_BY_IDS.getSql(), scriptSqlSelectColumns(dbTableInfo), dbTableInfo.getTableName(),
                dbTableInfo.getKeyColumn(), scriptSqlIds());
        logger.debug("{}.selectBatchByIds result:{}", dbTableInfo.getNamespace(), sql);
        return sql;
    }

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

}
