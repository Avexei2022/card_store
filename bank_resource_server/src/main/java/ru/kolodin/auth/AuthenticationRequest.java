package ru.kolodin.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс запроса на аутентификацию.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    String password;
}
