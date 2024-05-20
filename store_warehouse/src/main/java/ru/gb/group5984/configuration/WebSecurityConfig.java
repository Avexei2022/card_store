package ru.gb.group5984.configuration;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.group5984.service.UserDetailsServiceImpl;

/**
 * Конфигуратор безопасности
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Log
public class WebSecurityConfig{

    /**
     * Создание экземпляра сервиса пользователей.
     * @return сервис пользователей.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Кодировщик паролей
     * @return кодировщик
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    /**
     *  Реализация AuthenticationProvider,
     *  которая использует UserDetailsService и PasswordEncoder
     *  для проверки подлинности имени пользователя и пароля.
     * @return экземпляр поставщика DAO-аутентификации.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Правила фильтрации.
     * @param http защищенный http запрос.
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/css/**", "/", "/index")
                                    .permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(exception -> exception.accessDeniedPage("/index"))
                .formLogin(login -> login
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
