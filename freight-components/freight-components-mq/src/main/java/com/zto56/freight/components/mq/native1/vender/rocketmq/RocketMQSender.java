package com.zto56.freight.components.mq.native1.vender.rocketmq;

import com.zto56.freight.components.mq.native1.constant.MQVenderCS;
import com.zto56.freight.components.mq.native1.model.AbstractMQ;
import com.zto56.freight.components.mq.native1.vender.IMQSender;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/**
 * rocketMQ 消息发送器的实现
 *
 * @author zhangqingfu
 * @date 2022-07-04
 */
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ROCKET_MQ)
public class RocketMQSender implements IMQSender {

    private static final List<Integer> DELAY_TIME_LEVEL = new ArrayList<>();

    static {
        // 预设值的延迟时间间隔为：1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
        DELAY_TIME_LEVEL.add(1);
        DELAY_TIME_LEVEL.add(5);
        DELAY_TIME_LEVEL.add(10);
        DELAY_TIME_LEVEL.add(30);
        DELAY_TIME_LEVEL.add(60 * 1);
        DELAY_TIME_LEVEL.add(60 * 2);
        DELAY_TIME_LEVEL.add(60 * 3);
        DELAY_TIME_LEVEL.add(60 * 4);
        DELAY_TIME_LEVEL.add(60 * 5);
        DELAY_TIME_LEVEL.add(60 * 6);
        DELAY_TIME_LEVEL.add(60 * 7);
        DELAY_TIME_LEVEL.add(60 * 8);
        DELAY_TIME_LEVEL.add(60 * 9);
        DELAY_TIME_LEVEL.add(60 * 10);
        DELAY_TIME_LEVEL.add(60 * 20);
        DELAY_TIME_LEVEL.add(60 * 30);
        DELAY_TIME_LEVEL.add(60 * 60 * 1);
        DELAY_TIME_LEVEL.add(60 * 60 * 2);
    }

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(AbstractMQ mqModel) {
        rocketMQTemplate.convertAndSend(mqModel.getMQName(), mqModel.toMessage());
    }

    @Override
    public void asyncSend(AbstractMQ mqModel, SendCallback callback) {
        rocketMQTemplate.asyncSend(mqModel.getMQName(), mqModel.toMessage(), callback);
    }

    @Override
    public SendResult send(AbstractMQ mqModel, int delay) {
        // RocketMQ不支持自定义延迟时间， 需要根据传入的参数进行最近的匹配。
        return rocketMQTemplate.syncSend(mqModel.getMQName(), MessageBuilder.withPayload(mqModel.toMessage()).build(), 300000, getNearDelayLevel(delay));
    }

    /**
     * 获取最接近的节点值
     **/
    private int getNearDelayLevel(int delay) {
        // 如果包含则直接返回
        if (DELAY_TIME_LEVEL.contains(delay)) {
            return DELAY_TIME_LEVEL.indexOf(delay) + 1;
        }

        //两个时间的绝对值 - 位置
        TreeMap<Integer, Integer> resultMap = new TreeMap<>();
        DELAY_TIME_LEVEL.stream().forEach(time -> resultMap.put(Math.abs(delay - time), DELAY_TIME_LEVEL.indexOf(time) + 1));
        return resultMap.firstEntry().getValue();
    }

}
