package com.cong.swag.service.util;

import com.cong.swag.common.VO.AuditTypeVO;
import com.cong.swag.common.util.AuditTypeHolder;
import com.cong.swag.dao.AuditTypeDao;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-04-10
 */
@Service
public class ProjectInitilizationService implements ApplicationListener<ContextRefreshedEvent> {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectInitilizationService.class);

    @Autowired
    AuditTypeDao auditTypeDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            LOGGER.info("开始初始化AuditTypeHolder...");
            List<AuditTypeVO> typeVOS = auditTypeDao.getAuditTypeList();
            AuditTypeHolder.initAuditTypeVOS(typeVOS);
        }
    }
}
