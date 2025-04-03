package com.example.kafkademo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducerService {

    @Value("${crypto.kafka.topics.coin-trades}")
    private String topic;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private final List<String> coins = List.of("BTC", "ETH", "XRP", "ADA", "DOGE", "SOL", "DOT", "BCH", "ETC", "LINK");

    @Scheduled(fixedRate = 1000) // 1초마다 실행
    public void sendCoinSymbols() {
        for (String coin : coins) {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, coin);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("[PRODUCER] Sent message: {}", coin);
                } else {
                    log.error("[PRODUCER] Failed to send message: {} due to {}", coin, ex.getMessage());
                }
            });
        }
    }
}
