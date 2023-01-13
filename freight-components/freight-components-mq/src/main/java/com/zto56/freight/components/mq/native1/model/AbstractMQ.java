package com.zto56.freight.components.mq.native1.model;


/**
 * 定义MQ消息格式
 *
 * @author zhangqingfu
 * @date 2022-07-04
 */
public abstract class AbstractMQ {

    /** MQ名称 **/
    public abstract String getMQName();


    /** 构造MQ消息体 String类型 **/
    public abstract String toMessage();

}
