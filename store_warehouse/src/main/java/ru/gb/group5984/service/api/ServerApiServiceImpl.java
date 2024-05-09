package ru.gb.group5984.service.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.repository.BasketRepository;
import ru.gb.group5984.repository.CardsRepository;
import ru.gb.group5984.repository.CharacterRepository;
import java.util.List;


/**
 * Сервис склада магазина
 * Работает с базой данных:
 * - товары на складе;
 * - товары в продаже;
 * - товары в корзине покупателя (зарезервированные)
 */
@Service
@RequiredArgsConstructor
@Log
public class ServerApiServiceImpl implements ServerApiService {
    private final CharacterRepository characterRepository;
    private final CardsRepository cardsRepository;
    private final BasketRepository basketRepository;
    private final BasicConfig basicConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    /**
     * Подготовка объекта HTTP-запроса.
     * @return
     */
    private HttpEntity<String> getRequestEntity() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    /**
     * Получить полный список товаров из базы данных товаров на складе
     * @return список товара
     * При вызове метода его наименование, аргументы и время исполнения выводятся в консоль.
     */
    @TrackUserAction
    @Override
    public Characters getPageFromStorage(String page) {
        String url = basicConfig.getSERVER_API() + "/storage/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Characters> responseType = Characters.class;
        log.info("URI - " + url);
        ResponseEntity<Characters> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
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
        String url = basicConfig.getSERVER_API() + "/characters/delete_from_storage/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
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
    public void saveOneCardToSaleById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/storage/add_to_sale/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
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
    public Cards getPageCardsStorageFromSale(Integer page) {
        String url = basicConfig.getSERVER_API() + "/sale/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Cards> responseType = Cards.class;
        log.info("URI - " + url);
        ResponseEntity<Cards> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
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
        String url = basicConfig.getSERVER_API() + "/storage/delete_from_sale/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
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
    public void moveCardToBasket(Long id) {
        String url = basicConfig.getSERVER_API() + "/basket/add_to_basket/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }


    /**
     * Возврат единицы товара из корзины покупателя на полку магазина
     * @param id id - товара в корзине
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
        String url = basicConfig.getSERVER_API() + "/basket/return_to_sale/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
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
        String url = basicConfig.getSERVER_API() + "/basket/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Basket> responseType = Basket.class;
        log.info("URI - " + url);
        ResponseEntity<Basket> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

}
