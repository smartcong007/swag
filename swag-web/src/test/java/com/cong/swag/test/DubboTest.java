package com.cong.swag.test;

import com.cong.swag.api.export.DubboTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath*:applicationContext.xml"})
public class DubboTest {

    @Autowired
    DubboTestService dubboTestService;

    @Test
    public void test() throws InterruptedException {
        System.out.println("begainj..");
        System.out.println(dubboTestService.sayHellow());
        Thread.currentThread().join();
    }

}
