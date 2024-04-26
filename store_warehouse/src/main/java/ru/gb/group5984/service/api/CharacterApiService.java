package ru.gb.group5984.service.api;

import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;

import java.util.List;

/**
 * Интерфейс сервиса взаимодействия с сайтом Rick and Morty
 */
public interface CharacterApiService {
    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param url ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком героев
     */
    public Characters getAllCharacters(String url);

    /**
     * Метод сохранения выбранной прльзователем карточки в базе данных
     * @param url ссылка
     */
    public void saveOneCharacterById(String url);


}
