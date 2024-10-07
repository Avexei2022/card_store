package ru.kolodin.service.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import ru.kolodin.auth.AuthenticationService;
import ru.kolodin.auth.RegisterRequest;
import ru.kolodin.model.characters.CharacterResult;
import ru.kolodin.model.messeges.Message;

import ru.kolodin.model.users.Buyer;
import ru.kolodin.model.users.Role;
import ru.kolodin.model.users.StorageUser;
import ru.kolodin.model.users.User;
import ru.kolodin.repository.BuyerRepository;
import ru.kolodin.repository.StorageUserRepository;
import ru.kolodin.repository.UserRepository;

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
     * Поиск всех покупателей.
     * @return список покупателей.
     */
    @Override
    public List<Buyer> findAllBuyer() {
        return buyerRepository.findAll();
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

        try {
            if (storageUserRepository.count() == 0L) {
                storageUserRepository.save(createStorageUser());
            }

        } catch (RuntimeException e) {
            storageUserRepository.save(createStorageUser());
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
        try {
            if (buyerRepository.count() == 0L) {
                buyerRepository.save(createBuyer());
            }
        } catch (RuntimeException e) {
            buyerRepository.save(createBuyer());
        }
        return buyerRepository.findUserByUsername(username).orElseThrow();
    }

    private StorageUser createStorageUser() {
        return new StorageUser(0L,"admin"
                , "$2a$10$tRhzQK0FTSTzjy7T4uQsZegrrtA8vlWILG75ohkh09rGcK5jCr6YC"
                , Role.ADMIN, true, "admin@mail.com", false);
    }

    private Buyer createBuyer() {
        return new Buyer(0L,"user"
                , "$2a$10$OO6WBhYkkQSa7RLmzA9VyeOH2CzUB2yO6bLJFNEjERBAg.P6Gk2Rq"
                , Role.USER, true, "user@mail.com", false);
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
