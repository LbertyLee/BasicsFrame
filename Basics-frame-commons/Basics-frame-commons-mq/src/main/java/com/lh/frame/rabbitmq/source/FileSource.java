package com.lh.frame.rabbitmq.source;

import com.lh.frame.common.constant.mq.RabbitConstant;
import com.lh.frame.rabbitmq.constant.RabbitBasicsConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 创建文件清理队列和交换机。
 */
@Configuration
public class FileSource {
    /**
     * 创建并返回一个直接交换器实例。
     * 这个方法不接受任何参数，它会根据RabbitConstant中定义的EXCHANGE_TYPE常量来创建一个直接交换器。
     *
     * @return DirectExchange 直接交换器的实例。
     */
    @Bean
    public CustomExchange customExchange() {
        HashMap<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitConstant.FILE_DELAYED_MESSAGE, RabbitBasicsConstant.X_DELAYED_MESSAGE
                , true, false, arguments);
    }
    @Bean
    public Queue fileQueue() {
        return new Queue(RabbitConstant.FILE_CLEAR_QUEUE, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(fileQueue()).to(customExchange()).with(RabbitConstant.FILE_CLEAR_KEY).noargs();
    }
}
