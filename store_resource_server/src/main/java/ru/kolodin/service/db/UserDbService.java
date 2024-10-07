package ru.kolodin.service.db;

import ru.kolodin.model.characters.CharacterResult;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.users.Buyer;
import ru.kolodin.model.users.StorageUser;
import ru.kolodin.model.users.User;

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
     * Поиск всех покупателей.
     * @return список покупателей.
     */
    List<Buyer> findAllBuyer();

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
