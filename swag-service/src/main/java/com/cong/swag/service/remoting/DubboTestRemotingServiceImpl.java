package com.cong.swag.service.remoting;

import com.cong.swag.api.export.DubboTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhengcong on 2018/9/2.
 */
@Service("dubboTestRemotingService")
public class DubboTestRemotingServiceImpl implements DubboTestRemotingService {

    @Autowired
    DubboTestService dubboTestRemoteService;

    @Override
    public String printHellow() {
        return "test:"+dubboTestRemoteService.sayHellow();
    }
}
