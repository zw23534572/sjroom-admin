package com.github.sjroom.test;

import com.github.sjroom.BaseTest;
import com.github.sjroom.mybatis.entity.User;
import com.github.sjroom.mybatis.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
public class InsertUserTest extends BaseTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 测试1，未插入主键测试
     */
    @Test
    public void n001() {
        User user = new User();
        user.setName("zhangwu");
        user.setAge(5);
        int result = userMapper.insert(user);
        Assert.assertTrue(retBool(result));
    }


    /**
     * 测试2，插入主键测试
     */
    @Test
    public void n002() {
        User user = new User();
        user.setId(5l);
        user.setName("插入主键测试");
        user.setAge(18);
        int result = userMapper.insert(user);
        Assert.assertTrue(retBool(result));
    }

    /**
     * 测试4，插入存在的主键测试
     */
    @Test
    public void n004() {
        User user = new User();
        user.setId(4l);
        user.setName("插入主键测试");
        user.setAge(18);
        try {
            int result = userMapper.insert(user);
            Assert.assertTrue(retBool(result));
        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            if (errMsg.contains("Duplicate entry") || errMsg.contains("Unique index or primary key ")) {
                Assert.assertTrue(true);
                return;
            }
        }
    }

    /**
     * 测试5，插入存在的主键测试
     */
    @Test
    public void n005() {
        Long id = 6l;
        String extName = "扩展字段测试";
        User user = new User();
        user.setId(id);
        user.setAge(18);
        user.setExtName(extName);

        try {
            int result = userMapper.insert(user);
            Assert.assertTrue(retBool(result));

            User queryUser = new User();
            queryUser.setId(id);
            User userResult = userMapper.selectOne(queryUser);
            Assert.assertTrue(userResult != null && userResult.getExtName().equals(extName));

        } catch (Exception ex) {
            String errMsg = ex.getMessage();
            if (errMsg.contains("Duplicate entry") || errMsg.contains("Unique index or primary key ")) {
                Assert.assertTrue(true);
                return;
            }
        }
    }

}
