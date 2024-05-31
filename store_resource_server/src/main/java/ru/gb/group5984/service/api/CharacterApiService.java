package ru.gb.group5984.service.api;


import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;

/**
 * Интерфейс сервиса получения данных с сайта Rick and Morty.
 */
public interface CharacterApiService {

    /**
     * Получить с сайта Rick and Morty страницу со списком героев.
     * @param page номер страницы.
     * @return Страница со списком героев.
     */
    Characters getPageCharacters(String page);

    /**
     * "Закупка" единицы товара на сервисе Rick and Morty и сохранение в базе данных склада.
     * @param id номер карточки.
     */
    void saveOneCharacterById(Integer id);

    /**
     * Оплата товара из корзины покупателя через банк.
     * @param userName имя/логин покупателя.
     * @return сообщение о результате платежа.
     */
    Message basketPay(String userName);

    /**
     * Зарегистрировать нового пользователя.
     * @param id уникальный номер персонажа.
     * @return сообщение о результате регистрации.
     */
    Message registerNewUser(Integer id);
}
