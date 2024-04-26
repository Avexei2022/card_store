package ru.gb.group5984.service.api;

import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;

import java.util.List;

/**
 * Интерфей сервиса героев
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

    /**
     * Получить все карточки героев из базы данных
     * @return список карточек героев
     */
    public List<CharacterResult> getAllFromBasket();

    /**
     * Удалить карточку героя из базы данных
     * @param id Id героя
     */
    public void deleteById(Integer id);
}
