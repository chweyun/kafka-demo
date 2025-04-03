# Kafka Demo 프로젝트

이 프로젝트는 **Apache Kafka**를 활용하여 가상화폐(코인) 데이터를 주고받는  **Producer**와 **Consumer**를 구현한 데모 프로젝트입니다.

## 📌 프로젝트 개요
- **Kafka Producer**: 일정 간격(1초)마다 여러 코인의 심볼(BTC, ETH 등)을 Kafka 토픽으로 전송합니다.
- **Kafka Consumer**: 토픽에서 수신한 코인 심볼을 바탕으로 Bithumb API에서 해당 코인의 최신 거래 데이터를 가져옵니다.
- **로그 출력**: Consumer는 API 응답 데이터를 받아 로그로 출력합니다.
- **확장 가능**: 데이터를 데이터베이스 또는 인메모리 저장소에 저장하는 기능을 추가할 수 있습니다.

## 🚀 기술 스택
- Java 17
- Spring Boot 3
- Apache Kafka
- Spring Kafka

## 📂 프로젝트 구조
```
/kafka-demo
├── src/main/java/com/example/kafkademo
│   ├── service
│   │   ├── KafkaProducerService.java  # Kafka 메시지 생산자
│   │   ├── KafkaConsumerService.java  # Kafka 메시지 소비자
│   ├── config
│   │   ├── KafkaConfig.java           # Kafka 설정 파일
│   ├── KafkaDemoApplication.java      # Spring Boot 메인 애플리케이션
├── src/main/resources
│   ├── application.yml                # 환경 설정 파일
└── README.md
```


## 🔍 기능 설명
### KafkaProducerService.java
- `@Scheduled(fixedRate = 1000)`: 1초마다 미리 지정된 코인 심볼 리스트를 Kafka 토픽으로 전송합니다.
- `KafkaTemplate.send()`: 비동기 방식으로 메시지를 전송하고, 전송 성공/실패 여부를 로깅합니다.

### KafkaConsumerService.java
- `@KafkaListener(topics = "${crypto.kafka.topics.coin-trades}", groupId = "myGroup1")`: Kafka 토픽을 구독합니다.
- `RestTemplate.getForObject()`: 수신한 코인 심볼을 사용해 Bithumb API를 호출합니다.
- `objectMapper.readValue()`: JSON 응답을 리스트 형태로 변환 후 로그 출력합니다.

## 📌 확장 가능성
- **DB 저장**: 수집한 데이터를 MySQL, PostgreSQL, MongoDB 등에 저장하여 활용 가능
- **In-Memory 캐싱**: Redis를 사용해 최근 데이터를 빠르게 조회할 수 있도록 개선 가능
- **다양한 거래소 지원**: Bithumb뿐만 아니라 Binance, Upbit 등의 API를 추가 연동 가능
