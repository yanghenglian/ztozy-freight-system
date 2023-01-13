// package com.zto56.freight.components.mq.stream.send;
//
// import com.zto56.freight.components.mq.stream.model.DogMessage;
// import com.zto56.freight.components.mq.stream.source.DogSource;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.support.MessageBuilder;
// import org.springframework.stereotype.Component;
//
// /**
//  * mq发送
//  * @author zhangqingfu
//  * @date 2022-08-30
//  */
// @Component
// public class DogStreamSender implements IStreamSender {
//     @Autowired
//     private DogSource source;
//
//     public boolean send(DogMessage message) {
//         Message<DogMessage> springMessage = MessageBuilder.withPayload(message).build();
//         return source.output().send(springMessage);
//     }
// }
