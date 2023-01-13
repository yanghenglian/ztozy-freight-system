package com.zto56.freight.common.core.exception;


import com.zto56.freight.common.core.enums.ApiCodeEnum;

/**
 * 自定义业务异常
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final Integer DEFAULT_ERR_CODE = ApiCodeEnum.CUSTOM_FAIL.getCode();

    public BizException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public BizException(Integer errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public BizException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public BizException(Integer errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }
}