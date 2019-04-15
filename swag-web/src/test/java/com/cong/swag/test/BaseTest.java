package com.cong.swag.test;

import com.cong.swag.common.VO.AuditInfoVO;
import com.cong.swag.common.VO.AuditSnapshotVO;
import com.cong.swag.common.VO.AuditTypeVO;
import com.cong.swag.common.VO.UserVO;
import com.cong.swag.common.util.AuditTypeHolder;
import com.cong.swag.dao.AuditSnapshotDao;
import com.cong.swag.dao.AuditTypeDao;
import com.cong.swag.service.user.UserService;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    AuditTypeDao auditTypeDao;

    @Autowired
    AuditSnapshotDao auditSnapshotDao;

    @Test
    public void test() {
        UserVO userVO = userService.getUser(5);
        Assert.assertNotNull(userVO);
        Assert.assertEquals(java.util.Optional.of(23),userVO.getAge());
    }

    @Test
    public void test3(){
        AuditSnapshotVO snapshotVO = new AuditSnapshotVO();
        snapshotVO.setId(UUID.randomUUID().toString());
        snapshotVO.setAuditType("DEFEAT");
        snapshotVO.setOperateDate(new Date());
        int res = auditSnapshotDao.insertSnapshot(snapshotVO);
        Assert.assertEquals(1, res);
    }

    @Test
    public void test2(){
        AuditTypeVO typeVO = AuditTypeHolder.getByCode("DEFEAT");
        Assert.assertNotNull(typeVO);
    }

    @Test
    public void test4(){
        List<AuditInfoVO> auditInfoVOS = auditSnapshotDao.getSelfFishedAuditInfoList("o6qQ3ULaW7", Lists.newArrayList("SALE"));
        Assert.assertNotNull(auditInfoVOS);
    }

}
