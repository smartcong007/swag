package com.cong.swag.test;

import com.cong.swag.common.VO.GoodsVO;
import com.cong.swag.common.cache.RedisLock;
import com.cong.swag.common.cache.RedisRepository;
import com.cong.swag.dao.GoodsDao;
import com.cong.swag.service.user.UserService;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    RedisLock redisLock;

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

    @Test
    public void testRedis() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        int i = 0;
        while (i++<5) {
            executorService.submit(() -> {
                while (!redisLock.lock("lock", UUID.randomUUID().toString(), 5)) {
                    System.out.println(Thread.currentThread().getId()+"并没有获取到锁呀");
                }
                System.out.println(Thread.currentThread().getId()+"成功获取到锁");
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("所有线程都已获取到锁");

    }

}
