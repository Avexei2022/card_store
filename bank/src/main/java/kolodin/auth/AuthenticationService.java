package kolodin.auth;

import kolodin.configuration.BasicConfig;
import kolodin.model.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * Сервис аутентификации.
 */
@Service
@RequiredArgsConstructor
@Log
public class AuthenticationService {

    /**
     * Конфигуратор базовых настроек.
     */
    private final BasicConfig basicConfig;

    /**
     * Конфигуратор аутентификации.
     */
    private final AuthConfig authConfig;

    /**
     * Синхронный клиент REST.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Структура данных, представляющая заголовки HTTP-запросов или ответов.
     */
    @Autowired
    private HttpHeaders headers;

    /**
     * Подготовка объекта HTTP-запроса.
     * @return HTTP-запрос.
     */
    public HttpEntity<String> getRequestEntity() {
        String token = getToken(authConfig.getBankUsername(), authConfig.getBankPassword());
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    /**
     * Получить пользователя по имени.
     * @param name имя пользователя.
     * @return пользователь.
     */
    public User getUserByUserName(String name) {
        String url = basicConfig.getBANK_API() + "/user/" + name;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<User> responseType = User.class;
        log.info("LOG: ServerApiServiceImpl.getUserByUserName.URI = " + url);
        ResponseEntity<User> response = restTemplate.exchange(url, method, requestEntity, responseType);
        User user = response.getBody();
        assert user != null;
        log.info("LOG: ServerApiServiceImpl.getUserByUserName.userDetails = " + user);
        return user;
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
