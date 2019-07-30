package com.cong.swag.service.mq;

import com.cong.swag.common.dto.MqBodyDTO;
import com.cong.swag.common.exception.CommonException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-24
 */
@Service
public class BaseMqProducerService {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMqProducerService.class);

    @Autowired
    KafkaTemplate<String, MqBodyDTO> mqProducer;

    public void send(MqBodyDTO data) {
        ListenableFuture<SendResult<String, MqBodyDTO>> result = mqProducer.sendDefault(data);
        SendResult<String, MqBodyDTO> sendResult = null;
        try {
            sendResult = result.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (sendResult == null) {
            throw new CommonException(500, "消息发送失败");
        }
        LOGGER.info("发送成功：{}", sendResult.toString());
    }
}
