package com.lh.frame.demo.mq.listener;

import com.lh.frame.common.constant.mq.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SubscribeListener2 {
    @RabbitListener(queues = RabbitConstant.TOPIC_QUEUE_NAME)
    public void subscribe(String message) {
        log.info("订阅者2收到消息:"+message);
    }
}
