package ru.gb.group5984.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.repository.CharacterRepository;

import java.util.List;

/**
 * Сервис героев
 * Работает с базой данных
 */
@Service
@RequiredArgsConstructor
@Log
public class CharacterDbServiceImpl implements CharacterDbService{
    private final CharacterRepository characterRepository;
    /**
     * Метод сохранения выбранной пользователем карточки в базе данных
     * @param characterResult карточка героя
     */
    @Override
    public void saveOneCharacter(CharacterResult characterResult) {
        if (characterResult != null) characterRepository.save(characterResult);
    }

    /**
     * Получить все карточки героев из базы данных
     * @return список карточек героев
     */
    @Override
    public List<CharacterResult> getAllFromBasket() {
        return characterRepository.findAll();
    }

    /**
     * Удалить карточку героя из базы данных
     * @param id Id героя
     */
    @Override
    public void deleteById(Integer id) {
        characterRepository.deleteById(id);
    }
}
