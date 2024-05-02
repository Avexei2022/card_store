package ru.gb.group5984.service.api;


import ru.gb.group5984.model.characters.Characters;

/**
 * Интерфейс сервиса получения данных с сайта Rick and Morty
 */
public interface CharacterApiService {

    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param url ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком героев
     */
    Characters getAllCharacters(String url);

    /**
     * "Закупка" единицы товара на сервисе Rick and Morty и сохранение в базе данных склада
     * @param url ссылка на сервис Rick and Morty
     */
    void saveOneCharacterById(String url);

    /**
     * Оплата товара из корзины покупателя через банк.
     */
    void basketPay();
}
