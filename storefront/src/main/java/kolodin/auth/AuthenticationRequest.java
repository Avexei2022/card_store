package kolodin.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс запроса аутентификации.
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

    /**
     * Получить тело запроса в формате JSON.
     * @return Строка/тело запроса.
     */
    public String getRequest() {
        return "{\"username\": \""
                + username
                + "\", \"password\": \""
                + password
                + "\"}";
    }
}
