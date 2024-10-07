package kolodin.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Конфигуратор аутентификации.
 * Получает из файла application.yaml
 * логин и пароль для подключения к сервису ресурсов банка.
 */
@Component
@ConfigurationProperties(prefix = "auth-data")
@ComponentScan
@Getter
@Setter
@NoArgsConstructor
public class AuthConfig {
    /**
     * Логин сервиса ресурсов банка.
     */
    private String bankUsername;
    /**
     * Пароль сервиса ресурсов банка.
     */
    private String bankPassword;
}
