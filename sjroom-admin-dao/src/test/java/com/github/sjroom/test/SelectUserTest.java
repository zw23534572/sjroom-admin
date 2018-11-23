package com.github.sjroom.test;


import com.github.sjroom.BaseTest;
import com.github.sjroom.mybatis.entity.User;
import com.github.sjroom.mybatis.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
public class SelectUserTest extends BaseTest {

    @Autowired
    UserMapper userMapper;

    /**
     * 测试1，查询所有字段
     */
    @Test
    public void n001() {
        List<User> list = userMapper.selectList(new User());
        for (User user : list) {
            System.out.println("UpdateByIdUserTest user" + user);
        }
        Assert.assertTrue(true);
    }

    /**
     * 测试2，查询主键为 10000 的字段，
     * 正常为null
     */
    @Test
    public void n002() {
        User user = userMapper.selectById(10000l);
        boolean result = user == null;
        Assert.assertTrue(result);
    }

    /**
     * 测试2，查询主键为 4 的字段，
     * 正常为返回该User
     */
    @Test
    public void n003() {
        User user = userMapper.selectById(1l);
        boolean result = user != null;
        Assert.assertTrue(result);
    }

    /**
     * 测试4，批量查询主键为 4，5 的字段，
     * 正常为返回该User
     */
    @Test
    public void n004() {
        List<Long> list = new ArrayList<Long>();
        list.add(1l);
        list.add(2l);
        List<User> userList = userMapper.selectBatchIds(list);
        boolean result = userList != null && userList.size() == 2;
        Assert.assertTrue(result);
    }

    /**
     * 测试5，通过实体类查询，
     * 正常为返回User 个数
     */
    @Test
    public void n005() {
        int result = userMapper.selectCount(new User());
        Assert.assertTrue(result >= 0);
    }


    /**
     * 测试6，通过实体类查询，
     * 正常为返回User
     */
    @Test
    public void n006() {
        User user = new User();
        user.setId(1l);
        user = userMapper.selectOne(user);
        Assert.assertTrue(user != null && user.getId().intValue() == 1l);
    }


    /**
     * 测试8，字段extName 从数据库 ext_name读取，
     * 是否征程
     */
    @Test
    public void n007() {
        User user = new User();
        user.setId(1l);
        user = userMapper.selectOne(user);
//        Assert.assertTrue(user != null && user.getExtName().equals("用户姓名_扩展"));
    }


    /**
     * 测试8，pageHelpler 查询
     */
    @Test
    public void n008() {

        List<User> userList = userMapper.selectList(new User());
        for (User user : userList) {
            System.out.println("userList result:" + user);
        }


        Page<User> page = PageHelper.startPage(1, 2);
        userMapper.selectList(new User());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (User user : page.getResult()) {
            System.out.println("page result:" + user);
        }


        page = PageHelper.startPage(2, 2);
        userMapper.selectList(new User());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (User user : page.getResult()) {
            System.out.println("page result:" + user);
        }


        page = PageHelper.startPage(3, 2);
        userMapper.selectList(new User());
        System.out.println("page pageNum:{}" + page.getPageNum());
        System.out.println("page pageSize:{}" + page.getPageSize());
        System.out.println("page pages:{}" + page.getPages());
        System.out.println("page total:{}" + page.getTotal());
        for (User user : page.getResult()) {
            System.out.println("page result:" + user);
        }

    }

    /**
     * 测试9，pageHelpler 查询
     */
    @Test
    public void n009() {
        User user = userMapper.selectByPrimaryKey(1L);
        Assert.assertTrue(user != null);
    }
}
