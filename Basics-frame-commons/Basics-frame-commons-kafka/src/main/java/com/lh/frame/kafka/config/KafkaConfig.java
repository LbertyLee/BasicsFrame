package com.lh.frame.kafka.config;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.cluster")
public class KafkaConfig {

    /**
     * bootstrapServers 字符串存储了Kafka启动服务器的地址和端口信息。
     * 这个变量通常用于配置Kafka客户端，以便它们知道如何连接到Kafka集群。
     * 其格式通常为 "host1:port1,host2:port2,..."。
     */
    private String bootstrapServers;


    public Map<String, Object> getProducerConfig(){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        stringObjectHashMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSelection.class.getName());
        stringObjectHashMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSelection.class.getName());
        return stringObjectHashMap;
    }

    public Map<String, Object> getConsumerConfig(String groupId){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        stringObjectHashMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSelection.class.getName());
        stringObjectHashMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSelection.class.getName());
        stringObjectHashMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return stringObjectHashMap;
    }


}
