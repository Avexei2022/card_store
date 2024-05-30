package group5984.configuration;

import group5984.aspect.UserActionAspect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигуратор базовых настроек.
 * Принимает значения из файла настройки application.yaml
 */
@ConfigurationProperties(prefix = "url")
@Getter
@Setter
public class BasicConfig {

    /**
     * Адрес сервера ресурсов магазина.
     */
    private String SERVER_API;

    /**
     * Адрес API ресурса Rick and Morty.
     */
    private String CHARACTER_API;

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
