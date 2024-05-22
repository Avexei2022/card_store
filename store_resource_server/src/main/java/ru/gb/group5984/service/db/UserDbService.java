package ru.gb.group5984.service.db;

import ru.gb.group5984.model.users.User;

import java.util.List;

/**
 * Интерфейс сервиса пользователей.
 */
public interface UserDbService {

    /**
     * Поиск пользователя по имени
     * @param username - имя пользователя.
     * @return - пользователь.
     */
    User findUserByUsername(String username);

    List<User> findAllUser();
}
