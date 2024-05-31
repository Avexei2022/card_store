package ru.gb.group5984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Маршрутизация входящих запросов.
 */
@SpringBootApplication
public class ApiGatewayApp {

    /**
     * Точка входа.
     * @param args аргументы.
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApp.class, args);
    }

}
