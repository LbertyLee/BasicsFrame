package com.lh.frame.kafka.utils;

import com.lh.frame.kafka.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerUtils<T> {


    private final KafkaProducer<String, T> kafkaProducer;
    private final String topic;
    private final String key;

    public KafkaProducerUtils(String topic, String key, String value) {
        // 参数校验
        if (topic == null || key == null) {
            throw new IllegalArgumentException("Topic and Key must not be null.");
        }
        this.topic = topic;
        this.key = key;
        Properties props = new Properties();
        props.putAll(new KafkaConfig().getProducerConfig());
        this.kafkaProducer = new KafkaProducer<>(props);
    }


    public void send(T data) {
        try {
            ProducerRecord<String, T> record = new ProducerRecord<>(topic, key, data);
            kafkaProducer.send(record);
            log.info("KafkaProducerUtils send success");
        } catch (Exception e) {
            log.error("Kafka message send failed", e);
        } finally {
            kafkaProducer.close();
        }
    }


}

