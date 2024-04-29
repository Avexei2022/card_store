package ru.gb.group5984.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.group5984.model.basket.CardInBasket;

@Repository
public interface BasketRepository extends JpaRepository<CardInBasket, Long> {
}
