package com.cong.swag.test;

import com.cong.swag.common.VO.UserVO;
import com.cong.swag.service.user.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-03-31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void test() {
        UserVO userVO = userService.getUser(5);
        Assert.assertNotNull(userVO);
        Assert.assertEquals(java.util.Optional.of(23),userVO.getAge());
    }


}
