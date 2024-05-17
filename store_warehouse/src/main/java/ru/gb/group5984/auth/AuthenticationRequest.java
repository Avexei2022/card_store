package ru.gb.group5984.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


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
