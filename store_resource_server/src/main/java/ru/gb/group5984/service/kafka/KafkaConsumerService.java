package ru.gb.group5984.service.kafka;

import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class KafkaConsumerService {

    @KafkaListener(topics = "foreach", groupId = "myGroup")
    public void  receiveMessage(String message) {
        log.info("Kafka message: " + message);
    }
}
