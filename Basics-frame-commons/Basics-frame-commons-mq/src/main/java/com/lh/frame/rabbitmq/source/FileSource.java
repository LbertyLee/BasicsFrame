package com.lh.frame.rabbitmq.source;

import com.lh.frame.common.constant.mq.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建文件清理队列和交换机。
 */
//@Configuration
public class FileSource {
    /**
     * 创建并返回一个直接交换器实例。
     * 这个方法不接受任何参数，它会根据RabbitConstant中定义的EXCHANGE_TYPE常量来创建一个直接交换器。
     *
     * @return DirectExchange 直接交换器的实例。
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstant.X_DELAYED_MESSAGE);
    }

    /**
     * 创建并返回一个指定名称的队列。
     * @return 返回一个新创建的队列对象。
     */
    @Bean
    public Queue directQueue() {
        return new Queue((RabbitConstant.FILE_CLEAR_QUEUE));
    }


    /**
     * 创建一个直接绑定，将指定的队列绑定到直接交换器上。
     *
     * @return 返回一个Binding对象，表示已创建的绑定。
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(directQueue())
                .to(directExchange()).with(RabbitConstant.FILE_CLEAR_KEY);
    }
}
