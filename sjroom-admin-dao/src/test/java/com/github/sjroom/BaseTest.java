package com.github.sjroom;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-10 16:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-dao.xml"})
public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     *
     * @param result
     *            数据库操作返回影响条数
     * @return boolean
     */
    public boolean retBool(int result) {
        return result >= 1;
    }
}
