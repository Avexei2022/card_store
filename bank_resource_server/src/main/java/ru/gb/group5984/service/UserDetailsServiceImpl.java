package ru.gb.group5984.service;

import lombok.extern.java.Log;


//@Service
@Log
public class UserDetailsServiceImpl {// implements UserDetailsService {
//    @Autowired
//    private UserDbService userDbService;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        userRepository.deleteAll();
////        User admin = new User();
////        admin.setUsername("admin");
////        admin.setPassword("admin");
////        admin.setRole("ADMIN");
////        admin.setEnabled(true);
////        userRepository.save(admin);
////        User admin = userRepository.findUserByUsername("admin");
////        admin.setPassword("{noop}admin");
////        userRepository.save(admin);
//        try {
//            return userDbService.findUserByUsername(username);
////            return new ThisUserDetails(user);
//        } catch (NullPointerException e) {
//            throw  new UsernameNotFoundException("Could not find user");
//        }
//    }
}
