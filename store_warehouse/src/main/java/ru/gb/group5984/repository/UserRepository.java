package ru.gb.group5984.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByUsername(String username);
}
