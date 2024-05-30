package group5984.auth;

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
     * @return Строка/тело запроса в формате JSON.
     */
    public String getRequest() {
        return "{\"username\": \""
                + username
                + "\", \"password\": \""
                + password
                + "\"}";
    }
}
