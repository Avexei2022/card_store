package ru.gb.group5984.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/store_server/auth")
@RequiredArgsConstructor
@Log
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
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
