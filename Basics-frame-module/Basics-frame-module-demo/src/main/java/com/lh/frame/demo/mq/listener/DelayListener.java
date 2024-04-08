package com.lh.frame.demo.mq.listener;

import com.lh.frame.common.constant.mq.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DelayListener {

    @RabbitListener(queues = RabbitConstant.FILE_CLEAR_QUEUE)
    public void receive(String message){
        log.info("收到延迟消息：{}",message);
    }
}
