package ru.kolodin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Основной класс сервера ресурсов банка.
 */
@SpringBootApplication
public class BankServerApp {

    /**
     * Точка входа.
     * @param args аргументы.
     */
    public static void main(String[] args) {
        SpringApplication.run(BankServerApp.class, args);
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
