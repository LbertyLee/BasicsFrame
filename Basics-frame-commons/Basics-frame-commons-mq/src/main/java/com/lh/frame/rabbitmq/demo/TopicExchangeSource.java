package com.lh.frame.rabbitmq.demo;

import com.lh.frame.common.constant.mq.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//topic交换机模型，需要一个topic交换机，两个队列和三个binding
@Configuration
public class TopicExchangeSource {
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitConstant.TOPIC_NAME);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(RabbitConstant.TOPIC_QUEUE_NAME);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(RabbitConstant.TOPIC_QUEUE_NAME_WTO);
    }


    //3个binding将交换机和相应队列连起来
    @Bean
    public Binding bindingtopic1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("*.orange.*");
    }

    @Bean
    public Binding bindingtopic2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("*.*.rabbit");
    }


    @Bean
    public Binding bindingtopic3(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("lazy.#");
    }

}
