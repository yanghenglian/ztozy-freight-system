package com.zto56.freight.common.core.response;

import com.zto56.freight.common.core.enums.ApiCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础返回体
 * @author zhangqingfu
 * @date 2022-06-30
 */
@Data
public class BaseResponse<T> extends Response {
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private T data;


    /**
     * 成功
     */
    public static <T> BaseResponse<T> ok() {
        return build(true, ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), null);
    }

    /**
     * 成功
     *
     * @param message 提示信息
     */
    public static <T> BaseResponse<T> ok(String message) {
        return build(true, ApiCodeEnum.SUCCESS.getCode(), message, null);
    }

    /**
     * 成功
     *
     * @param data 数据
     */
    public static <T> BaseResponse<T> ok(T data) {
        return build(true, ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 成功
     *
     * @param message 提示信息
     * @param data 数据
     */
    public static <T> BaseResponse<T> ok(String message, T data) {
        return build(true, ApiCodeEnum.SUCCESS.getCode(), message, data);
    }


    /**
     * 失败
     */
    public static <T> BaseResponse<T> failed() {
        return build(false, ApiCodeEnum.FAILED.getCode(), ApiCodeEnum.FAILED.getMsg(), null);
    }

    /**
     * 失败
     *
     * @param errMessage 错误信息
     */
    public static <T> BaseResponse<T> failed(String errMessage) {
        return build(false, ApiCodeEnum.FAILED.getCode(), errMessage, null);
    }

    /**
     * 失败
     *
     * @param errCode 误码码
     * @param errMessage 错误信息
     */
    public static <T> BaseResponse<T> failed(Integer errCode, String errMessage) {
        return build(false, errCode, errMessage, null);
    }


    /**
     * 自定义失败
     *
     * @param customMsg 自定义失败消息
     */
    public static <T> BaseResponse<T> customFailed(String customMsg) {
        return build(false, ApiCodeEnum.CUSTOM_FAIL.getCode(), customMsg, null);
    }

    /**
     * 系统失败
     *
     * @param errMessage 错误信息
     */
    public static <T> BaseResponse<T> sysFailed(String errMessage) {
        return build(false, ApiCodeEnum.SYSTEM_ERROR.getCode(), errMessage, null);
    }

    /**
     * 构建
     *
     * @param success 成功
     * @param code 编码
     * @param msg 消息
     * @param data 数据
     */
    public static <T> BaseResponse<T> build(Boolean success, Integer code, String msg, T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setSuccess(success);
        baseResponse.setCode(code);
        baseResponse.setData(data);
        baseResponse.setMsg(msg);
        return baseResponse;
    }

}
