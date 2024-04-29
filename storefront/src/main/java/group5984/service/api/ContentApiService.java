package group5984.service.api;

import group5984.model.basket.Basket;
import group5984.model.storage.Cards;

/**
 * Интерфейс сервиса взаимодействия с сайтом Rick and Morty
 */
public interface ContentApiService {
    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param way ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком героев
     */
    Cards getAllFromSale(String way);
    Basket getAllFromBasket(String way);
    void addToBasketById(Integer id);

    void deleteFromBasketById(Integer id);
}
