package com.cong.swag.service.task.factory;

import com.cong.swag.common.task.JobModel;
import com.cong.swag.service.task.impl.JobHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
@Component
@PersistJobDataAfterExecution
public class JobFactory implements ApplicationContextAware, Job {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JobFactory.class);

    private static ApplicationContext applicationContext;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobModel jobModel = (JobModel) jobExecutionContext.getMergedJobDataMap().get("jobModel");
        JobHandler jobHandler = (JobHandler) applicationContext.getBean(jobModel.getJobHandler());
        jobHandler.handleJob(jobModel);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOGGER.info("开始设置applicationContext");
        JobFactory.applicationContext = applicationContext;
    }
}
