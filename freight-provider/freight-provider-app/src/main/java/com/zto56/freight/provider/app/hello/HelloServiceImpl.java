package com.zto56.freight.provider.app.hello;

import cn.hutool.core.lang.Assert;
import cn.monitor4all.logRecord.annotation.OperationLog;
import com.alibaba.fastjson.JSON;
import com.github.fashionbrot.validated.annotation.NotBlank;
import com.github.fashionbrot.validated.annotation.Validated;
import com.zto56.freight.common.core.exception.BizException;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.common.expand.valid.ValidateService;
import com.zto56.freight.provider.app.hello.convertor.DogConvertor;
import com.zto56.freight.provider.client.api.dubbo.HelloService;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;
import com.zto56.freight.provider.domain.entity.DogDO;
import com.zto56.pay.client.api.TestServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-30
 */
@Slf4j
@RefreshScope
@DubboService
public class HelloServiceImpl implements HelloService {
    @Autowired
    private ValidateService validateService;

    @DubboReference(version = "1.0.0", group = "DEFAULT_GROUP")
    private TestServiceI testServiceI;

    @Autowired
    private DogConvertor dogConvertor;

    @Value("${test}")
    private String test;

    @OperationLog(bizType = "'test'", bizId = "#name", msg = "'hello执行啦'+#name")
    @Override
    @Validated
    public BaseResponse<String> hello(@NotBlank(msg = "入参name不能为空") String name) {

        DogDO dogDO = new DogDO();
        dogDO.setId(1L);
        dogDO.setName("汪汪");
        dogDO.setAge(3);

        DogVO dogVO = dogConvertor.DO2VO(dogDO);
        log.info("dogVO:{}", JSON.toJSONString(dogVO));


        String data = "hello " + name;
        log.info("hello:{}", data);
        // int a = 1 / 0;
        // if (1 == 1) {
        //     Assert.isTrue(1 == 2, "出错了");
        //     throw new BizException(222, "抛一个异常");
        // }
        return BaseResponse.ok("成功", data + " " + test);
    }

    @Override
    @Validated(failFast = false)
    public BaseResponse<Order> helloOrder(Order order) {
        return BaseResponse.ok(order);
    }

    @Override
    public BaseResponse<DogVO> helloDog(DogVO dogVO) {
        validateService.validate(dogVO);
        return BaseResponse.ok(dogVO);
    }

    @Override
    @Validated
    public String hello2(@NotBlank(msg = "hello2入参name不能为空") String name) {
        if (1 == 1) {
            Assert.isTrue(1 == 2, "出错了");
            throw new BizException(222, "抛一个异常");
        }
        return "hello2" + name;
    }


    @Override
    public BaseResponse<String> test(String name) {
        log.info("test:{}", name);
        String s = testServiceI.sayHello(name);
        return BaseResponse.ok("成功", s);
    }
}
