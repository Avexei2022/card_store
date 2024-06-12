package ru.gb.group5984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Работающий сервер Eureka на порту 8761
 * http://localhost:8761/
 * хранит информацию обо всех зарегистрированных микросервисах.
 * Является централизованной и автоматизированной системой
 * для обнаружения и управления микросервисами.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {

    /**
     * Точка входа.
     * @param args аргументы.
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
