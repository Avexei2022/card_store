package ru.gb.group5984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

//отправить POST-запрос на /actuator/bus-refresh микросервиса

/**
 * Сервер для централизованного управления внешними конфигурациями
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApp {

    /**
     * Точка входа.
     * @param args аргументы.
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApp.class, args);
    }
}
