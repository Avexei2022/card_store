package ru.kolodin.configuration;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигуратор безопасности.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log
public class SecurityConfig {

    /**
     * Фильтр аутентификации.
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Поставщик проверки подлинности имени пользователя и пароля.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Правила фильтрации.
     * @param http защищенный http запрос.
     * @return цепочка фильтров.
     * @throws Exception общие исключения.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(HttpMethod.POST,"/store_server/auth/**"
                                        , "/store_server/characters/register/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/actuator/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
