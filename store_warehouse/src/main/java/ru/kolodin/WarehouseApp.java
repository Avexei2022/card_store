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

}
