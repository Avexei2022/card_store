package ru.gb.group5984.service.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.repository.UserRepository;

/**
 * Сервис пользователей.
 */
@Service
@Log
@RequiredArgsConstructor
@Getter
public class UserDbServiceImpl implements UserDbService{
    private final UserRepository userRepository;

    /**
     * Поиск пользователя по имени
     * @param username - имя пользователя.
     * @return - пользователь.
     */
    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow();
        log.info(user.toString());
        return userRepository.findUserByUsername(username).orElseThrow();
    }
}
