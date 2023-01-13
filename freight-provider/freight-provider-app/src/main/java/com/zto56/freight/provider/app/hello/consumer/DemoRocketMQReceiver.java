package com.zto56.freight.provider.app.hello.consumer;

import com.zto56.freight.components.mq.native1.constant.MQVenderCS;
import com.zto56.freight.components.mq.native1.model.DemoMQ;
import com.zto56.freight.components.mq.native1.vender.IMQMsgReceiver;
import com.zto56.freight.provider.infra.thread.ThreadExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * RocketMQ消息接收入口
 *
 * @author zhangqingfu
 * @date 2022-07-05
 */
@Slf4j
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ROCKET_MQ)
@ConditionalOnBean(DemoMQ.IMQReceiver.class)
@RocketMQMessageListener(topic = DemoMQ.MQ_NAME, consumerGroup = DemoMQ.MQ_NAME, messageModel = MessageModel.CLUSTERING)
public class DemoRocketMQReceiver implements IMQMsgReceiver, RocketMQListener<MessageExt> {

    @Autowired
    private DemoMQ.IMQReceiver mqReceiver;

    /** 接收 【 queue 】 类型的消息 **/
    @Override
    public void receiveMsg(String msg) {
        mqReceiver.receive(DemoMQ.parse(msg));
    }

    @Override
    @Async(ThreadExecutor.demoExecutor)
    public void onMessage(MessageExt message) {
        log.info("messageId:{}", message.getMsgId());
        this.receiveMsg(new String(message.getBody()));
    }

}
