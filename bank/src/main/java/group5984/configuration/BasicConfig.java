package group5984.configuration;

import group5984.aspect.UserActionAspect;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


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
    private String BANK_API;
    private RestTemplate restTemplate;

    @Bean
    public UserActionAspect loginAspect() {
        return new UserActionAspect();
    }


}
