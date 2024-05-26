package ru.gb.group5984.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.basket.CardInBasket;

import java.util.List;

/**
 * Репозиторий зарезервированных товаров - корзина покупателей
 */
@Repository
public interface BasketRepository extends JpaRepository<CardInBasket, Long> {
    public Page<CardInBasket> findAllByUser_id(Long userId, Pageable pageable);
    public List<CardInBasket> findAllByUser_id(Long userId);
    public void deleteAllByUser_id(Long userId);
}
