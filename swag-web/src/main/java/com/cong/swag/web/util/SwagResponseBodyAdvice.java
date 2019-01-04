package com.cong.swag.web.util;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cong.swag.common.util.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-04
 */
@ControllerAdvice(basePackages = {"com.cong.swag.controller"})
public class SwagResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(aClass);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
        ServerHttpResponse serverHttpResponse) {
        if (o instanceof Result) {
            return o;
        }else {
            return new Result<>().setCode("200").setMsg("success").setSuccess(true).setData(o);
        }
    }
}
