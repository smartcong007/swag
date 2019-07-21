package com.cong.swag.service.task.factory;

import com.cong.swag.common.task.JobModel;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
@Service
public class TaskProducerImpl implements TaskProducer {


    @Autowired
    Scheduler scheduler;

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskProducerImpl.class);

    @Override
    public void addCronJob(String jobName, String jobGroup, JobModel jobModel, String cron) throws SchedulerException {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup) || Objects.isNull(jobModel) || StringUtils
            .isEmpty(cron)) {
            throw new IllegalArgumentException();
        }
        if (scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroup)) != null) {
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        }
        JobDetail jobDetail = JobBuilder.newJob(JobFactory.class).withIdentity(jobName, jobGroup).build();
        jobDetail.getJobDataMap().put("jobModel", jobModel);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(
            CronScheduleBuilder.cronSchedule(cron)).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    @Override
    public void deleteCronJob(String jobName, String jobGroup) throws SchedulerException {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup)) {
            throw new IllegalArgumentException();
        }
        if (scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroup)) == null) {
            return;
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
    }

    @Override
    public void updateCronJob(String jobName, String jobGroup, JobModel jobModel, String cron) throws SchedulerException {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup) || Objects.isNull(jobModel) || StringUtils
            .isEmpty(cron)) {
            throw new IllegalArgumentException();
        }
        if (scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroup)) != null) {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        }else {
            throw new IllegalStateException("不存在的任务");
        }
        addCronJob(jobName, jobGroup, jobModel, cron);
    }
}
