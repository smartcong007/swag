package com.cong.swag.service.task.factory;

import com.cong.swag.common.task.JobModel;
import org.quartz.SchedulerException;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
public interface TaskProducer {

    /**
     * 添加基于cron的定时任务
     * @param jobName 任务名
     * @param jobGroup 任务分组
     * @param jobModel 任务实例
     * @param cron cron表达式
     */
    void addCronJob(String jobName, String jobGroup, JobModel jobModel, String cron) throws SchedulerException;

    /**
     * 删除基于cron的定时任务
     * @param jobName 任务名
     * @param jobGroup 任务分组
     */
    void deleteCronJob(String jobName, String jobGroup) throws SchedulerException;

    /**
     * 更新基于cron的定时任务
     * @param jobName 任务名
     * @param jobGroup 任务分组
     * @param jobModel 任务实例
     * @param cron cron表达式
     */
    void updateCronJob(String jobName, String jobGroup, JobModel jobModel, String cron) throws SchedulerException;

}
