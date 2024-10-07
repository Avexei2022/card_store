package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolodin.model.users.StorageUser;
import java.util.Optional;

/**
 * Репозиторий пользователей веб-ресурса склада магазина.
 */
public interface StorageUserRepository extends JpaRepository<StorageUser, Long> {

    /**
     * Получить пользователя по имени/логину.
     * @param username имя/логин пользователя.
     * @return пользователь.
     */
    public Optional<StorageUser> findUserByUsername(String username);
}
