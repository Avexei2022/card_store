package group5984.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth-data")
@ComponentScan
@Getter
@Setter
public class AuthConfig {
    private String username;
    private String password;
}
