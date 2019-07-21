package com.cong.swag.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.cong.swag.common.task.JobModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
@Service("basicJobHandler")
public class BasicJobHandler implements JobHandler {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicJobHandler.class);

    @Override
    public void handleJob(JobModel jobModel) {
        LOGGER.info("开始执行任务：{}", JSONObject.toJSONString(jobModel));
    }
}
