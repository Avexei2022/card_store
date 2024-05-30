package ru.gb.group5984.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.group5984.auth.AuthenticationService;
import ru.gb.group5984.model.users.ThisUserDetails;
import ru.gb.group5984.model.users.User;

/**
 * Сервис проверки подлинности учетных данных пользователя.
 * Используется DaoAuthenticationProvider для получения имени пользователя, пароля
 * и других атрибутов для аутентификации с использованием имени пользователя и пароля.
 */
@Service
@Log
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Сервис аутентификации.
     */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Получить пользователя по имени/логину.
     * @param username имя/логин пользователя.
     * @return Аутентификационные данные пользователя.
     * @throws UsernameNotFoundException Исключение в случае отсутствия пользователя.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = authenticationService.getUserByUserName(username);
            ThisUserDetails thisUserDetails = new ThisUserDetails(user);
            log.info("LOG: UserDetailsServiceImpl.loadUserByUsername.thisUserDetails = " + thisUserDetails);
            return thisUserDetails;
        } catch (NullPointerException e) {
            throw  new UsernameNotFoundException("Пользователь не найден");
        }
    }
}
