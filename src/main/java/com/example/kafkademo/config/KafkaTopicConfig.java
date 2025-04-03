package com.example.kafkademo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${crypto.kafka.topics.coin-trades}")
    private String topic;

    @Bean
    public NewTopic minuteCandleTopic() {
        return TopicBuilder.name(topic)
                .configs(
                        Map.of(
                                TopicConfig.RETENTION_MS_CONFIG, "3600000" // 1h
                        )
                )
                .partitions(10)
                .replicas(1)
                .build();
    }
}
