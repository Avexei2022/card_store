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
    private String CHARACTER_API;
    private String BANK_API;
    private String DEBIT_USER;
    private RestTemplate restTemplate;

    @Bean
    public UserActionAspect loginAspect() {
        return new UserActionAspect();
    }


}
