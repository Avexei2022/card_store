package ru.gb.group5984.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.model.users.ThisUserDetails;
import ru.gb.group5984.service.api.ServerApiService;

@Service
@Log
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ServerApiService serverApiService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
        try {
            User user = serverApiService.getUserByUserName(username);
            return new ThisUserDetails(user);
        } catch (NullPointerException e) {
            throw  new UsernameNotFoundException("Пользователь не найден");
        }
    }
}
