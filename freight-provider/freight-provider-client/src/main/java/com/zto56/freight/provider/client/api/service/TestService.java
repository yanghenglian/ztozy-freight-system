package com.zto56.freight.provider.client.api.service;

import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;

/**
 *
 * @author zhangqingfu
 * @date 2022-09-07
 */
public interface TestService {

    void test1(DogVO newDog);

    void test2(Order newOrder);

    void valid(Order vo);
}
