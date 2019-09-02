package com.cong.swag.service.kafka.serializer;

import com.alibaba.fastjson.JSON;
import com.cong.swag.common.dto.MqBodyDTO;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @Description kafka自定义json序列化器
 * @Author zheng cong
 * @Date 2019-07-24
 */
public class JsonSerializer implements Serializer<MqBodyDTO> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, MqBodyDTO mqBodyDTO) {
        return JSON.toJSONBytes(mqBodyDTO);
    }

    @Override
    public void close() {

    }
}
