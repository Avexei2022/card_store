package ru.gb.group5984.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsInfo;
import ru.gb.group5984.model.storage.CardsStorage;
import ru.gb.group5984.repository.CardsRepository;
import ru.gb.group5984.repository.CharacterRepository;

import java.util.List;
import java.util.Objects;

/**
 * Сервис героев
 * Работает с базой данных
 */
@Service
@RequiredArgsConstructor
@Log
public class CharacterDbServiceImpl implements CharacterDbService{
    private final CharacterRepository characterRepository;
    private final CardsRepository cardsRepository;
    /**
     * Метод сохранения выбранной карточки в базе данных
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
    public List<CharacterResult> getAllFromStorage() {
        return characterRepository.findAll();
    }

    /**
     * Удалить карточку героя из базы данных склада
     * @param id Id героя
     */
    @Override
    public void deleteById(Integer id) {
        characterRepository.deleteById(id);
    }

    /**
     * Метод занесения картоки на складе в список продаж
     * @param id - id карточки героя
     * Вначале проверяется наличие карточки на складе
     * Создается новая запись в списке продаж в которую заносится карточка
     * Далее запись сохраняется в базе данных.
     * Остальные поля записи заполняются в сервисе продаж.
     */
    @Override
    public void saveOneCardById(Integer id) {
        CharacterResult characterResult = characterRepository.findById(id).orElse(null);
        if (characterResult != null) {
            CardsStorage cardsStorage = new CardsStorage();
            cardsStorage.setCard(characterResult);
            cardsStorage.setAmount(100);
            cardsStorage.setPrice(19.99);
            cardsRepository.save(cardsStorage);
        }
    }


    /**
     * Получить все карточки героев из списка продаж
     * @return список карточек героев
     */
    @Override
    public List<CharacterResult> getAllCardFromSale() {
        List<CardsStorage> cardsStorageList = cardsRepository.findAll();
        return cardsStorageList.stream().map(CardsStorage::getCard).toList();
    }

    @Override
    public Cards getAllCardsStorageFromSale(Integer page) {
        page = page - 1;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        Page<CardsStorage> cardsStoragePage = cardsRepository.findAll(pageable);
        Cards cards = new Cards();
        CardsInfo cardsInfo = new CardsInfo();
        cardsInfo.setCount(cardsStoragePage.getTotalElements());
        cardsInfo.setPages(cardsStoragePage.getTotalPages());
        if (cardsStoragePage.hasPrevious())
            cardsInfo.setPrev(cardsStoragePage.getNumber());
        else cardsInfo.setPrev(cardsStoragePage.getTotalPages());
        if (cardsStoragePage.hasNext())
            cardsInfo.setNext(cardsStoragePage.getNumber() + 2);
        else cardsInfo.setNext(1);
        cardsInfo.setCurrent(cardsStoragePage.getNumber() + 1);
        cards.setCardsStorageList(cardsStoragePage.toList());
        cards.setInfo(cardsInfo);
        return cards;
    }

    @Override
    public void deleteCardFromSaleById(Integer id) {
        //TODO добавить проверку на резервирование покупателем
        List<CardsStorage> cardsStorageList = cardsRepository.findAll();
        Long cardsStoreId = cardsStorageList.stream()
                .filter(cardsStorage -> Objects.equals(cardsStorage.getCard().getId(), id))
                .toList().getFirst().getId();
        cardsRepository.deleteById(cardsStoreId);
    }

    @Override
    public void saveCardStorage(CardsStorage cardsStorage) {
        cardsRepository.save(cardsStorage);
    }
}
