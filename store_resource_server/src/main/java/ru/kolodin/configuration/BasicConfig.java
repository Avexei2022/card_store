package ru.kolodin.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kolodin.aspect.UserActionAspect;

/**
 * Конфигуратор
 * принимает значения из файла настройки application.yaml
 */
@Component
@ConfigurationProperties(prefix = "url")
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
@Getter
@Setter
public class BasicConfig {

    /**
     * Адрес сервиса ресурсов Rick and Morty.
     */
    private String CHARACTER_API;

    /**
     * Адрес сервиса ресурсов банка.
     */
    private String BANK_API;

    /**
     * Имя/логин владельца магазина, имеющего счет в банке.
     */
    private String DEBIT_USER;

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

//    /**
//     * Аспект для регистрации действий пользователей и вывода их в консоль.
//     * @return новый экземпляр.
//     */
//    @Bean
//    public UserActionAspect loginAspect() {
//        return new UserActionAspect();
//    }


}
