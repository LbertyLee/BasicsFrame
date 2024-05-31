package com.lh.frame.demo.mq.listener;

import com.lh.frame.common.constant.mq.RabbitConstant;
import com.lh.frame.rabbitmq.entity.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DirectListener {

    @RabbitListener(queues = RabbitConstant.DIRECT_QUEUE_NAME1)
    public void displayMail(String message) throws Exception {
        log.info("DirectListener接收到directqueue1的消息：{}", message);
    }


}
