package com.zto56.freight.common.expand.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.zto56.freight.common.core.enums.ApiCodeEnum;
import com.zto56.freight.common.core.exception.BizException;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.common.core.response.PageResponse;
import com.zto56.freight.common.core.util.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * dubbo自定义过滤器
 * 1.处理Dubbo的traceId
 * 2.拦截所有的异常,返回统一返回体
 *
 * @author zhangqingfu
 * @date 2022-08-11
 */
@Slf4j
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class DubboCustomFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 获取dubbo上下文中的traceId
        String traceId = RpcContext.getContext().getAttachment(TraceUtil.TRACE_ID);
        if (StrUtil.isBlank(traceId)) {
            // customer 设置traceId到dubbo的上下文
            RpcContext.getContext().setAttachment(TraceUtil.TRACE_ID, TraceUtil.getTraceId());
        } else {
            // provider 设置traceId到日志的上下文
            TraceUtil.putTraceId(traceId);
        }
        Result result = null;
        String trace = TraceUtil.getTraceId();
        Integer code = ApiCodeEnum.FAILED.getCode();
        long timestamp = System.currentTimeMillis();
        try {
            result = invoker.invoke(invocation);
            Method method = invoker.getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            Class<?> returnType = method.getReturnType();
            if (result.hasException()) {
                Throwable throwable = result.getException();
                log.error("dubbo全局异常 msg:{} e:{}", throwable.getMessage(), Arrays.toString(throwable.getStackTrace()));
                String msg = throwable.getMessage();

                if (throwable instanceof BizException) {
                    // 自定义业务异常
                    code = ((BizException) throwable).getErrCode();
                } else if (throwable instanceof IllegalArgumentException) {
                    // 参数异常
                    code = ApiCodeEnum.PARAMETER_ERROR.getCode();
                } else if (throwable instanceof ValidatedException) {
                    // 参数异常
                    List<MarsViolation> violations = ((ValidatedException) throwable).getViolations();
                    code = ApiCodeEnum.PARAMETER_ERROR.getCode();
                    msg = ((ValidatedException) throwable).getMsg();
                    if (ObjectUtil.isNotEmpty(violations)) {
                        if (violations.size() == 1) {
                            msg = violations.get(0).getMsg();
                        } else {
                            msg = violations.stream().map(MarsViolation::getMsg).collect(Collectors.joining(","));
                        }
                    }
                    log.error("dubbo全局异常,参数校验失败 msg:{} e:{}", msg, Arrays.toString(throwable.getStackTrace()));
                }

                if (StrUtil.equals(BaseResponse.class.getName(), returnType.getName())) {
                    BaseResponse<T> failed = BaseResponse.failed(code, msg);
                    failed.setTraceId(trace);
                    failed.setTimestamp(timestamp);
                    return AsyncRpcResult.newDefaultAsyncResult(failed, invocation);
                } else if (StrUtil.equals(PageResponse.class.getName(), returnType.getName())) {
                    PageResponse<T> failed = PageResponse.failed(code, msg);
                    failed.setTraceId(trace);
                    failed.setTimestamp(timestamp);
                    return AsyncRpcResult.newDefaultAsyncResult(failed, invocation);
                }
            }
        } catch (Exception e) {
            log.error("dubbo统一捕捉异常失败 msg:{} e:{}", e.getMessage(), Arrays.toString(e.getStackTrace()));
            BaseResponse<T> failed = BaseResponse.failed(code, e.getMessage());
            failed.setTraceId(trace);
            failed.setTimestamp(timestamp);
            return AsyncRpcResult.newDefaultAsyncResult(failed, invocation);
        }
        return result;
    }
}


