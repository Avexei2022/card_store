package ru.kolodin.service.db;

import ru.kolodin.model.users.User;

/**
 * Интерфейс сервиса пользователей.
 */
public interface UserDbService {

    /**
     * Поиск пользователя по имени.
     * @param username - имя пользователя.
     * @return - пользователь.
     */
    User findUserByUsername(String username);
}
