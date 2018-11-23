
package com.github.sjroom.common.jdbc.core;

import com.github.sjroom.common.jdbc.entity.DbTableHelper;
import com.github.sjroom.common.jdbc.entity.DbTableInfo;
import com.github.sjroom.common.jdbc.enums.SqlMethodEnum;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>
 * SQL 自动注入器
 * </p>
 *
 * @author Zhouwei
 */
public class SqlMethodInjector {

    private static Logger logger = LoggerFactory.getLogger(com.github.sjroom.common.jdbc.core.SqlMethodInjector.class);

    private Configuration configuration;
    private LanguageDriver languageDriver;
    private MapperBuilderAssistant builderAssistant;

    public SqlMethodInjector(Configuration configuration) {
        this.configuration = configuration;
        this.languageDriver = configuration.getDefaultScriptingLanguageInstance();
    }

    /**
     * 开始注入mapperClass
     *
     * @param mapperClass
     */
    public void inject(Class<?> mapperClass) {
        logger.debug("inject mapperClass stared: {}", mapperClass.getName());
        this.builderAssistant = new MapperBuilderAssistant(configuration, mapperClass.getName().replaceAll("\\.", "/"));
        this.builderAssistant.setCurrentNamespace(mapperClass.getName());

        Class<?> entityClass = extractEntityClass(mapperClass);
        if (entityClass == null) {
            logger.warn("current mapperClass:{} is not have implements baseMapper<T>", mapperClass);
            return;
        }

        DbTableInfo dbTableInfo = DbTableHelper.initTableInfo(this.builderAssistant.getCurrentNamespace(), entityClass);

        /**
         * 注入-sql
         */
        this.injectInsertSql(mapperClass, entityClass, dbTableInfo);
        this.injectUpdateSql(mapperClass, entityClass, dbTableInfo);
        this.injectDelectSql(mapperClass, entityClass, dbTableInfo);
        this.injectSelectSql(mapperClass, entityClass, dbTableInfo);

        logger.debug("inject mapperClass end: {}.{}", mapperClass.getPackage(), mapperClass.getSimpleName());
    }

    /**
     * 提取数据库entity实体类
     *
     * @param mapperClass
     * @return
     */
    private Class<?> extractEntityClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        ParameterizedType target = null;
        for (Type type : types) {
            if (type instanceof ParameterizedType
                    && ((ParameterizedType) type).getRawType().equals(BaseMapper.class)) {
                target = (ParameterizedType) type;
                break;
            }
        }
        Type[] parameters = target.getActualTypeArguments();
        Class<?> entityClass = (Class<?>) parameters[0];
        return entityClass;
    }


    /**
     * <p>
     * 注入插入 SQL 语句
     * </p>
     */
    private void injectInsertSql(Class<?> mapperClass, Class<?> entityClass, DbTableInfo dbTableInfo) {
        /**
         * 注入sql:insert
         */
        SqlMethodEnum sqlMethodEnum = SqlMethodEnum.INSERT;
        String sql = SqlMethodHelper.sqlInsert(dbTableInfo);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.INSERT, entityClass, null,
                Integer.class, new Jdbc3KeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());
    }

    /**
     * <p>
     * 注入插入 UPDATE 语句
     * </p>
     */
    private void injectUpdateSql(Class<?> mapperClass, Class<?> entityClass, DbTableInfo dbTableInfo) {
        /**
         * 注入sql:updateById
         */
        SqlMethodEnum sqlMethodEnum = SqlMethodEnum.UPDATE_BY_ID;
        String sql = SqlMethodHelper.sqlUpdateById(dbTableInfo);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.UPDATE, entityClass, null, Integer.class,
                new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());

    }

    /**
     * <p>
     * 注入 删除 语句
     * deleteById,delete,deleteBatchByIds方法
     * </p>
     */
    private void injectDelectSql(Class<?> mapperClass, Class<?> entityClass, DbTableInfo dbTableInfo) {
        /**
         * 注入sql:deleteById
         */
        SqlMethodEnum sqlMethodEnum = SqlMethodEnum.DELETE_BY_ID;
        String sql = SqlMethodHelper.sqlDeleteById(dbTableInfo);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.DELETE, entityClass, null,
                Integer.class, new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());

        /**
         * 注入sql:deleteBatchByIds
         */
        sqlMethodEnum = SqlMethodEnum.DELETE_BATCH_BY_IDS;
        sql = SqlMethodHelper.sqlDeleteBatchByIds(dbTableInfo);
        sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.DELETE, entityClass, null, Integer.class,
                new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());
    }

    /**
     * <p>
     * 注入 删除 语句
     * deleteById,delete,deleteBatchByIds方法
     * </p>
     */
    private void injectSelectSql(Class<?> mapperClass, Class<?> entityClass, DbTableInfo dbTableInfo) {
        /**
         * 注入sql:selectById
         */
        SqlMethodEnum sqlMethodEnum = SqlMethodEnum.SELECT_BY_ID;
        String sql = SqlMethodHelper.sqlSelectById(dbTableInfo);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.SELECT, entityClass, null,
                entityClass, new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());

        /**
         * 注入sql:selectBatchIds
         */
        sqlMethodEnum = SqlMethodEnum.SELECT_BATCH_BY_IDS;
        sql = SqlMethodHelper.sqlSelectBatchByIds(dbTableInfo);
        sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.SELECT, entityClass, null,
                entityClass, new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());

        /**
         * 注入sql:selectOne
         */
        sqlMethodEnum = SqlMethodEnum.SELECT_ONE;
        sql = SqlMethodHelper.sqlSelectOne(dbTableInfo);
        sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.SELECT, entityClass, null,
                entityClass, new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());

        /**
         * 注入sql:selectList
         */
        sqlMethodEnum = SqlMethodEnum.SELECT_LIST;
        sql = SqlMethodHelper.sqlSelectList(dbTableInfo);
        sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.SELECT, entityClass, null,
                entityClass, new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());


        /**
         * 注入sql:selectCount
         */
        sqlMethodEnum = SqlMethodEnum.SELECT_COUNT;
        sql = SqlMethodHelper.sqlSelectCount(dbTableInfo);
        sqlSource = languageDriver.createSqlSource(configuration, sql, entityClass);
        this.addMappedStatement(mapperClass, sqlMethodEnum.getId(), sqlSource, SqlCommandType.SELECT, entityClass, null,
                Integer.class, new NoKeyGenerator(), dbTableInfo.getKeyProperty(), dbTableInfo.getKeyColumn());
    }


    /**
     * 添加至 mybatis mapper
     *
     * @param mapperClass
     * @param id
     * @param sqlSource
     * @param sqlCommandType
     * @param parameterClass
     * @param resultMap
     * @param resultType
     * @param keyGenerator
     * @param keyProperty
     * @param keyColumn
     * @return
     */
    public MappedStatement addMappedStatement(Class<?> mapperClass, String id, SqlSource sqlSource,
                                              SqlCommandType sqlCommandType,
                                              Class<?> parameterClass,
                                              String resultMap,
                                              Class<?> resultType,
                                              KeyGenerator keyGenerator,
                                              String keyProperty,
                                              String keyColumn) {
        String statementName = mapperClass.getName() + "." + id;
        if (configuration.hasStatement(statementName)) {
            logger.warn("{} Has been loaded by XML or SqlProvider, ignoring the injection of the SQL.", statementName);
            return null;
        }

        /* 缓存逻辑处理 */
        boolean isSelect = false;
        if (sqlCommandType == SqlCommandType.SELECT) {
            isSelect = true;
        }
        return builderAssistant.addMappedStatement(id, sqlSource, StatementType.PREPARED, sqlCommandType, null, null, null,
                parameterClass, resultMap, resultType, null, !isSelect, isSelect, false, keyGenerator, keyProperty, keyColumn,
                configuration.getDatabaseId(), languageDriver, null);
    }


}
