package com.zto56.freight.common.expand.config;

import com.zto56.freight.common.core.response.Response;
import com.zto56.freight.common.core.util.TraceUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * http返回体统一添加traceId字段
 * @author zhangqingfu
 * @date 2022-08-11
 */
@ControllerAdvice
public class ResponseModifyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // put traceId to response
        if (o instanceof Response) {
            ((Response) o).setTraceId(TraceUtil.getTraceId());
            ((Response) o).setTimestamp(System.currentTimeMillis());
        }
        return o;
    }
}
