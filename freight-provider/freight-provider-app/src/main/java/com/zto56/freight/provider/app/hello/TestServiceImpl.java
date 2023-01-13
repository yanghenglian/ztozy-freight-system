package com.zto56.freight.provider.app.hello;

import cn.monitor4all.logRecord.annotation.OperationLog;
import cn.monitor4all.logRecord.context.LogRecordContext;
import com.alibaba.fastjson.JSON;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.jsonzou.jmockdata.JMockData;
import com.zto56.freight.common.core.constant.OperationType;
import com.zto56.freight.common.expand.valid.ValidateService;
import com.zto56.freight.provider.app.hello.executor.ReadExe;
import com.zto56.freight.provider.app.hello.executor.WriteExe;
import com.zto56.freight.provider.client.api.service.TestService;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhangqingfu
 * @date 2022-09-07
 */
@Component
@Slf4j
public class TestServiceImpl implements TestService {
    @Autowired
    private ValidateService validateService;

    @Autowired
    private ReadExe readExe;


    @Autowired
    private WriteExe writeExe;


    @OperationLog(bizId = "#newDog.id",
            bizType = OperationType.TEST,
            msg = "#_DIFF(#oldObject, #newDog)",
            tag = "'标签'",
            operatorId = "'张三'",
            recordReturnValue = true,
            condition = "#newDog.id!=1")
    @Override
    public void test1(DogVO newDog) {
        validateService.validate(newDog);
        // 老数据
        DogVO oldObject = JMockData.mock(DogVO.class);

        LogRecordContext.putVariables("oldObject", oldObject);
    }

    @OperationLog(bizId = "#newOrder.orderId",
            bizType = OperationType.TEST,
            msg = "#_DIFF(#oldObject, #newOrder)",
            tag = "'标签'",
            operatorId = "'张三'",
            recordReturnValue = true)
    @Override
    public void test2(Order newOrder) {
        Order order1 = JMockData.mock(Order.class);

        LogRecordContext.putVariables("oldObject", order1);
    }

    @Override
    @Validated(failFast = false)
    public void valid(Order vo) {
        log.info("vo:{}", JSON.toJSONString(vo));
    }
}
