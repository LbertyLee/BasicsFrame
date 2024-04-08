package com.lh.frame.rabbitmq.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RabbitMqUtil<T> {

    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 使用direct交换机发送消息
     */
    public void sendDirectMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
    /**
     * 使用topic交换机发送消息
     */
    public void sendTopicMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }


    /**
     * 发送生产者消息到指定队列
     * @param queue 目标队列的名称，消息将被发送到这个队列
     * @param message 要发送的消息内容
     * 该方法会使用RabbitTemplate将消息转换为适当的格式并发送到指定的队列。
     */
    public void sendProduceMessage( String queue, String message) {
        rabbitTemplate.convertAndSend(queue, message); // 使用RabbitTemplate发送消息到指定队列
    }

    /**
     *发布订阅模式
     */
    public void sendPublishMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    /**
     * 延迟消息
     */
    public void sendDelayMessage(String exchange, String routingKey, String dataMessage, Integer delayTime) {
        rabbitTemplate.convertAndSend(exchange, routingKey, dataMessage, message -> {
            message.getMessageProperties().setDelay(delayTime); // 设置延迟时间
            return message;
        });
    }

}
