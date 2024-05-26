package ru.gb.group5984.service.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.gb.group5984.model.users.Role;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

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
        return userRepository.findUserByUsername(username).orElseThrow();
        // Данный блок используется в случае отключение основного репозитория при переходе на Н2
//        return switch (username) {
//            case "admin" -> userList.get(0);
//            case "user" -> userList.get(1);
//            default -> throw new UsernameNotFoundException("Пользователь не найден");
//        };
    }

    /**
     * Найти всех пользователей.
     * @return список пользователей.
     */
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

//    /**
//     * Временная замена репозиторию.
//     * @return список пользователей.
//     */
//    private List<User> createUsers() {
//        List<User> userList = new ArrayList<>();
//        userList.add(new User(1L, "admin", "$2a$10$tRhzQK0FTSTzjy7T4uQsZegrrtA8vlWILG75ohkh09rGcK5jCr6YC"
//                , Role.Admin, true, "admin@gmail.com", false));
//        userList.add(new User(2L, "user", "$2a$10$OO6WBhYkkQSa7RLmzA9VyeOH2CzUB2yO6bLJFNEjERBAg.P6Gk2Rq"
//                , Role.User, true, "user@gmail.com", true));
//        return userList;
//    }

}
