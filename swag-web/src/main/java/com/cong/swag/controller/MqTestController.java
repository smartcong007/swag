package com.cong.swag.controller;

import com.cong.swag.common.dto.MqBodyDTO;
import com.cong.swag.common.util.Result;
import com.cong.swag.service.mq.BaseMqProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-24
 */
@RestController
public class MqTestController {

    @Autowired
    BaseMqProducerService baseMqProducerService;

    @PostMapping(path = "/mq/push", consumes = "application/json", produces = "application/json")
    public Result send(@RequestBody MqBodyDTO mqBodyDTO) {
        baseMqProducerService.send(mqBodyDTO);
        return Result.success("发送成功");
    }

}
