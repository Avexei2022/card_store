package ru.gb.group5984;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Маршрутизация входящих запросов.
 * Интегрирован с сервисом авторизации и реализует механизм Token Reley.
 * Помещает токен, полученный от сервера авторизации в заголовок запроса к сервисам, которые проксирует.
 */
@SpringBootApplication
public class ApiGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApp.class, args);
    }

}
