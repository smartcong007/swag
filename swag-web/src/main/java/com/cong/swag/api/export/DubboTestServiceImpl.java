package com.cong.swag.api.export;

import org.springframework.stereotype.Service;

/**
 * Created by zhengcong on 2018/7/1.
 */
@Service("dubboTestService")
public class DubboTestServiceImpl implements DubboTestService {

    @Override
    public String sayHellow() {
        return "hellow! dubbo";
    }
}
