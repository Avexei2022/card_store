package kolodin.configuration;

import kolodin.aspect.UserActionAspect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигуратор базовых настроек.
 * Принимает значения из файла настройки application.yaml
 */
@ConfigurationProperties(prefix = "url")
@Configuration
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

    /**
     * Аспект для регистрации действий пользователей и вывода их в консоль.
     * @return новый экземпляр.
     */
    @Bean
    public UserActionAspect loginAspect() {
        return new UserActionAspect();
    }
}
