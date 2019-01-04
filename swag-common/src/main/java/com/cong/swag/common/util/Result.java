package com.cong.swag.common.util;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-03
 */
public class Result<T> {

    private boolean success = false;

    private T data = null;

    private String msg = "";

    private String code = "500";

    public Result() {}

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode("200");
        r.setMsg("success");
        r.setSuccess(true);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> fail(String code, String msg) {
        Result<T> r = new Result<>();
        r.setSuccess(false);
        r.setMsg(msg);
        r.setCode(code);
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }
}
