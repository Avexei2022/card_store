package kolodin.service.api;


import kolodin.model.characters.Characters;
import kolodin.model.messeges.Message;

/**
 * Интерфейс сервиса получения данных с сайта Rick and Morty.
 * Персонажи являются потенциальными покупателями,
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
     * @return сообщение о результате.
     */
    Message saveOneCharacterById(Integer id);


}
