package com.zto56.freight.common.core.exception;


/**
 * 基础异常
 *
 * @author zhangqingfu
 * @date 2022-09-05
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer errCode;

    public BaseException(String errMessage) {
        super(errMessage);
    }

    public BaseException(Integer errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public BaseException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    public BaseException(Integer errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

}
