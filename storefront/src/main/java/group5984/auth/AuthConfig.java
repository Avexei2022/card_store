package group5984.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Конфигуратор аутентификации.
 * Получает данные из фала application.yaml
 */
@Component
@ConfigurationProperties(prefix = "auth-data")
@ComponentScan
@Getter
@Setter
public class AuthConfig {

    /**
     * Логин сервиса ресурсов магазина.
     */
    private String username;

    /**
     * Пароль сервиса ресурсов магазина.
     */
    private String password;
}
