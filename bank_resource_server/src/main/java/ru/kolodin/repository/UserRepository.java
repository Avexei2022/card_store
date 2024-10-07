package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.users.User;

import java.util.Optional;

/**
 * Репозиторий пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по имени.
     * @param username имя.
     * @return пользователь.
     */
    public Optional<User> findUserByUsername(String username);
}
