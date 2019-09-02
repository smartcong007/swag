package com.cong.swag.service.kafka.serializer;

import com.alibaba.fastjson.JSON;
import com.cong.swag.common.dto.MqBodyDTO;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @Description kafka自定义json反序列化器
 * @Author zheng cong
 * @Date 2019-07-24
 */
public class JsonDeserializer implements Deserializer<MqBodyDTO> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public MqBodyDTO deserialize(String s, byte[] bytes) {
        return JSON.parseObject(bytes, MqBodyDTO.class);
    }

    @Override
    public void close() {

    }
}
