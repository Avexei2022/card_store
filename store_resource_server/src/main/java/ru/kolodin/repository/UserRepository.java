package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodin.model.users.User;
import java.util.Optional;

/**
 * Репозиторий пользователей сервиса ресурсов магазина.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Получить пользователя по имени/логину.
     * @param username имя/логин пользователя.
     * @return пользователь.
     */
    public Optional<User> findUserByUsername(String username);
}
