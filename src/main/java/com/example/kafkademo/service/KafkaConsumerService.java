package com.example.kafkademo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${crypto.kafka.api}")
    private String BITHUMB_API_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    @KafkaListener(topics = "${crypto.kafka.topics.coin-trades}", groupId = "myGroup1")
    public void consume(String coin) {
        String url = UriComponentsBuilder.fromHttpUrl(BITHUMB_API_URL)
                .queryParam("market", "KRW-" + coin)
                .queryParam("count", 1)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            List<Map<String, Object>> tradeData = objectMapper.readValue(response, new TypeReference<>() {});

            log.info("[CONSUMER] Trade Data: {}", tradeData);
        } catch (Exception e) {
            log.error("[CONSUMER] API 요청 실패: {}", e.getMessage());
        }
    }
}
