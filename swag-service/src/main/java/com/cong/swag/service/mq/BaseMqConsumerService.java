package com.cong.swag.service.mq;

import com.cong.swag.common.dto.MqBodyDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-24
 */
public class BaseMqConsumerService implements MessageListener<String, MqBodyDTO> {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMqConsumerService.class);

    @Override
    public void onMessage(ConsumerRecord<String, MqBodyDTO> stringStringConsumerRecord) {
        LOGGER.info("开始消费消息：{}", stringStringConsumerRecord);
    }
}
