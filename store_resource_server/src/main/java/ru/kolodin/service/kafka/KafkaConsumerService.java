package ru.kolodin.service.kafka;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log
public class KafkaConsumerService {

    @KafkaListener(topics = "foreach", groupId = "myGroup")
    public void  receiveMessage(String message) {
        log.info("Kafka message: " + message);
    }
}
