package ru.kolodin.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kolodin.aspect.UserActionAspect;


/**
 * Конфигуратор
 * принимает значения из файла настройки application.yaml
 */
@Component
@ConfigurationProperties(prefix = "url")
@ComponentScan
@EnableAspectJAutoProxy
@Getter
@Setter
public class BasicConfig {

    /**
     * Синхронный клиент REST.
     */
    private RestTemplate restTemplate;

    /**
     * Адрес сервиса ресурсов Rick and Morty.
     */
    private String CLIENT_API;

    /**
     * Аспект для регистрации действий пользователей и вывода их в консоль.
     * @return новый экземпляр.
     */
    @Bean
    public UserActionAspect loginAspect() {
        return new UserActionAspect();
    }


}
