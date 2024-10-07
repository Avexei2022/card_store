package ru.kolodin.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс запроса на регистрацию.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Адрес электронной почты пользователя.
     */
    private String email;
}
