package com.zto56.freight.components.mq.native1.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MQ消息收发测试
 *
 * @author zhangqingfu
 * @date 2022-07-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DemoMQ extends AbstractMQ {

    /** 【！重要配置项！】 定义MQ名称 **/
    public static final String MQ_NAME = "DEMO_TEST_MQ_TOPIC";

    /** 内置msg 消息体定义 **/
    private MsgPayload payload;

    /**  【！重要配置项！】 定义Msg消息载体 **/
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MsgPayload {
        private Long id;
        private String name;
    }

    @Override
    public String getMQName() {
        return MQ_NAME;
    }


    @Override
    public String toMessage() {
        return JSONObject.toJSONString(payload);
    }

    /**  【！重要配置项！】 构造MQModel , 一般用于发送MQ时 **/
    public static DemoMQ build(MsgPayload msgPayload ) {
        return new DemoMQ(msgPayload);
    }

    /** 解析MQ消息， 一般用于接收MQ消息时 **/
    public static MsgPayload parse(String msg) {
        return JSON.parseObject(msg, MsgPayload.class);
    }

    /** 定义 IMQReceiver 接口： 项目实现该接口则可接收到对应的业务消息  **/
    public interface IMQReceiver {
        void receive(MsgPayload payload);
    }

}
