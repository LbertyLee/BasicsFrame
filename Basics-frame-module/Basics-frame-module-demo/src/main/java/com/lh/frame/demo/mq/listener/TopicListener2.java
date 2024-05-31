package com.lh.frame.demo.mq.listener;


import com.lh.frame.common.constant.mq.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class TopicListener2 {
	
	@RabbitListener(queues = RabbitConstant.TOPIC_QUEUE_NAME_WTO)
	public void displayTopic(String message) throws IOException {
        log.info("从topicqueue2取出消息:{}", message);
		}
}
