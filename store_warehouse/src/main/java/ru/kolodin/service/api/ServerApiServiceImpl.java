package ru.kolodin.service.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kolodin.aspect.TrackUserAction;
import ru.kolodin.auth.AuthenticationService;
import ru.kolodin.configuration.BasicConfig;
import ru.kolodin.model.basket.Basket;
import ru.kolodin.model.characters.Characters;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.storage.Cards;


/**
 * Сервиса взаимодействия с API сервиса ресурсов магазина.
 */
@Service
@RequiredArgsConstructor
@Log
public class ServerApiServiceImpl implements ServerApiService {

    /**
     * Конфигуратор базовых настроек.
     */
    private final BasicConfig basicConfig;

    /**
     * Сервис аутентификации пользователей.
     */
    private final AuthenticationService authenticationService;

    /**
     * Синхронный клиент REST.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Получить страницу из списка товаров на складе.
     * @param page номер страницы.
     * @return список товара.
     */
    @TrackUserAction
    @Override
    public Characters getPageFromStorage(String page) {
        String url = basicConfig.getSERVER_API() + "/storage/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Characters> responseType = Characters.class;
        log.info("URI - " + url);
        ResponseEntity<Characters> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Удалить товар со склада.
     * @param id уникальный номер товара.
     * @return сообщение о результате удаления товара.
     */
    @Override
    @TrackUserAction
    public Message deleteFromStorageById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/characters/" + id;
        HttpMethod method = HttpMethod.DELETE;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);
        ResponseEntity<Message> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    //TODO Реализовать ввод данных от пользователя
    /**
     * Выствить товар на продажу.
     * Данные действия пользователя выводятся в консоль.
     * @param id уникальный номер товара.
     */

    @Override
    @TrackUserAction
    public void saveOneCardToSaleById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/storage/" + id;
        HttpMethod method = HttpMethod.POST;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * Получить все товары постранично, выставленные на продажу.
     * По умолчанию страница содержит 20 товаров
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров в корзине;
     * - количество страниц;
     * - номера текущей, предыдущей и следующей страниц.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы
     * Данные действия пользователя выводятся в консоль
     * @param page - запрашиваемая пользователем страница.
     * @return список товаров в продаже.
     */
    @TrackUserAction
    @Override
    public Cards getPageCardsStorageFromSale(Integer page) {
        String url = basicConfig.getSERVER_API() + "/sale/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Cards> responseType = Cards.class;
        log.info("URI - " + url);
        ResponseEntity<Cards> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Удалить товар из списка продаж / убрать с полки.
     * Данные действия пользователя выводятся в консоль
     * @param id - уникальный номер товара.
     */
    @Override
    @TrackUserAction
    public void deleteCardFromSaleById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/storage/" + id;
        HttpMethod method = HttpMethod.DELETE;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }


    /**
     * Переместить единицу товара с полки в корзину покупателя.
     *  При наличии товара на полке его количество уменьшается на единицу.
     *  Если товара на полке больше нет, то данная партия товара удаляется из списка.
     *  В корзине сохраняется информация о номере партии товара,
     *  а также дата и время перемещения товара в корзину.
     * @param id - уникальный номер товара.
     */
    //TODO доработать ввод количества товара и проверку на валидность
    @Override
    public void moveCardToBasket(Long id) {
        String url = basicConfig.getSERVER_API() + "/basket/" + id;
        HttpMethod method = HttpMethod.POST;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }


    /**
     * Возврат единицы товара из корзины покупателя на полку магазина.
     * Проверяется наличие партии данного товара на полке.
     * Если товар из данной партии в наличии на полке, то его количество увеличивается на количество товара в корзине.
     * Если товар из данной партии на полке отсутствует,
     * то восстанавливается партия товара на полке в количестве товара из корзины.
     * Данные действия пользователя выводятся в консоль
     * @param id уникальный номер товара.
     */
    @Override
    @TrackUserAction
    public void returnCardFromBasketToSale(Long id) {
        String url = basicConfig.getSERVER_API() + "/basket/" + id;
        HttpMethod method = HttpMethod.DELETE;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * Получить все товары постранично, зарезервированные в корзине.
     * По умолчанию страница содержит 20 товаров
     * Список товаров дополнен следующей информацией о нем:
     * - общее количество товаров в корзине;
     * - количество страниц;
     * - номера текущей, предыдущей и следующей страниц;
     * -общая сумма товара в корзине.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы.
     * Данные действия пользователя выводятся в консоль.
     * @param page - запрашиваемая пользователем страница.
     * @return список товаров в корзине.
     */
    @TrackUserAction
    @Override
    public Basket getAllFromBasket(Integer page) {
        String url = basicConfig.getSERVER_API() + "/basket/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Basket> responseType = Basket.class;
        log.info("URI - " + url);
        ResponseEntity<Basket> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }



}
