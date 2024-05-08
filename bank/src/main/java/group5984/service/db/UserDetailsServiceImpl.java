package group5984.service.db;

import group5984.model.users.ThisUserDetails;
import group5984.model.users.User;
import group5984.repository.UserRepository;
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
    private UserRepository userRepository;
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
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw  new UsernameNotFoundException("Could not find user");
        }
        return new ThisUserDetails(user);
    }
}
