package com.lh.frame.demo.mq.listener;

import com.lh.frame.common.constant.mq.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueListener2 {



    @RabbitListener(queues = RabbitConstant.QUEUE_NAME)
    public void displayMail2(String message){
        log.info("队列监听器2收到消息{}", message);
    }
}
