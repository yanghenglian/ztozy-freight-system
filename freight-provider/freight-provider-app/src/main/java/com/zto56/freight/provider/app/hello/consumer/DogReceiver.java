// package com.zto56.freight.provider.consumer;
//
// import com.zto56.freight.components.mq.stream.model.DogMessage;
// import com.zto56.freight.components.mq.stream.receive.IStreamReceiver;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.cloud.stream.annotation.StreamListener;
// import org.springframework.stereotype.Component;
//
// /**
//  *
//  * @author zhangqingfu
//  * @date 2022-08-26
//  */
// @Component
// @Slf4j
// public class DogReceiver implements IStreamReceiver {
//     /**
//      * 接收消息
//      */
//     @StreamListener(value = DogMessage.INPUT)
//     public void receiveMsg(String message) {
//         log.info("receiveMsg message:{}", message);
//     }
//
//     // /**
//     //  * 接收原始message
//     //  *
//     //  * @param message 讯息
//     //  */
//     // @StreamListener(value = Demo01Message.INPUT)
//     // public void listener(Message<byte[]> message) {
//     //     log.info("headers:{}", message.getHeaders());
//     //     byte[] payload = message.getPayload();
//     //     log.info("接收到的消息:{}", new String(payload));
//     // }
// }
