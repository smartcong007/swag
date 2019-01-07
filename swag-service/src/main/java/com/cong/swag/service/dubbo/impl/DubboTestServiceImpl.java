package com.cong.swag.service.dubbo.impl;

import com.cong.swag.api.export.DubboTestService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-07
 */
@Service("dubboTestService")
public class DubboTestServiceImpl implements DubboTestService {

    @Override
    public String sayHellow() {
        return "hellow dubbo!";
    }
}
