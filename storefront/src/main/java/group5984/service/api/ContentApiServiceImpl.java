package group5984.service.api;

import group5984.configuration.BasicConfig;
import group5984.model.basket.Basket;
import group5984.model.clients.Cards;
import group5984.model.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Сервис витрины / торгового зала магазина.
 * Готовит запросы на склад и получает от него соответствующие ответы.
 */
@Service
@RequiredArgsConstructor
@Log
public class ContentApiServiceImpl implements ContentApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    private final BasicConfig basicConfig;


    /**
     * Получить со склада список товаров, выставленных на продажу.
     * @param page запрашиваемая страница товаров.
     * @return Список товаров на полке.
     */
    @Override
    public Cards getAllFromSale(String page) {
        String url = basicConfig.getSERVER_API() + "/sale/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Cards> responseType = Cards.class;
        log.info("URI - " + url);
        ResponseEntity<Cards> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Получить список товаров в корзине покупателя.
     * @param page запрашиваемая страница товаров.
     * @return список товаров в корзине.
     */
    @Override
    public Basket getAllFromBasket(String page) {
        String url = basicConfig.getSERVER_API() + "/basket/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Basket> responseType = Basket.class;
        log.info("URI - " + url);
        ResponseEntity<Basket> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Добавить товар в корзину покупателя.
     * @param id - id товара.
     */
    @Override
    public void addToBasketById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/basket/add_to_basket/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * Вернуть товар из корзины покупателя на полку.
     * @param id - id товара.
     */
    @Override
    public void deleteFromBasketById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/basket/return_to_sale/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * Оплатить товар в корзине.
     */
    @Override
    public void basketPay() {
        String url = basicConfig.getSERVER_API() + "/basket/pay";
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
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
        log.info("URI - " + url);
        ResponseEntity<User> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Подготовка объекта HTTP-запроса.
     * @return
     */
    private HttpEntity<String> getRequestEntity() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }
}
