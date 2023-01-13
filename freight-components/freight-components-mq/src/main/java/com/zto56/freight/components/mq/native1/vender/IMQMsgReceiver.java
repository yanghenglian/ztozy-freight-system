package com.zto56.freight.components.mq.native1.vender;


/**
 * MQ 消息接收器 接口定义
 *
 * @author zhangqingfu
 * @date 2022-07-04
 */
public interface IMQMsgReceiver {

    /** 接收消息 **/
    void receiveMsg(String msg);
}
