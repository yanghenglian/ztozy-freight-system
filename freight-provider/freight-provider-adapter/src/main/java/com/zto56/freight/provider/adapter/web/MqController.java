package com.zto56.freight.provider.adapter.web;

import cn.hutool.core.util.IdUtil;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.components.mq.native1.model.DemoMQ;
import com.zto56.freight.components.mq.native1.vender.IMQSender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
@Api(tags = "MQ测试")
@RestController
@RequestMapping("/mq")
@Slf4j
public class MqController {
    // @Autowired
    // private MqSource mqSource;

    @Autowired
    private IMQSender imqSender;

    // @Autowired
    // private DogStreamSender dogStreamSender;

    @ApiOperation(value = "rocketMQTemplate发送消息", notes = "rocketMQTemplate发送消息", httpMethod = "GET")
    @GetMapping("/send")
    public BaseResponse send() {
        DemoMQ.MsgPayload msgPayload = new DemoMQ.MsgPayload();
        msgPayload.setId(1L);
        msgPayload.setName(IdUtil.fastSimpleUUID());
        imqSender.send(DemoMQ.build(msgPayload));
        log.info("rocketMQTemplate发送消息成功");
        return BaseResponse.ok();
    }





}
