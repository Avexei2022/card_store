package ru.gb.group5984.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.basket.CardInBasket;

import java.util.List;

/**
 * Репозиторий зарезервированных товаров - корзина покупателей.
 */
@Repository
public interface BasketRepository extends JpaRepository<CardInBasket, Long> {
    /**
     * Получить список товаров в корзине покупателя.
     * @param userId уникальный номер покупателя.
     * @param pageable номер страницы в списке товаров.
     * @return страница списка товаров в корзине покупателя.
     */
    public Page<CardInBasket> findAllByUser_id(Long userId, Pageable pageable);

    /**
     * Получить список товаров в корзине покупателя.
     * @param userId уникальный номер покупателя.
     * @return список товаров в корзине покупателя.
     */
    public List<CardInBasket> findAllByUser_id(Long userId);

    /**
     * Удалить все товары из корзины покупателя.
     * @param userId уникальный номер покупателя.
     */
    public void deleteAllByUser_id(Long userId);
}
