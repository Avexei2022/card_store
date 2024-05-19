package ru.gb.group5984.service.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.gb.group5984.model.users.Role;
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

    //TODO После сдачи домашних работ вернуться к БД
    /**
     * Поиск пользователя по имени
     * @param username - имя пользователя.
     * @return - пользователь.
     * На период сдачи домашних работ стоит заглушка репозитория пользователей
     */
    @Override
    public User findUserByUsername(String username) {
        //Временно деактивировано
//        User user = userRepository.findUserByUsername(username).orElseThrow();
//        log.info(user.toString());

        return switch (username) {
            case "admin" -> new User(1L, "admin", "$2a$10$tRhzQK0FTSTzjy7T4uQsZegrrtA8vlWILG75ohkh09rGcK5jCr6YC"
                    , Role.Admin, true);
            case "user" -> new User(2L, "user", "$2a$10$OO6WBhYkkQSa7RLmzA9VyeOH2CzUB2yO6bLJFNEjERBAg.P6Gk2Rq"
                    , Role.User, true);
            default -> throw new UsernameNotFoundException("Пользователь не найден");
        };
    }


}
