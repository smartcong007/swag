package com.cong.swag.test;

import com.cong.swag.common.util.ConfigUtils;
import com.cong.swag.service.user.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by zhengcong on 2018/5/28.
 */
@WebAppConfiguration
public class BaseTestWithoutContext {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTestWithoutContext.class);

    @Test
    public void testLogger(){
        LOGGER.info("开始测试日志功能");
        LOGGER.debug("开始测试日志功能");
        LOGGER.error("开始测试日志功能");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService",UserService.class);
    }

    @Test
    public void testConfig(){
        System.out.println(ConfigUtils.getValue("domain.default"));
    }


}
