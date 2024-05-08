package ru.gb.group5984.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.basket.BasketInfo;
import ru.gb.group5984.model.basket.CardInBasket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsInfo;
import ru.gb.group5984.model.storage.CardsStorage;
import ru.gb.group5984.repository.BasketRepository;
import ru.gb.group5984.repository.CardsRepository;
import ru.gb.group5984.repository.CharacterRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Сервис склада магазина.
 * Работает с базой данных:
 * - товары на складе;
 * - товары в продаже;
 * - товары в корзине покупателя (зарезервированные)
 */
@Service
@RequiredArgsConstructor
@Log
public class CharacterDbServiceImpl implements CharacterDbService{
    private final CharacterRepository characterRepository;
    private final CardsRepository cardsRepository;
    private final BasketRepository basketRepository;

    /**
     * Сохранение единицы товара, закупленного у поставщика,
     * в базе данных товаров на складе.
     * @param characterResult Единица товара
     * Данные действия пользователя выводятся в консоль
     */
    @TrackUserAction
    @Override
    public void saveOneCharacter(CharacterResult characterResult) {
        if (characterResult != null) characterRepository.save(characterResult);
    }

    /**
     * Получить полный список товаров из базы данных товаров на складе
     * @return список товара
     * Данные действия пользователя выводятся в консоль
     */
    @TrackUserAction
    @Override
    public List<CharacterResult> getAllFromStorage() {
        return characterRepository.findAll();
    }

    //TODO Добавить проверку на наличие товара в продаже и в корзине
    /**
     * Удалить единицу товара из базы данных товаров на складе
     * @param id Id Товара
     * Данные действия пользователя выводятся в консоль
     */
    @Override
    @TrackUserAction
    public void deleteById(Integer id) {
        characterRepository.deleteById(id);
    }

    //TODO Реализовать ввод данных от пользователя
    /**
     * Выствить товар на продажу
     * @param id - id товара
     * Устанавливается количество товара и стоимость единицы товара
     * Данные действия пользователя выводятся в консоль
     */
    @Override
    @TrackUserAction
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
     * Получить список товара, выставленного на продажу
     * @return список товара.
     * Данные действия пользователя выводятся в консоль
     */
    @TrackUserAction
    @Override
    public List<CharacterResult> getAllCardFromSale() {
        List<CardsStorage> cardsStorageList = cardsRepository.findAll();
        return cardsStorageList.stream().map(CardsStorage::getCard).toList();
    }

    /**
     * Получить все товары постранично, выставленные на продажу.
     * @param page - запрашиваемая пользователем страница
     * @return список товаров в продаже
     * По умолчанию страница содержит 20 товаров
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров в корзине;
     * - количество страниц;
     * - номера текущей, предыдущей и следующей страниц.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы
     * Данные действия пользователя выводятся в консоль
     */
    @TrackUserAction
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

    //TODO добавить проверку на резервирование покупателем
    /**
     * Удалить товар из списка продаж / убрать с полки.
     * @param id - id товара
     * Данные действия пользователя выводятся в консоль
     */
    @Override
    @TrackUserAction
    public void deleteCardFromSaleById(Integer id) {
        List<CardsStorage> cardsStorageList = cardsRepository.findAll();
        Long cardsStoreId = cardsStorageList.stream()
                .filter(cardsStorage -> Objects.equals(cardsStorage.getCard().getId(), id))
                .toList().getFirst().getId();
        cardsRepository.deleteById(cardsStoreId);
    }

    /**
     * Закупка единицы товара у поставщика и сохранение на складе магазина
     * @param cardsStorage единица товара
     * Данные действия пользователя выводятся в консоль
     */
    @TrackUserAction
    @Override
    public void saveCardStorage(CardsStorage cardsStorage) {
        cardsRepository.save(cardsStorage);
    }

    /**
     * Переместить единицу товара с полки в корзину покупателя
     * @param id - id товара
     *  При наличии товара на полке его количество уменьшается на единицу.
     *  Если товара на полке больше нет, то данная партия товара удаляется из списка.
     *  В корзине сохраняется информация о номере партии товара,
     *  а также дата и время перемещения товара в корзину.
     */
    //TODO доработать ввод количества товара и проверку на валидность
    @Override
    @Transactional
    public void moveCardToBasket(Long id) {
        CardsStorage cardInSale = cardsRepository.findById(id).orElseThrow();
        if (cardInSale.getAmount() > 0) {
            CardInBasket cardInBasket = new CardInBasket();
            cardInBasket.setCard(cardInSale.getCard());
            cardInBasket.setAmount(1);
            cardInSale.setAmount(cardInSale.getAmount() - 1);
            cardInBasket.setPrice(cardInSale.getPrice());
            cardInBasket.setCardsStorageId(cardInSale.getId());
            cardInBasket.setCreated(LocalDateTime.now());
            if (cardInSale.getAmount() < 1) cardsRepository.deleteById(id);
            else cardsRepository.save(cardInSale);
            basketRepository.save(cardInBasket);
        }
    }


    /**
     * Возврат единицы товара из корзины покупателя на полку магазина
     * @param id id - товара в корзине.
     * Проверяется наличие партии данного товара на полке.
     * Если товар из данной партии в наличии на полке, то его количество увеличивается на количество товара в корзине.
     * Если товар из данной партии на полке отсутствует,
     * то восстанавливается партия товара на полке в количестве товара из корзины.
     * Данные действия пользователя выводятся в консоль
     */
    @Override
    @TrackUserAction
    @Transactional
    public void returnCardFromBasketToSale(Long id) {
        CardInBasket cardInBasket = basketRepository.findById(id).orElseThrow();
        log.info("TEST" + cardInBasket.toString());
        Long cardsStorageId = cardInBasket.getCardsStorageId();
        if (cardsRepository.existsById(cardsStorageId)) {
            CardsStorage cardsStorage = cardsRepository.findById(cardsStorageId).orElseThrow();
            cardsStorage.setAmount(cardsStorage.getAmount() + cardInBasket.getAmount());
            cardsRepository.save(cardsStorage);
        } else {
            CardsStorage cardsStorage = new CardsStorage();
            cardsStorage.setId(cardsStorageId);
            cardsStorage.setCard(cardInBasket.getCard());
            cardsStorage.setPrice(cardInBasket.getPrice());
            cardsStorage.setAmount(cardInBasket.getAmount());
            cardsRepository.save(cardsStorage);
        }
        basketRepository.deleteById(id);
    }

    /**
     * Получить все товары постранично, зарезервированные в корзине.
     * @param page - запрашиваемая пользователем страница
     * @return список товаров в корзине
     * По умолчанию страница содержит 20 товаров
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров в корзине;
     * - количество страниц;
     * - номера текущей, предыдущей и следующей страниц;
     * -общая сумма товара в корзине.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы
     * Данные действия пользователя выводятся в консоль
     */
    @TrackUserAction
    @Override
    public Basket getAllFromBasket(Integer page) {
        page = page - 1;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        Page<CardInBasket> cardInBasketPage = basketRepository.findAll(pageable);
        Basket basket = new Basket();
        BasketInfo basketInfo = new BasketInfo();
        basketInfo.setCount(cardInBasketPage.getTotalElements());
        basketInfo.setPages(cardInBasketPage.getTotalPages());
        if (cardInBasketPage.hasPrevious())
            basketInfo.setPrev(cardInBasketPage.getNumber());
        else basketInfo.setPrev(cardInBasketPage.getTotalPages());
        if (cardInBasketPage.hasNext())
            basketInfo.setNext(cardInBasketPage.getNumber() + 2);
        else basketInfo.setNext(1);
        basketInfo.setCurrent(cardInBasketPage.getNumber() + 1);
        basketInfo.setTotalPrice(basketRepository.findAll()
                .stream()
                .map(CardInBasket::getPrice)
                .reduce(Double::sum)
                .orElse(0.0));
        basket.setCardInBasketList(cardInBasketPage.toList());
        basket.setInfo(basketInfo);
        return basket;
    }

    /**
     * Получить общую сумму товаров в корзине
     * @return сумма товаров в корзине
     */
    @Override
    public Double getTotalPriceFromBasket() {
        return basketRepository.findAll().stream()
                .map(CardInBasket::getPrice)
                .reduce(Double::sum)
                .orElse(0.0);
    }

    /**
     * Удалить все товары из корзины
     */
    @Override
    public void deleteAllFromBasket() {
        basketRepository.deleteAll();
    }


}
