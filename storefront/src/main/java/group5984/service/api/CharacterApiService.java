package group5984.service.api;


import group5984.model.characters.Characters;
import group5984.model.messeges.Message;

/**
 * Интерфейс сервиса получения данных с сайта Rick and Morty.
 * Персонажи являются потенциальными покупателями
 * готовыми зарегистрироваться в магазине.
 */
public interface CharacterApiService {

    /**
     * Получить с сайта Rick and Morty страницу со списком героев.
     * @param page запрашиваемый номер страницы.
     * @return страница со списком героев.
     */
    Characters getPageCharacters(String page);

    /**
     * Зарегистрироваться в качестве покупателя.
     * @param id уникальный номер покупателя.
     */
    Message saveOneCharacterById(Integer id);


}
