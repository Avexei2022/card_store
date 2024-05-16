package ru.gb.group5984.service.api;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.auth.AuthenticationRequest;
import ru.gb.group5984.auth.AuthenticationResponse;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.users.User;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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
        String token = getToken("storage", "storage");
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
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
    public Message deleteFromStorageById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/characters/delete_from_storage/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);
        ResponseEntity<Message> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
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

    /**
     * Получить пользователя по имени.
     * @param name имя пользователя.
     * @return пользователь.
     */
    @Override
    public User getUserByUserName(String name) {
        String url = basicConfig.getSERVER_API() + "/user/" + name;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<User> responseType = User.class;
        log.info("LOG: ServerApiServiceImpl.getUserByUserName.URI = " + url);
        ResponseEntity<User> response = restTemplate.exchange(url, method, requestEntity, responseType);
        User user = response.getBody();
        assert user != null;
        log.info("LOG: ServerApiServiceImpl.getUserByUserName.userDetails = " + user);
        return user;
    }

    private String getToken(String username, String password) {
        String url = basicConfig.getSERVER_API() + "/auth/authenticate";
        HttpMethod method = HttpMethod.POST;
        var requestBody = AuthenticationRequest.builder()
                .username(username)
                        .password(password).build();
        log.info("LOG:  ServerApiServiceImpl.getToken.requestBody = " + requestBody.getRequest() + "\n" + requestBody);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.getRequest(), headers);
        log.info("LOG:  ServerApiServiceImpl.getToken.requestEntity.Header = " + requestEntity.getHeaders());
        log.info("LOG:  ServerApiServiceImpl.getToken.requestEntity.Body = " + requestEntity.getBody());
        Class<AuthenticationResponse> responseType = AuthenticationResponse.class;
        ResponseEntity<AuthenticationResponse> response = restTemplate.exchange(url, method, requestEntity, responseType);
        String token = Objects.requireNonNull(response.getBody()).getToken();
        log.info("LOG:  ServerApiServiceImpl.getToken.token = " + token);
        return token;

    }

}
