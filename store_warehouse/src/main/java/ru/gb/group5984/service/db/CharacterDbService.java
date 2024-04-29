package ru.gb.group5984.service.db;

import org.springframework.data.domain.Page;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsStorage;

import java.util.List;

/**
 * Интерфейс сервиса взаимодействия с базой данных
 */
public interface CharacterDbService {
    /**
     * Метод сохранения выбранной пользователем карточки в базе данных
     * @param characterResult карточка героя
     */
    void saveOneCharacter(CharacterResult characterResult);

    /**
     * Получить все карточки героев из базы данных
     * @return список карточек героев
     */
    List<CharacterResult> getAllFromStorage();

    /**
     * Удалить карточку героя из базы данных
     * @param id Id героя
     */
    void deleteById(Integer id);

    void saveOneCardById(Integer id);

    List<CharacterResult> getAllCardFromSale();

    Cards getAllCardsStorageFromSale(Integer page);

    void deleteCardFromSaleById(Integer id);

    void saveCardStorage(CardsStorage cardsStorage);
    void moveCardToBasket(Long ig);
    void returnCardFromBasketToSale(Long id);

    Basket getAllFromBasket(Integer page);
}
