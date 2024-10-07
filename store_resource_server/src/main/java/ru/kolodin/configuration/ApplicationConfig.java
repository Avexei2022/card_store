package ru.kolodin.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kolodin.auth.AuthConfig;
import ru.kolodin.model.users.Role;
import ru.kolodin.model.users.User;


/**
 * Конфигуратор приложения.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    /**
     * Конфигуратор аутентификации.
     */
    private final AuthConfig authConfig;

    /**
     * Сервис пользовательских данных.
     * Создан единственный пользователь, логин и пароль которого
     * устанавливаются в конфигурационном файле application.yaml
     * @return данные пользователя.
     */
    @Bean
    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findUserByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return username -> new User(111L, authConfig.getUsername(), authConfig.getPassword()
                , Role.ADMIN, true, "user@gmail.com", true);
    }

    /**
     * Обработчик аутентификации.
     * @return обработчик.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Кодировщик пароля.
     * @return экземпляр кодировщика.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Менеджер аутентификации.
     * @param config конфигурация проверки подлинности.
     * @return экземпляр менеджера аутентификации.
     * @throws Exception исключение.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
