package com.github.sjroom.mybatis.mapper;

import com.github.sjroom.common.jdbc.core.BaseMapper;
import com.github.sjroom.mybatis.entity.User;

/**
 * <B>说明：用户test表</B><BR>
 *
 * @author
 * @version 1.0.0.
 * @date 2018-02-27 14:52
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 自定义查询： 根据id,进行查询
     *
     * @param id
     * @return
     */
    User selectByPrimaryKey(Long id);

}
