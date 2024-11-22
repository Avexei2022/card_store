package ru.kolodin.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * Создание топиков Кафка
 */
@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    /**
     * Тема с одним разделом
     * @return топик
     */
    @Bean
    public NewTopic restController() {
        return TopicBuilder.name("StoreResourceServer.RestController")
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Bean
    public NewTopic email() {
        return TopicBuilder.name("StoreResourceServer.Email")
                .partitions(3)
                .build();
    }

//    @Bean
//    public NewTopic topic5() {
//        return TopicBuilder.name("topic5")
//                .replicas(2)
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic6() {
//        return TopicBuilder.name("topic6")
//                .partitions(3)
//                .build();
//    }

//    /**
//     * Создание нескольких тем
//     * @return топики
//     */
//    @Bean
//    public KafkaAdmin.NewTopics topic456() {
//        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("topic7")
//                        .build(),
//                TopicBuilder.name("topic8")
//                        .replicas(2)
//                        .build(),
//                TopicBuilder.name("topic9")
//                        .partitions(3)
//                        .build());
//    }

    @KafkaListener(id = "cardStoreGroup", topics = "StoreResourceServer.Email")
    public void listen(String in) {
        System.out.println(in);
    }
}
