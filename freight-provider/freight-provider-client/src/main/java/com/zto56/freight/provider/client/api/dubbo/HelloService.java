package com.zto56.freight.provider.client.api.dubbo;

import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;

/**
 * dubbo测试
 * @author zhangqingfu
 * @date 2022-08-30
 */
public interface HelloService {
    String BEAN_ID = "helloService";

    BaseResponse<String> hello(String name);

    BaseResponse<Order> helloOrder(Order order);

    BaseResponse<DogVO> helloDog(DogVO dogVO);

    String hello2(String name);

    BaseResponse<String> test(String address);
}
