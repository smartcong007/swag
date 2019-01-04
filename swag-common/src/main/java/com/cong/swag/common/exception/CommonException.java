package com.cong.swag.common.exception;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-03
 */
public class CommonException extends RuntimeException {

    private int code;

    public CommonException(int code, String errMessage) {
        super(errMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
