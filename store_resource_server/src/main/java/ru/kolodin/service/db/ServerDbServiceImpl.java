package ru.kolodin.service.db;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kolodin.aspect.TrackUserAction;
import ru.kolodin.model.basket.Basket;
import ru.kolodin.model.basket.BasketInfo;
import ru.kolodin.model.basket.CardInBasket;
import ru.kolodin.model.characters.CharacterInfo;
import ru.kolodin.model.characters.CharacterResult;
import ru.kolodin.model.characters.Characters;
import ru.kolodin.model.exceptions.ExcessAmountException;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.observer.NewProductEvent;
import ru.kolodin.model.storage.Cards;
import ru.kolodin.model.storage.CardsInfo;
import ru.kolodin.model.storage.CardsStorage;
import ru.kolodin.model.users.Buyer;
import ru.kolodin.repository.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Сервис склада магазина.
 */
@Service
@RequiredArgsConstructor
@Log
public class ServerDbServiceImpl implements ServerDbService {
    /**
     * Репозиторий товаров на складе.
     */
    private final CharacterRepository characterRepository;

    /**
     * Репозиторий товаров в продаже.
     */
    private final CardsRepository cardsRepository;

    /**
     * Репозиторий товаров в корзинах покупателей.
     */
    private final BasketRepository basketRepository;

    /**
     * Репозиторий пользователей сервиса ресурсов магазина.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий покупателей - пользователей веб-сервиса магазина.
     */
    private final BuyerRepository buyerRepository;

    /**
     * Репозиторий пользователей веб-сервиса склада магазина.
     */
    private final StorageUserRepository storageUserRepository;

    /**
     * Уведомитель о событиях.
     */
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Сохранить единицу товара, закупленного у поставщика,
     * в базе данных товаров на складе.
     * @param characterResult Единица товара.
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     */
    @TrackUserAction
    @Override
    public void saveOneCharacter(CharacterResult characterResult) {
        if (characterResult != null) characterRepository.save(characterResult);
    }

    /**
     * Получить все товары постранично, хранящиеся на складе.
     * По умолчанию страница содержит 20 товаров.
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров на складе;
     * - количество страниц;
     * - номера предыдущей и следующей страниц.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы.
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param page - номер страницы.
     * @return список товаров на складе.
     */
    @TrackUserAction
    @Override
    public Characters getPageCharactersFromStorage(Integer page) {
        page = page - 1;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        Page<CharacterResult> characterResultPage = characterRepository.findAll(pageable);
        Characters characters = new Characters();
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setCount((int) characterResultPage.getTotalElements());
        characterInfo.setPages(characterResultPage.getTotalPages());
        if (characterResultPage.hasPrevious())
            characterInfo.setPrev(String.valueOf(characterResultPage.getNumber()));
        else characterInfo.setPrev(String.valueOf(characterResultPage.getTotalPages()));
        if (characterResultPage.hasNext())
            characterInfo.setNext(String.valueOf(characterResultPage.getNumber() + 2));
        else characterInfo.setNext("1");
        characters.setResults(characterResultPage.toList());
        characters.setInfo(characterInfo);
        return characters;
    }

    /**
     * Удалить единицу товара из базы данных товаров на складе.
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param id Id Товара.
     */
    @Override
    @TrackUserAction
    public Message deleteFromStorageById(Integer id) {
        boolean isNotInBasket = basketRepository.findAll()
                                        .stream()
                                        .filter(cardInBasket -> cardInBasket.getCard().getId().equals(id))
                                        .toList().isEmpty();
        boolean isNotInSale = cardsRepository.findAll()
                                        .stream()
                                        .filter(cardsStorage -> cardsStorage.getCard().getId().equals(id))
                                        .toList().isEmpty();
        Message message = new Message();
        if (isNotInBasket && isNotInSale) {
            try {
                characterRepository.deleteById(id);
                message.setMessage("none");
            } catch (RuntimeException e) {
                message.setMessage("Удаление товара невозможно: " + e.getMessage());
            }
        } else message.setMessage("Удаление товара невозможно: " +
                "Товар выставлен на продажу и может быть зарезервирован." +
                " Для удаления товара со склада снимите его с продажи.");
        return message;
    }

    //TODO Реализовать ввод данных от пользователя
    /**
     * Выствить товар на продажу.
     * Устанавливается количество товара и стоимость единицы товара
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param id - id товара.
     */
    @Override
    @TrackUserAction
    public void saveOneCardById(Integer id) {
        CharacterResult characterResult = characterRepository.findById(id).orElse(null);
        if (characterResult != null) {
            CardsStorage cardsStorage = new CardsStorage();
            cardsStorage.setCard(characterResult);
            cardsStorage.setAmount(100);
            cardsStorage.setPrice(BigDecimal.valueOf(19.99));
            cardsRepository.save(cardsStorage);
            publisher.publishEvent(new NewProductEvent(this, cardsStorage));
        }
    }

    /**
     * Получить все товары постранично, выставленные на продажу.
     * По умолчанию страница содержит 20 товаров.
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров в продаже;
     * - количество страниц в списке;
     * - номера текущей, предыдущей и следующей страниц.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы.
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param page - номер страницы.
     * @return список товаров в продаже.
     */
    @TrackUserAction
    @Override
    public Cards getPageCardsStorageFromSale(Integer page) {
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
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param id уникальный номер товара.
     */
    @Override
    @TrackUserAction
    public void deleteCardFromSaleById(Long id) {
        cardsRepository.deleteById(id);
    }

    /**
     * Закупка единицы товара у поставщика и сохранение на складе магазина.
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param cardsStorage единица товара
     */
    @TrackUserAction
    @Override
    public void saveCardStorage(CardsStorage cardsStorage) {
        cardsRepository.save(cardsStorage);
    }

    /**
     * Переместить единицу товара с полки в корзину покупателя.
     *  При наличии товара на полке его количество уменьшается на единицу.
     *  Если товара на полке больше нет, то данная партия товара удаляется из списка.
     *  В корзине сохраняется информация о номере партии товара,
     *  а также дата и время перемещения товара в корзину.
     * @param cardId - уникальный номер товара в продаже.
     * @param userName - имя/логин покупателя.
     */
    //TODO доработать ввод количества товара и проверку на валидность
    @Override
    @Transactional()
    public void moveCardToBasket(Long cardId, String userName)  throws ExcessAmountException, NoSuchElementException {
        CardsStorage cardInSale;
        Buyer buyer;
        try {
            cardInSale = cardsRepository.findById(cardId).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Товар на складе не найден.");
        }
        try {
            buyer = buyerRepository.findUserByUsername(userName).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Покупатель не найден.");
        }
        if (cardInSale.getAmount() > 0) {
            CardInBasket cardInBasket = new CardInBasket();
            cardInBasket.setCard(cardInSale.getCard());
            cardInBasket.setAmount(1);
            cardInSale.setAmount(cardInSale.getAmount() - 1);
            cardInBasket.setPrice(cardInSale.getPrice());
            cardInBasket.setCardsStorageId(cardInSale.getId());
            cardInBasket.setCreated(LocalDate.now());
            cardInBasket.setUser(buyer);
            if (cardInSale.getAmount() < 1) cardsRepository.deleteById(cardId);
            else cardsRepository.save(cardInSale);
            log.info("LOG: ServerDbServiceImpl.moveCardToBasket.cardInBasket = " + cardInBasket);
            basketRepository.save(cardInBasket);
        } else throw new ExcessAmountException("Отрицательный баланс товара на складе");
    }


    /**
     * Возврат единицы товара из корзины покупателя на полку магазина.
     * Проверяется наличие партии данного товара на полке.
     * Если товар из данной партии в наличии на полке, то его количество увеличивается на количество товара в корзине.
     * Если товар из данной партии на полке отсутствует,
     * то восстанавливается партия товара на полке в количестве товара из корзины.
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param id уникальный номер товара в корзине.
     */
    @Override
    @TrackUserAction
    @Transactional
    public void returnCardFromBasketToSale(Long id) {
        CardInBasket cardInBasket = basketRepository.findById(id).orElseThrow();
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
     * Получить все товары постранично, зарезервированные покупателем в корзине.
     * По умолчанию страница содержит 20 товаров
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров в корзине;
     * - количество страниц;
     * - номера текущей, предыдущей и следующей страниц;
     * -общая сумма товара в корзине.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы
     * При вызове метода в консоль выводится наименование метода, его аргументы и время исполнения.
     * @param page - запрашиваемая пользователем страница
     * @param userName - имя/логин покупателя.
     * @return список товаров в корзине
     */
    @TrackUserAction
    @Override
    public Basket getPageFromBasket(Integer page, String userName) {
        Buyer buyer = buyerRepository.findUserByUsername(userName).orElseThrow();
        page = page - 1;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        Page<CardInBasket> cardInBasketPage = basketRepository.findAllByUser_id(buyer.getId(), pageable);
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
        basketInfo.setTotalPrice(basketRepository.findAllByUser_id(buyer.getId())
                .stream()
                .map(CardInBasket::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0)));
        basket.setCardInBasketList(cardInBasketPage.toList());
        basket.setInfo(basketInfo);
        return basket;
    }

    /**
     * Получить полный список товаров в корзинах покупателей.
     * @return список зарезервированного товара.
     */
    @Override
    public List<CardInBasket> getAllFromBasket() {
        return basketRepository.findAll();
    }

    /**
     * Получить общую сумму товаров в корзине покупателя.
     * @param userId - уникальный номер покупателя.
     * @return сумма товаров в корзине.
     */
    @Override
    public BigDecimal getTotalPriceFromBasket(Long userId) {
        return basketRepository.findAllByUser_id(userId).stream()
                .map(CardInBasket::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

    /**
     * Удалить все товары из корзины покупателя.
     * @param userId - уникальный номер покупателя.
     */
    @TrackUserAction
    @Override
    public void deleteAllFromBasket(Long userId) {
        basketRepository.deleteAllByUser_id(userId);
    }

    /**
     * Регистрация нового пользователя.
     * @param characterResult пользователь.
     */
    @Override
    public void registerNewUser(CharacterResult characterResult) {

    }


}
