package com.lh.frame.rabbitmq.demo;

import com.lh.frame.common.constant.mq.RabbitConstant;
import com.lh.frame.rabbitmq.constant.RabbitBasicsConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *发布订阅模式的配置,包括两个队列和对应的订阅者,
 * 发布者的交换机类型使用fanout(子网广播),两根网线binding用来绑定队列到交换机
 */
@Configuration
public class PublishSubscribeSource {
    @Bean
    public Queue myQueue1() {
        return new Queue(RabbitConstant.TOPIC_QUEUE_NAME);

    }

    @Bean
    public Queue myQueue2() {
        return new Queue(RabbitConstant.TOPIC_QUEUE_NAME_WTO);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitBasicsConstant.FANOUT);
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(myQueue1()).to(fanoutExchange());
    }


    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(myQueue2()).to(fanoutExchange());
    }
}
