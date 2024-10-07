package ru.kolodin.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST-контроллер аутентификации.
 */
@RestController
@RequestMapping("/bank_server/auth")
@RequiredArgsConstructor
@Log
public class AuthenticationController {

    /**
     * Сервис аутентификации.
     */
    private final AuthenticationService service;

    /**
     * Регистрация нового пользователя.
     * @param request запрос на регистрацию.
     * @return токен.
     */
    @GetMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        log.info("LOG: AuthenticationController" +
                ".register.request  =  " + request.toString());
        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(service.register(request));
        log.info("LOG: AuthenticationController" +
                ".register.response =  " + response);
        return response;
    }

    /**
     * Аутентификация пользователя.
     * @param request запрос на аутентификацию.
     * @return токен.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("LOG: AuthenticationController" +
                ".authenticate.request =  " + request.toString());
        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(service.authenticate(request));
        log.info("LOG: AuthenticationController" +
                ".authenticate.response =  " + response);
        return response;
    }
}
