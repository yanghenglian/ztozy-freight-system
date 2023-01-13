package com.zto56.freight.provider.app.hello.consumer;

import com.alibaba.fastjson.JSON;
import com.zto56.freight.components.mq.native1.model.DemoMQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 接收MQ消息处理业务逻辑
 *
 * @author zhangqingfu
 * @date 2022-07-05
 */
@Slf4j
@Component
public class DemoMQReceiver implements DemoMQ.IMQReceiver {

    @Override
    public void receive(DemoMQ.MsgPayload payload) {
        try {
            log.info("接收到MQ消息,msg:{}", JSON.toJSONString(payload));
        } catch (Exception e) {
            log.error("MQ消费出错 msg:{} e:{}", e.getMessage(), Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
}
