package ru.gb.group5984.service.api;


import ru.gb.group5984.model.characters.Characters;

/**
 * Интерфейс сервиса получения данных с сайта Rick and Morty
 */
public interface CharacterApiService {

    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param page запрашиваемый номер страницы
     * @return Страница со списком героев
     */
    Characters getAllCharacters(String page);

    /**
     * "Закупка" единицы товара на сервисе Rick and Morty и сохранение в базе данных склада
     * @param id номер товара
     */
    void saveOneCharacterById(Integer id);


}
