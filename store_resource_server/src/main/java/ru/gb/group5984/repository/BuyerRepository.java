package ru.gb.group5984.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.group5984.model.users.Buyer;
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
