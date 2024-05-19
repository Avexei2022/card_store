package ru.gb.group5984.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


/**
 * Контроллер регистрации и аутентификации
 */
@RestController
@RequestMapping("/store_server/auth")
@RequiredArgsConstructor
@Log
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        log.info("LOG: AuthenticationController" +
                ".register.request  =  " + request.toString());
        ResponseEntity<AuthenticationResponse> response = ResponseEntity
                .ok(authenticationService.register(request));
        log.info("LOG: AuthenticationController" +
                ".register.response =  " + response);
        return response;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("LOG: AuthenticationController" +
                ".authenticate.request =  " + request.toString());
        ResponseEntity<AuthenticationResponse> response = ResponseEntity
                .ok(authenticationService.authenticate(request));
        log.info("LOG: AuthenticationController" +
                ".authenticate.response =  " + response);
        return response;
    }
}