package ru.kolodin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

//TODO Разделить на два микросервиса
/**
 * Основной класс веб-сервиса склада магазина.
 * Зарегистрирован на сервере Eureka
 */
@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan

public class WarehouseApp {

    /**
     * Точка входа.
     * @param args аргументы.
     */
    public static void main(String[] args) {
        SpringApplication.run(WarehouseApp.class, args);
    }

    /**
     * Синхронный клиент REST.
     * @return новый экземпляр.
     */
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }

    /**
     * Структура данных, представляющая заголовки HTTP-запросов или ответов.
     * @return новый экземпляр.
     */
    @Bean
    public HttpHeaders headers()
    {
        return new HttpHeaders();
    }
}
