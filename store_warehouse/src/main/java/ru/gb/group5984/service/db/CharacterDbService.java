package ru.gb.group5984.service.db;

import ru.gb.group5984.model.characters.CharacterResult;

import java.util.List;

/**
 * Интерфейс сервиса взаимодействия с базой данных
 */
public interface CharacterDbService {
    /**
     * Метод сохранения выбранной пользователем карточки в базе данных
     * @param characterResult карточка героя
     */
    public void saveOneCharacter(CharacterResult characterResult);

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
