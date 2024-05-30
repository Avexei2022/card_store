package ru.gb.group5984.service.db;

import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.users.Buyer;
import ru.gb.group5984.model.users.StorageUser;
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

    /**
     * Поиск всех пользователей.
     * @return список пользователей.
     */
    List<User> findAllUser();

    /**
     * Регистрация нового покупателя.
     * @param characterResult - покупатель.
     * @return сообщение о результате регистрации.
     */
    Message registerNewBuyer(CharacterResult characterResult);

    /**
     * Поиск пользователя веб-сервиса склада магазина по имени/логину
     * @param username - имя/логин пользователя.
     * @return - пользователь.
     */
    StorageUser findStorageUserByUsername(String username);

    /**
     * Поиск покупателя - пользователя веб-сервиса магазина по имени/логину
     * @param username - имя/логин пользователя.
     * @return - пользователь.
     */
    Buyer findBuyerByUsername(String username);
}
