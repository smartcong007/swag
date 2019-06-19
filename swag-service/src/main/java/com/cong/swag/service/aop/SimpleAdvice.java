package com.cong.swag.service.aop;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
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

    private static KeyGenerator keyGenerator = new DefaultKeyGenerator();

    public void simpleBeforeAdvice() {
        LOGGER.info("代理生效啦!");
    }

    public Object insertIdGenerate(ProceedingJoinPoint joinPoint)
        throws Throwable {
        Object[] params = joinPoint.getArgs();
        Object insertObj = params[0];
        Class cl = insertObj.getClass();
        Method method = cl.getDeclaredMethod("setId", new Class[]{Long.class});
        method.invoke(insertObj, (Long)keyGenerator.generateKey());
        params[0] = insertObj;
        return joinPoint.proceed(params);
    }

}
