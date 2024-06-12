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

    /**
     * Репозиторий пользователей данного ресурса.
     */
    private final UserRepository userRepository;

    /**
     * Поиск пользователя по имени.
     * @param username - имя пользователя.
     * @return - пользователь.
     */
    @Override
    public User findUserByUsername(String username) {
        try {
            if (userRepository.count() == 0L) {
                userRepository.save(createUser());
            }

        } catch (RuntimeException e) {
            userRepository.save(createUser());
        }
        return userRepository.findUserByUsername(username).orElseThrow();

    }

    private User createUser() {
        return new User(0L,"bank"
                , "$2a$10$C4M2moeOYc4MJ/mJCwOP/.GPgAb6641BAT1GqBw/qxVCzXk0X0dfm"
                , Role.ADMIN, true, "bank@mail.com", false);
    }
}
