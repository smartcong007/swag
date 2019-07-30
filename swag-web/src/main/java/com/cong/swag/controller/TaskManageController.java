package com.cong.swag.controller;

import com.cong.swag.common.dto.TaskDTO;
import com.cong.swag.common.util.Result;
import com.cong.swag.service.task.factory.TaskProducer;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-22
 */
@RestController
public class TaskManageController {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManageController.class);

    @Autowired
    TaskProducer taskProducer;

    @PutMapping(path = "/task/add", consumes = "application/json", produces = "application/json")
    Result addTask(@RequestBody TaskDTO taskDTO) {
        try {
            taskProducer.addCronJob(taskDTO.getName(), taskDTO.getGroup(), taskDTO.getJobModel(), taskDTO.getCron());
        } catch (SchedulerException e) {
            LOGGER.error("添加任务异常：{}",e);
            return Result.fail("500", "添加任务异常");
        }
        return Result.success("任务添加成功");
    }

    @DeleteMapping(path = "/task/{group}/{name}", produces = "application/json")
    public Result delete(@PathVariable String group, @PathVariable String name) {
        try {
            taskProducer.deleteCronJob(name, group);
        } catch (SchedulerException e) {
            LOGGER.error("删除任务异常：{}", e);
            return Result.fail("500", "删除任务异常");
        }
        return Result.success("任务删除成功");
    }

    @PatchMapping(path = "/task/update", consumes = "application/json", produces = "application/json")
    public Result update(@RequestBody TaskDTO taskDTO) {
        try {
            taskProducer.updateCronJob(taskDTO.getName(), taskDTO.getGroup(), taskDTO.getJobModel(), taskDTO.getCron());
        } catch (SchedulerException e) {
            LOGGER.error("更新任务失败：{}", e);
            return Result.fail("500","更新任务异常");
        }
        return Result.success("任务更新成功");
    }

}
