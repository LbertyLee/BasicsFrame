package com.lh.frame.demo.mq.service;

import com.lh.frame.common.constant.mq.RabbitConstant;

import com.lh.frame.rabbitmq.constant.RabbitBasicsConstant;
import com.lh.frame.rabbitmq.entity.MqMessage;
import com.lh.frame.rabbitmq.utils.RabbitMqUtil;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class DirectSourceService {

    @Resource
    private  RabbitMqUtil<MqMessage> rabbitMqUtil;



    /**
     * 发送直接消息
     * @param message 要发送的消息内容，类型为MqMessage
     * 该方法通过调用rabbitMqUtil的sendDirectMessage方法，将指定的消息发送出去。
     * 其中，消息路由类型为DIRECT，路由键为FILE_CLEAR_KEY，消息体为message的toString形式。
     */
    public void sendDirectMessage(MqMessage message){
        rabbitMqUtil.sendDirectMessage(RabbitConstant.EXCHANGE_NAME,message.getKey(),message.toString());
    }

    /**
     *生产者消费者模式的配置
     * @param message 要发送的消息对象，不应为null。
     * 该方法使用RabbitMQ工具类向名为TOPIC_QUEUE_NAME的队列发送消息，
     * 消息的路由键为FILE_CLEAR_KEY，发送的消息为参数message。
     */
    public void ProducerConsumerSentMessage(MqMessage message) {
        rabbitMqUtil.sendProduceMessage(RabbitConstant.QUEUE_NAME,message.toString());
    }

    /**
     * 发布订阅模式下发送消息。
     * 该方法通过RabbitMQ的实用工具类向指定的交换器发送消息，交换器根据绑定键将消息路由到一个或多个队列中。
     *
     * @param message 要发送的消息对象，转换为字符串后发送。
     */
    public void PublishSubscribeSentMessage(MqMessage message) {
        // 使用RabbitMQ工具类发送发布订阅模式的消息
        rabbitMqUtil.sendPublishMessage(RabbitBasicsConstant.FANOUT,"",message.toString());
    }


    /**
     * 发送主题模式的消息。
     * 使用RabbitMQ工具类向指定的主题发送消息，消息内容为MqMessage对象的字符串形式。
     *
     * @param message 要发送的消息内容，类型为MqMessage。
     */
    public void TopicSentMessage(MqMessage message) {
        // 发送主题模式的消息
        rabbitMqUtil.sendTopicMessage(RabbitConstant.TOPIC_NAME, message.getKey(), message.toString());
    }
}



