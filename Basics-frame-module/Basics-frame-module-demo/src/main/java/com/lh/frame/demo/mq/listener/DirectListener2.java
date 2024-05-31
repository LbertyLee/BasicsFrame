package com.lh.frame.demo.mq.listener;

import com.lh.frame.common.constant.mq.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class DirectListener2 {

    @RabbitListener(queues = RabbitConstant.DIRECT_QUEUE_NAME2)
    public void displayMail(String message) throws Exception {

        log.info("DirectListener接收到directqueue2的消息：{}", message);
    }


}
