package ru.kolodin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolodin.model.users.Buyer;
import java.util.Optional;


/**
 * Репозиторий покупателей - пользователей веб-ресурса магазина.
 *
 */
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    /**
     * Получить пользователя по имени/логину.
     * @param username имя/логин пользователя.
     * @return пользователь.
     */
    public Optional<Buyer> findUserByUsername(String username);
}
