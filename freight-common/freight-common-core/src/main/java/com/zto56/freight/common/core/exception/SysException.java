package com.zto56.freight.common.core.exception;


import com.zto56.freight.common.core.enums.ApiCodeEnum;


/**
 * 系统异常
 *
 * @author zhangqingfu
 * @date 2022-09-05
 */
public class SysException extends BaseException {

    private static final long serialVersionUID = 1L;

    private static final Integer DEFAULT_ERR_CODE = ApiCodeEnum.SYSTEM_ERROR.getCode();

    public SysException(String errMessage) {
        super(DEFAULT_ERR_CODE, errMessage);
    }

    public SysException(Integer errCode, String errMessage) {
        super(errCode, errMessage);
    }

    public SysException(String errMessage, Throwable e) {
        super(DEFAULT_ERR_CODE, errMessage, e);
    }

    public SysException(Integer errorCode, String errMessage, Throwable e) {
        super(errorCode, errMessage, e);
    }

}
