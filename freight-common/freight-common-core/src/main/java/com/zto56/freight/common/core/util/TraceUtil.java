package com.zto56.freight.common.core.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;

/**
 * Trace工具类
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
public class TraceUtil {
    public static final String TRACE_ID = "traceId";


    public static void putTraceId() {
        String traceId = getTraceId();
        putTraceId(StrUtil.isEmpty(traceId) ? IdUtil.simpleUUID() : traceId);
    }

    public static void putTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static void removeTraceId() {
        MDC.remove(TRACE_ID);
    }
}
