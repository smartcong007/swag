package com.cong.swag.test;

import com.cong.swag.common.VO.GoodsVO;
import com.cong.swag.dao.GoodsDao;
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

    @Autowired
    GoodsDao goodsDao;

    @Test
    public void goods() {
        GoodsVO goodsVO = goodsDao.getGoodsById((long) 1);
        Assert.assertNotNull(goodsVO);
    }

    @Test
    public void insertGoods(){
        GoodsVO vo = new GoodsVO();
        vo.setStorage(9);
        int res = goodsDao.addGoods(vo);
        Assert.assertEquals(1, res);
    }

}
