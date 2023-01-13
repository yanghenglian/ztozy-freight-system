package com.zto56.freight.consumer.client.api.dubbo;

import com.zto56.freight.common.core.response.BaseResponse;

/**
 * dubbo服务测试
 * @author zhangqingfu
 * @date 2022-08-30
 */
public interface ByeService {
    String BEAN_ID = "ByeService";

    BaseResponse<String> bye(String name);

}
