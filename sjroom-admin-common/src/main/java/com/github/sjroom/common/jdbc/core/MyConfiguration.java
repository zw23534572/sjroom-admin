package com.github.sjroom.common.jdbc.core;

import com.github.sjroom.common.jdbc.core.BaseMapper;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * <p>
 * replace default Configuration class
 * </p>
 *
 * @author Zhouwei
 */
public class MyConfiguration extends Configuration {

    private static Logger logger = LoggerFactory.getLogger(com.github.sjroom.common.jdbc.core.MyConfiguration.class);

    public MyConfiguration() {
        super.setMapUnderscoreToCamelCase(true);
    }

    /**
     * Mybatis加载sql的顺序：<br>
     * 1、加载xml中的sql；<br>
     * 2、加载sqlprovider中的sql<br>
     * 3、xmlsql 与 sqlprovider不能包含相同的sql <br>
     * <br>
     * 调整后的sql优先级：xmlsql > sqlprovider > crudsql
     */
    @Override
    public void addMappedStatement(MappedStatement ms) {
        if (this.mappedStatements.containsKey(ms.getId())) {// 说明已加载了xml中的节点；忽略mapper中的SqlProvider数据
            logger.warn("mapper[{}] is ignored, because it's exists, maybe from xml file", ms.getId());
            return;
        }
        super.addMappedStatement(ms);
    }

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return super.getMapper(type, sqlSession);
    }

    /**
     * 重新 addMapper 方法
     */
    @Override
    public <T> void addMapper(Class<T> mapperClass) {
        super.addMapper(mapperClass);

        if (!BaseMapper.class.isAssignableFrom(mapperClass)) {
            logger.warn("{} class skip addMapper", mapperClass);
            return;
        }

        /* 自动注入 SQL */
        new SqlMethodInjector(this).inject(mapperClass);
    }

    @Override
    public void addMappers(String packageName) {
        this.addMappers(packageName, Object.class);
    }

    @Override
    public void addMappers(String packageName, Class<?> superType) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        for (Class<?> mapperClass : mapperSet) {
            logger.debug("addMappers:{}", mapperClass);
            this.addMapper(mapperClass);
        }
    }
}
