package com.lh.frame.rabbitmq.demo;

import com.lh.frame.common.constant.mq.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeSource {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitConstant.EXCHANGE_NAME);
    }

    @Bean
    public Queue directQueue1() {
        return new Queue(RabbitConstant.DIRECT_QUEUE_NAME1);
    }

    @Bean
    public Queue directQueue2() {
        return new Queue(RabbitConstant.DIRECT_QUEUE_NAME2);
    }

    //binding将交换机和相应队列连起来
    @Bean
    public Binding bindingorange(){
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with(RabbitConstant.DIRECT_KEY_ORANGE);
    }


    @Bean
    public Binding bindingblack(){
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with(RabbitConstant.DIRECT_KEY_BLACK);
    }
}
