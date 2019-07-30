package com.cong.swag.test;

import com.cong.swag.common.VO.WeiboHotRankItemVO;
import com.cong.swag.dao.WeiboHotRankDao;
import com.cong.swag.service.task.impl.WeiboHotRankSyncJobHandler;
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
    WeiboHotRankSyncJobHandler weiboHotRankSyncJobHandler;

    @Autowired
    WeiboHotRankDao weiboHotRankDao;

    @Test
    public void test(){
        weiboHotRankSyncJobHandler.handleJob(null);
    }

    @Test
    public void testDao(){
        WeiboHotRankItemVO itemVO = weiboHotRankDao.getById(1);
        Assert.assertNotNull(itemVO);
    }

}
