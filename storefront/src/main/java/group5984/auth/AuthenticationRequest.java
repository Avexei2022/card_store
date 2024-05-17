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

    private String username;
    String password;



    public String getRequest() {
        return "{\"username\": \""
                + username
                + "\", \"password\": \""
                + password
                + "\"}";
    }
}
