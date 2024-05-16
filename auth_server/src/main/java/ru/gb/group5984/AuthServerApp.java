package ru.gb.group5984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Стартовый класс (точка входа) сервера авторизации.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
public class AuthServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }
}
