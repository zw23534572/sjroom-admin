package com.github.sjroom.test;


import com.github.sjroom.BaseTest;
import com.github.sjroom.mybatis.entity.User;
import com.github.sjroom.mybatis.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>说明：删除测试</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
public class DeleteByIdUserTest extends BaseTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 测试1，更新单个字段测试
     */
    @Test
    public void n001() {
        List<Long> list = new ArrayList<Long>();
        list.add(100l);
        list.add(200l);

        int result = userMapper.deleteBatchByIds(list);
        Assert.assertTrue(retBool(result));

        //查询出来，应该为null
        List<User> result1 = userMapper.selectBatchIds(list);
        Assert.assertTrue(result1 == null || result1.size() == 0);
    }

    /**
     * 测试2，删除数据库的 主键为1 的字段
     */
    @Test
    public void n002() {
        /** 主键为1 的数据已经删除 */
        int result = userMapper.deleteById(100l);
        Assert.assertFalse(retBool(result));
    }

    /**
     * 测试3，删除数据库，年龄为18的数据
     */
    @Test
    public void n003() {
        User user = new User();
        user.setName("删除的数据");
        user.setAge(18);
        int result = userMapper.insert(user);
        Assert.assertTrue(retBool(result));

        System.out.print("DeleteByIdUserTest n003 user:" + user);
        int result1 = userMapper.deleteById(user.getId());
        Assert.assertTrue(retBool(result1));

        User userResult = userMapper.selectById(user.getId());
        Assert.assertTrue(StringUtils.isEmpty(userResult));
    }
}
