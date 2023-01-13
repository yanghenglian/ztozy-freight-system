package com.zto56.freight.provider.app.hello.consumer;

import com.alibaba.fastjson.JSON;
import com.zto56.freight.components.mq.native1.constant.MQVenderCS;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * RocketMQ消费案例
 * @author zhangqingfu
 * @date 2022-09-07
 */
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ROCKET_MQ)
@Component
@RocketMQMessageListener(topic = "demo", consumerGroup = "demo", messageModel = MessageModel.CLUSTERING)
@Slf4j
public class DemoConsumer implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        log.info("MessageExt:{}", JSON.toJSONString(message));
        String json = new String(message.getBody());
        log.info("onMessage messageId:{} message:{}", message.getMsgId(), json);
    }
}