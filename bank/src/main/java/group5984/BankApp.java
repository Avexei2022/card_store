package group5984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


/**
 * Основной класс микросервиса Банка.
 * Зарегистрирован на сервере Eureka
 */
@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan

public class BankApp {
    public static void main(String[] args) {
        SpringApplication.run(BankApp.class, args);
    }

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders headers()
    {
        return new HttpHeaders();
    }
}
