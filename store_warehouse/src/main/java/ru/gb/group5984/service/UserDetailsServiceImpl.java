package ru.gb.group5984.service;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gb.group5984.model.users.ThisUserDetails;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.service.api.ServerApiService;


@Log
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ServerApiService serverApiService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = serverApiService.getUserByUserName(username);
            ThisUserDetails thisUserDetails = new ThisUserDetails(user);
            log.info("LOG: UserDetailsServiceImpl.loadUserByUsername.thisUserDetails = " + thisUserDetails);
            return thisUserDetails;
        } catch (NullPointerException e) {
            throw  new UsernameNotFoundException("Пользователь не найден");
        }
//        if (serverApiService.getUserByUserName(username) != null)
//            return serverApiService.getUserByUserName(username);
//        else throw  new UsernameNotFoundException("Пользователь не найден");
    }
}
