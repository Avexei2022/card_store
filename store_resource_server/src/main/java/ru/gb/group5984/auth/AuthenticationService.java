package ru.gb.group5984.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.users.Role;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.repository.UserRepository;
import ru.gb.group5984.service.auth.JwtService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BasicConfig basicConfig;
    private final AuthConfig authConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    //TODO Реализовать
    /**
     * Регистрация нового сервиса.
     * @param request запрос на регистрацию.
     * @return Ответ с результатом регистрации.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("LOG: AuthenticationService.register = " + request.toString());
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)
                .enabled(true)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Аутентификация сервиса.
     * @param request запрос.
     * @return ответ с результатом.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("LOG: AuthenticationService.authenticate.request = " + request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        // Закомментированный код предназначен на случай регистрации сервисов в базе данных.
//        var user = userRepository.findUserByUsername(request.getUsername())
//                .orElseThrow();
        User user = new User(111L, authConfig.getUsername(), authConfig.getPassword()
                ,Role.Admin, true, "user@gmail.com", true);
        log.info("LOG: AuthenticationService.authenticate.user = " + user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Подготовка объекта HTTP-запроса.
     * @return
     */
    public HttpHeaders getHeaders() {
        String token = getToken(authConfig.getBankUsername(), authConfig.getBankPassword());
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Получить токен.
     * @param username логин.
     * @param password пароль.
     * @return токен.
     */
    private String getToken(String username, String password) {
        String url = basicConfig.getBANK_API() + "/auth/authenticate";
        HttpMethod method = HttpMethod.POST;
        var requestBody = AuthenticationRequest.builder()
                .username(username)
                .password(password).build();
        log.info("LOG:  ServerApiServiceImpl.getToken.requestBody = " + requestBody.getRequest() + "\n" + requestBody);
        headers.clear();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.getRequest(), headers);
        log.info("LOG:  ServerApiServiceImpl.getToken.requestEntity.Header = " + requestEntity.getHeaders());
        log.info("LOG:  ServerApiServiceImpl.getToken.requestEntity.Body = " + requestEntity.getBody());
        Class<AuthenticationResponse> responseType = AuthenticationResponse.class;
        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(url, method, requestEntity, responseType);
        String token = Objects.requireNonNull(response.getBody()).getToken();
        log.info("LOG:  ServerApiServiceImpl.getToken.token = " + token);
        return token;

    }
}
