package com.cong.swag.test;

import com.cong.swag.common.VO.UserVO;
import com.cong.swag.service.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by zhengcong on 2018/5/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:applicationContext.xml"})
public class BaseTest {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    UserService userService;

    @Test
    public void test(){
        UserVO userVO = userService.getUser(5437);
        Assert.assertNotNull(userVO);
    }

    @Test
    public void testLog(){
        LOGGER.info("开始测试日志功能");
        LOGGER.debug("开始测试日志功能");
        LOGGER.error("开始测试日志功能");
    }

}
