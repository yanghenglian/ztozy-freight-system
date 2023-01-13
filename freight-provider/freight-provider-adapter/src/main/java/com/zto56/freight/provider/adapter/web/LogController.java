package com.zto56.freight.provider.adapter.web;

import cn.monitor4all.logRecord.annotation.OperationLog;
import com.alibaba.fastjson.JSON;
import com.github.jsonzou.jmockdata.JMockData;
import com.zto56.freight.common.core.constant.OperationType;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.api.service.TestService;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
@Api(tags = "日志测试")
@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {

    @Autowired
    private TestService testService;

    @OperationLog(bizId = "#vo.id",
            bizType = OperationType.TEST,
            msg = "T(cn.hutool.core.date.DateUtil).date()+ ' 狗狗:' + #vo.name",
            tag = "'标签'",
            operatorId = "'张三'",
            recordReturnValue = true,
            condition = "#vo.id!=1")
    @ApiOperation(value = "日志测试1", notes = "日志测试1", httpMethod = "POST")
    @PostMapping("/test1")
    public BaseResponse<DogVO> test1(@RequestBody DogVO vo) {
        // Assert.isTrue(1 == 2, "我报错啦");
        log.info("vo:{}", JSON.toJSONString(vo));
        return BaseResponse.ok(vo);
    }

    @ApiOperation(value = "日志测试2,diff 简单对象", notes = "日志测试2,diff 简单对象", httpMethod = "POST")
    @PostMapping("/test2")
    public BaseResponse<DogVO> test2() {
        // 新数据
        DogVO newDog = JMockData.mock(DogVO.class);

        testService.test1(newDog);
        return BaseResponse.ok(newDog);
    }

    @ApiOperation(value = "日志测试3,diff 复杂对象", notes = "日志测试3,diff 复杂对象", httpMethod = "POST")
    @PostMapping("/test3")
    public BaseResponse<Order> test3() {
        Order order = JMockData.mock(Order.class);

        testService.test2(order);
        return BaseResponse.ok(order);
    }


}
