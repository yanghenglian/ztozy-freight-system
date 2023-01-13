package com.zto56.freight.common.core.enums;


import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口返回码
 *
 * @author zhangqingfu
 * @date 2022-06-27
 */
@AllArgsConstructor
@Getter
public enum ApiCodeEnum {
    /**
     * 通用状态码可以参考{@link HttpStatus}
     */
    CUSTOM_FAIL(9999, "自定义业务异常"),
    FAILED(-1, "操作失败"),
    // 1xx：相关信息
    // 2xx：操作成功
    SUCCESS(200, "操作成功"),
    // 3xx：重定向
    // 4xx：客户端错误
    BAD_REQUEST(400, "错误请求"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "不支持的请求类型"),
    // 5xx：服务器错误
    SYSTEM_ERROR(500, "服务器错误"),


    PARAMETER_ERROR(1000, "参数错误"),
    OPERATION_FAIL_INSERT(1001, "新增失败"),
    OPERATION_FAIL_DELETE(1002, "删除失败"),
    OPERATION_FAIL_UPDATE(1003, "修改失败"),
    OPERATION_FAIL_SELETE(1004, "记录不存在");

    private Integer code;
    private String msg;
}
