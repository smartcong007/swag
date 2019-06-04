package com.cong.swag.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cong.swag.common.exception.CommonException;
import com.cong.swag.common.util.Result;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description 统一异常处理
 * @Author zheng cong
 * @Date 2019-01-03
 */
@Order(-1)
public class CommonExceptionHandler implements HandlerExceptionResolver {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        Object o, Exception e) {
        LOGGER.error("exception info during web request:{}", e);
        ModelAndView result = new ModelAndView();
        Result r;
        if (e instanceof CommonException) {
            CommonException ex = (CommonException) e;
            r = Result.fail(String.valueOf(ex.getCode()), ex.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            r = parseValidationErrors(bindingResult);
        } else if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            r = parseValidationErrors(bindingResult);
        } else {
            Map<String, Object> data = Maps.newHashMap();
            Object stackTrace;
            if (null != e.getStackTrace() && e.getStackTrace().length > 0) {
                stackTrace = e.getStackTrace()[0];
            } else {
                stackTrace = e.getStackTrace();
            }
            data.put("exStackTrace", stackTrace);
            data.put("exMessage", e.getMessage());
            data.put("exClass", e.getClass().getName());
            r = new Result().setCode("-1").setMsg("系统内部错误").setData(data).setSuccess(false);
        }
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
        } catch (IOException e1) {
            LOGGER.error("");
        }
        out.print(JSON.toJSONString(r));
        return result;
    }

    private Result parseValidationErrors(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuffer errmsgBF = new StringBuffer();
        for (ObjectError error : errors) {
            JSONObject errObj = JSONObject.parseObject(JSON.toJSONString(error));
            String field = errObj.getString("field");
            String massage = errObj.getString("defaultMessage");
            errmsgBF.append(field+massage);
            errmsgBF.append("||");
        }
        String errmsgString = errmsgBF.toString();
        errmsgString = errmsgString.length() > 2 ? errmsgString.substring(0, errmsgString.length() - 2) : errmsgString;
        LOGGER.error("Validation failed! {} ", errmsgString);
        return Result.fail("400", errmsgString);
    }
}
