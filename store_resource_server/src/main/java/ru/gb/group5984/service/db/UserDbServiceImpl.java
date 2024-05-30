package ru.gb.group5984.service.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import ru.gb.group5984.auth.AuthenticationService;
import ru.gb.group5984.auth.RegisterRequest;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.messeges.Message;

import ru.gb.group5984.model.users.Buyer;
import ru.gb.group5984.model.users.Role;
import ru.gb.group5984.model.users.StorageUser;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.repository.BuyerRepository;
import ru.gb.group5984.repository.StorageUserRepository;
import ru.gb.group5984.repository.UserRepository;

import java.util.List;

/**
 * Сервис пользователей.
 */
@Service
@Log
@RequiredArgsConstructor
@Getter
public class UserDbServiceImpl implements UserDbService{

    /**
     * Репозиторий пользователей сервиса ресурсов магазина.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий пользователей веб-сервиса склада магазина.
     */
    private final StorageUserRepository storageUserRepository;

    /**
     * Репозиторий покупателей - пользователей веб-сервиса магазина.
     */
    private final BuyerRepository buyerRepository;

    /**
     * Сервис аутентификации.
     */
    private final AuthenticationService authenticationService;

    /**
     * Поиск пользователя по имени
     * @param username - имя пользователя.
     * @return - пользователь.
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow();
    }

    /**
     * Найти всех пользователей.
     * @return список пользователей.
     */
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * Зарегистрировать нового покупателя - пользователя веб-сервиса магазина.
     * @param characterResult - персонаж с ресурса Rick and Morty
     */
    @Override
    public Message registerNewBuyer(CharacterResult characterResult) {
        Message message = new Message();
        String newUserName = characterResult.getName()
                .replaceAll(" ", "_");
        if (buyerRepository.findUserByUsername(newUserName).isPresent()) {
            message.setMessage("Пользователь с таким именем уже зарегистрирован");
        } else {
            try {
                authenticationService.buyerRegister(new RegisterRequest(newUserName
                        , newUserName
                        , newUserName + "@mail.com"));
                message.setMessage("Вы успешно зарегистрированы!\nЗапомните логин и пароль!\nЛогин: "
                + newUserName + "\nПароль: " + newUserName);
            } catch (RuntimeException e) {
                message.setMessage("Ресурс временно недоступен. Зайдите позже.");
            }
        }
        return message;
    }

    /**
     * Поиск пользователя веб-сервиса склада магазина по имени/логину.
     * @param username - имя/логин пользователя.
     * @return - пользователь.
     */
    @Override
    public StorageUser findStorageUserByUsername(String username) {
        log.info("Log: UserDbServiceImpl.findStorageUserByUsername.username = " + username);
        log.info("Log: UserDbServiceImpl.findStorageUserByUsername.storageUserRepository.count() = "
                + storageUserRepository.count());
        if (storageUserRepository.count()<1) {
            storageUserRepository.save(new StorageUser(0L,"admin"
                    , "$2a$10$tRhzQK0FTSTzjy7T4uQsZegrrtA8vlWILG75ohkh09rGcK5jCr6YC"
                    , Role.ADMIN, true, "admin@mail.com", false));
        }
        return storageUserRepository.findUserByUsername(username).orElseThrow();
    }

    /**
     * Поиск покупателя - пользователя веб-сервиса магазина по имени/логину.
     * @param username - имя/логин пользователя.
     * @return - пользователь.
     */
    @Override
    public Buyer findBuyerByUsername(String username) {
        if (buyerRepository.count()<1) {
            buyerRepository.save(new Buyer(0L,"user"
                    , "$2a$10$OO6WBhYkkQSa7RLmzA9VyeOH2CzUB2yO6bLJFNEjERBAg.P6Gk2Rq"
                    , Role.USER, true, "user@mail.com", false));
        }
        return buyerRepository.findUserByUsername(username).orElseThrow();
    }

//    /**
//     * Временная замена репозитория в случае необходимости тестов.
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
