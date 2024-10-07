package ru.kolodin.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kolodin.model.users.Role;
import ru.kolodin.model.users.User;
import ru.kolodin.repository.UserRepository;
import ru.kolodin.service.JwtService;


/**
 * Сервис аутентификации.
 */
@Service
@RequiredArgsConstructor
@Log
public class AuthenticationService {

    /**
     * Репозиторий пользователей ресурса.
     */
    private final UserRepository repository;

    /**
     * Кодировщик паролей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Сервис токенов.
     */
    private final JwtService jwtService;

    /**
     * Обработчик запросов на аутентификацию.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Конфигуратор аутентификации.
     */
    private final AuthConfig authConfig;

    /**
     * Регистрация нового пользователя сервиса ресурсов банка.
     * @param request запрос на регистрацию.
     * @return токен.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        log.info("LOG: AuthenticationService.register = " + request.toString());
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.BANK)
                .enabled(true)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Аутентификация пользователя сервиса ресурсов банка.
     * Создан единственный пользователь/владелец сервиса, логин и пароль которого
     * устанавливается в файле application.yaml.
     * @param request запрос на аутентификацию.
     * @return токен
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = new User(111L, authConfig.getBankUsername(), authConfig.getBankPassword()
                ,Role.BANK, true, "", false);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
