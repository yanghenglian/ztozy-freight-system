package com.zto56.freight.components.mq.native1.vender;


import com.zto56.freight.components.mq.native1.model.AbstractMQ;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * MQ 消息发送器 接口定义
 *
 * @author zhangqingfu
 * @date 2022-07-04
 */
public interface IMQSender {

    /**
     * 推送MQ消息， 同步
     */
    void send(AbstractMQ mqModel);

    /**
     * 推送MQ消息， 异步
     */
    void asyncSend(AbstractMQ mqModel, SendCallback callback);

    /**
     * 推送MQ消息， 延迟接收，单位：s
     */
    SendResult send(AbstractMQ mqModel, int delay);

}
