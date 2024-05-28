package group5984.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.client.RestTemplate;

/**
 * Конфигуратор
 * принимает значения из файла настройки application.yaml
 */
@ConfigurationProperties(prefix = "url")
@Getter
@Setter
public class BasicConfig {
    private String SERVER_API;
    private String CHARACTER_API;
    private RestTemplate restTemplate;
}
