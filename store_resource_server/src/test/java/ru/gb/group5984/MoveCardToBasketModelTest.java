package ru.gb.group5984;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.group5984.model.basket.CardInBasket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.storage.CardsStorage;
import ru.gb.group5984.model.users.Role;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.repository.BasketRepository;
import ru.gb.group5984.repository.CardsRepository;
import ru.gb.group5984.service.db.ServerDbServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


/**
 * Модульный тест перемещения товара с полки в корзину покупателя
 * / Резервирование товара на складе.
 */
@ExtendWith(MockitoExtension.class)
public class MoveCardToBasketModelTest {

    @InjectMocks
    private ServerDbServiceImpl serverDbService;
    @Mock
    private BasketRepository basketRepository;
    @Mock
    private CardsRepository cardsRepository;

    /**
     * Проверка корректности подсчета товара на складе и в корзине.
     */
    @Test
    public void cardCorrectCountTest() {
        //Блок предусловия
        Date date = new Date();
        LocalDate localDate = LocalDate.now();
        CardsStorage cardsStorage = createCardsStorage(10, date);
        CardsStorage cardsStorageAfterSale = createCardsStorage(9, date);
        CardInBasket cardInBasket = createCardInBasket(1, date, localDate);

        given(cardsRepository.findById(1L)).willReturn(Optional.of(cardsStorage));

        //Блок действия
        serverDbService.moveCardToBasket(1L, "user");

        //Блок проверки
        verify(cardsRepository).save(cardsStorageAfterSale);
        verify(basketRepository).save(cardInBasket);
    }

    /**
     * Проверка исключения при отсутствии товара на складе.
     */
    @Test
    public void cardAmountExceptionTest() {
        //Блок предусловия
        given(cardsRepository.findById(2L)).willReturn(Optional.empty());
        //Блок действия

        assertThrows(NoSuchElementException.class, () -> serverDbService.moveCardToBasket(2L, "user"));

        //Блок проверки
        verify(cardsRepository, never()).save(any());
        verify(basketRepository, never()).save(any());
    }

    /**
     * Создание тестового товара на складе.
     * @param amount количество товара.
     * @return товар.
     */
    private CardsStorage createCardsStorage(Integer amount, Date date) {
        CharacterResult characterResult = new CharacterResult();
        characterResult.setId(1);
        characterResult.setName("item_name");
        characterResult.setStatus("item_status");
        characterResult.setSpecies("item_species");
        characterResult.setType("item_type");
        characterResult.setGender("item_mail");
        characterResult.setImage("item_img");
        characterResult.setUrl("item_url");
        characterResult.setCreated(date);

        CardsStorage cardsStorage = new CardsStorage();
        cardsStorage.setId(1L);
        cardsStorage.setCard(characterResult);
        cardsStorage.setAmount(amount);
        cardsStorage.setPrice(BigDecimal.valueOf(20));

        return cardsStorage;
    }

    /**
     * Создание тестового пользователя
     * @return тестовый пользователь
     */
    private User createUser() {
        return new User(2L, "user", "$2a$10$OO6WBhYkkQSa7RLmzA9VyeOH2CzUB2yO6bLJFNEjERBAg.P6Gk2Rq"
                , Role.USER, true, "user@gmail.com", true);
    }

    /**
     * Создание тестового товара в корзине.
     * @return товар.
     */
    private CardInBasket createCardInBasket(Integer amount, Date date, LocalDate localDate) {
        CharacterResult characterResult = new CharacterResult();
        characterResult.setId(1);
        characterResult.setName("item_name");
        characterResult.setStatus("item_status");
        characterResult.setSpecies("item_species");
        characterResult.setType("item_type");
        characterResult.setGender("item_mail");
        characterResult.setImage("item_img");
        characterResult.setUrl("item_url");
        characterResult.setCreated(date);

        CardInBasket cardInBasket = new CardInBasket();
        cardInBasket.setId(null);
        cardInBasket.setCard(characterResult);
        cardInBasket.setAmount(amount);
        cardInBasket.setPrice(BigDecimal.valueOf(20));
        cardInBasket.setCardsStorageId(1L);
        cardInBasket.setCreated(localDate);
        cardInBasket.setUser(createUser());

        return cardInBasket;
    }

}
