package com.github.sjroom.common.jdbc.entity;

import com.github.sjroom.common.util.SqlReservedWordsUtil;
import com.github.sjroom.common.jdbc.annotation.TableField;
import com.github.sjroom.common.jdbc.annotation.TableId;
import com.github.sjroom.common.jdbc.annotation.TableName;
import com.github.sjroom.common.jdbc.entity.DbTableInfo;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <B>说明：创建表信息的帮助类</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-12 13-59
 */
public class DbTableHelper {

    /**
     * 缓存反射类表信息
     */
    private static final Map<String, com.github.sjroom.common.jdbc.entity.DbTableInfo> tableInfoCache = new ConcurrentHashMap<String, com.github.sjroom.common.jdbc.entity.DbTableInfo>();

    /**
     * <p>
     * 实体类反射获取表信息【初始化】
     * <p>
     *
     * @param currentNameSpace 该实体类的路径
     * @param entityClass      实体类
     * @return
     */
    public synchronized static com.github.sjroom.common.jdbc.entity.DbTableInfo initTableInfo(String currentNameSpace, Class<?> entityClass) {
        com.github.sjroom.common.jdbc.entity.DbTableInfo dbTableInfo = getDbTableInfo(entityClass.getName());
        if (dbTableInfo != null) {
            return dbTableInfo;
        }

        dbTableInfo = new com.github.sjroom.common.jdbc.entity.DbTableInfo();
        dbTableInfo.setNamespace(currentNameSpace);

        /* 表名 */
        TableName table = entityClass.getAnnotation(TableName.class);
        if (table == null || StringUtils.isEmpty(table.value())) {
            String errMsg = String.format("error msg: %s.class must contain @TableName(name='xxxx') annotation", entityClass.getName());
            throw new RuntimeException(errMsg);
        }
        dbTableInfo.setTableName(table.value());

        /* 获取该表需要映射的所有字段 */
        List<Field> dbFieldList = getAllFields(entityClass);

        validationExistAnnotation(dbFieldList, entityClass);

        List<DbTableFieldInfo> dbTableFieldInfoList = new ArrayList<DbTableFieldInfo>();
        for (Field field : dbFieldList) {

            /**
             * 主键ID 初始化
             */
            if (dbTableInfo.getKeyColumn() == null) {
                initTableId(dbTableInfo, field);
                continue;
            }

            /**
             * 初始化每个字段
             */
            TableField tableField = field.getAnnotation(TableField.class);
            DbTableFieldInfo dbTableFieldInfo = new DbTableFieldInfo(tableField.value(), field.getName());
            dbTableFieldInfo.setRelated(SqlReservedWordsUtil.containsWord(tableField.value()));
            dbTableFieldInfoList.add(dbTableFieldInfo);
        }

        /* 字段列表 */
        dbTableInfo.setDbTableFieldInfoList(dbTableFieldInfoList);
        /*
         * 缓存
         */
        tableInfoCache.put(entityClass.getName(), dbTableInfo);
        return dbTableInfo;
    }

    /**
     * 获取缓存的表信息
     *
     * @param entityClassName
     * @return
     */
    public static com.github.sjroom.common.jdbc.entity.DbTableInfo getDbTableInfo(String entityClassName) {
        com.github.sjroom.common.jdbc.entity.DbTableInfo dbTableInfo = tableInfoCache.get(entityClassName);
        if (dbTableInfo != null) {
            return dbTableInfo;
        }
        return null;
    }

    /**
     * 获取该类的所有属性列表
     *
     * @param entityClass 反射类
     * @return
     */
    private static List<Field> getAllFields(Class<?> entityClass) {
        List<Field> result = new LinkedList<Field>();
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {

            /* 过滤静态属性 */
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            /* 过滤 transient关键字修饰的属性 */
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            /* 过滤注解非表字段属性 */
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField == null || tableField.exist()) {
                result.add(field);
            }
        }

        /* 处理父类字段 */
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass.equals(Object.class)) {
            return result;
        }
        result.addAll(getAllFields(superClass));
        return result;
    }

    /**
     * <p>
     * 主键属性初始化
     * </p>
     *
     * @param dbTableInfo
     * @param field
     * @return true 继续下一个属性判断，返回 continue;
     */
    private static void initTableId(DbTableInfo dbTableInfo, Field field) {
        String column = field.getName();
        TableId tableId = field.getAnnotation(TableId.class);
        if (!StringUtils.isEmpty(tableId.value())) {
            column = tableId.value();
        }
        dbTableInfo.setKeyColumn(column);
        dbTableInfo.setKeyProperty(field.getName());

    }


    /**
     * <p>
     * 判断主键注解是否存在
     * </p>
     *
     * @param fieldList 字段列表
     * @return
     */
    public static void validationExistAnnotation(List<Field> fieldList, Class<?> entityClass) {
        String errMsg = null;
        for (Field field : fieldList) {
            TableId tableId = field.getAnnotation(TableId.class);
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableId != null && tableField != null) {
                errMsg = String.format("error msg: %s field %s can not contain @TableId and @TableField annotation at the same time", entityClass.getName(), field.getName());
            } else if (tableId == null && tableField == null) {
                errMsg = String.format("error msg: %s field %s have one @TableId and @TableField annotation at the same time", entityClass.getName(), field.getName());
            }
        }

        if (errMsg != null) {
            throw new RuntimeException(errMsg);
        }
    }

}
