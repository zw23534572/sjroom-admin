package com.github.sjroom.common.jdbc.core;

import java.io.Serializable;
import java.util.List;

/**
 * 基础mapper类
 *
 * @param <T>
 */
public interface BaseMapper<T> {
    /**********************************************  查询方法  *****************************************************/
    /**
     * 通过id查找
     *
     * @param id
     * @return
     */
    T selectById(Serializable id);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return List<T>
     */
    List<T> selectBatchIds(List<? extends Serializable> idList);

    /**
     * 通过实体类查找单个
     */
    T selectOne(T t);

    /**
     * 通过实体类查询多个
     *
     * @param t
     * @return
     */
    List<T> selectList(T t);

    /**
     * 通过实体类，返回查询个数
     *
     * @param t
     * @return
     */
    int selectCount(T t);

    /**********************************************  新增方法  *****************************************************/
    /**
     * 插入
     *
     * @param bean
     * @return true:成功 false:失败
     */
    int insert(T bean);

    /**********************************************  更新方法  *****************************************************/
    /**
     * 更新
     *
     * @param bean
     * @return true:成功 false:失败
     */
    int updateById(T bean);


    /**********************************************  删除方法  *****************************************************/
    /**
     * 根据主键删除
     *
     * @param id
     * @return true:成功 false:失败
     */
    int deleteById(Serializable id);

    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return int
     */
    int deleteBatchByIds(List<? extends Serializable> idList);

}
