package group5984.service;

import group5984.auth.AuthenticationService;
import group5984.model.users.ThisUserDetails;
import group5984.model.users.User;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Log

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthenticationService authenticationService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = authenticationService.getUserByUserName(username);
            ThisUserDetails thisUserDetails = new ThisUserDetails(user);
            log.info("LOG: UserDetailsServiceImpl.loadUserByUsername.thisUserDetails = " + thisUserDetails);
            return thisUserDetails;
        } catch (NullPointerException e) {
            throw  new UsernameNotFoundException("Пользователь не найден");
        }
    }
}
