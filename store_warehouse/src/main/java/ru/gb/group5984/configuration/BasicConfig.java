package ru.gb.group5984.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.aspect.UserActionAspect;

/**
 * Конфигуратор базовых настроек.
 * Принимает значения из файла настройки application.yaml
 */
@Component
@ConfigurationProperties(prefix = "url")
@ComponentScan
@EnableAspectJAutoProxy
@Getter
@Setter
public class BasicConfig {

    /**
     * Адрес API ресурса Rick and Morty.
     */
    private String CHARACTER_API;

    /**
     * Адрес сервера ресурсов магазина.
     */
    private String SERVER_API;

    /**
     * Синхронный клиент REST.
     */
    private RestTemplate restTemplate;

    /**
     * Аспект для регистрации действий пользователей и вывода их в консоль.
     * @return новый экземпляр.
     */
    @Bean
    public UserActionAspect loginAspect() {
        return new UserActionAspect();
    }


}
