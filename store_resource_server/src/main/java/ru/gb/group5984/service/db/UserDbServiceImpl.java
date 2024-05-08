package ru.gb.group5984.service.db;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.repository.UserRepository;

@Service
@Log
@RequiredArgsConstructor
@Getter
public class UserDbServiceImpl implements UserDbService{
    private final UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) {
//        userRepository.deleteAll();
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword("admin");
//        admin.setRole("ADMIN");
//        admin.setEnabled(true);
//        userRepository.save(admin);
//        User admin = userRepository.findUserByUsername("admin");
//        admin.setPassword("{noop}admin");
//        userRepository.save(admin);
        return userRepository.findUserByUsername(username).orElseThrow();
    }
}
