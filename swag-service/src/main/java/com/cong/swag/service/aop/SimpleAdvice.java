package com.cong.swag.service.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-05-23
 */
public class SimpleAdvice {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAdvice.class);

    public void simpleBeforeAdvice() {
        LOGGER.info("代理生效啦!");
    }

}
