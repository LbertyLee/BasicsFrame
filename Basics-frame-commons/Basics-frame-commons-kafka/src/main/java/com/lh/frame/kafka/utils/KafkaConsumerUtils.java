package com.lh.frame.kafka.utils;

import com.lh.frame.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class KafkaConsumerUtils<T> {

    private final KafkaConsumer<String, T> kafkaConsumer;
    // 添加一个标识来控制消费过程是否继续
    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public KafkaConsumerUtils(String topic, String groupId) {
        Properties props = new Properties();
        props.putAll(new KafkaConfig().getConsumerConfig(groupId));
        this.kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    /**
     * 消费指定主题的消息
     *
     * @return 消费到的消息，如果没有消息则返回null
     */
    public T consume() {
        // 考虑异常处理
        try {
            ConsumerRecords<String, T> poll = kafkaConsumer.poll(100);
            if (poll.count() > 0) {
                // 这里假设返回的数据类型与传入的dataType一致，实际应用中可能需要进一步的类型检查或转换
                return poll.iterator().next().value();
            }
            return null;
        } catch (Exception e) {
            return null;
        }finally {
            // 确保在退出时关闭消费者
            kafkaConsumer.close();
        }
    }



}

