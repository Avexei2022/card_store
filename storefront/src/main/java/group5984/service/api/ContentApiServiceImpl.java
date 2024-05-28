package group5984.service.api;

import group5984.auth.AuthenticationService;
import group5984.configuration.BasicConfig;
import group5984.model.basket.Basket;
import group5984.model.cards.Cards;
import group5984.model.messeges.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private final AuthenticationService authenticationService;

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
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Cards> responseType = Cards.class;
        log.info("URI - " + url);
        ResponseEntity<Cards> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Получить список товаров в корзине покупателя.
     * @param userName - имя/логин покупателя.
     * @param page запрашиваемая страница товаров.
     * @return список товаров в корзине.
     */
    @Override
    public Basket getPageFromBasket(String userName, String page) {
        String url = basicConfig.getSERVER_API() + "/basket/page/" + page + "/" + userName;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Basket> responseType = Basket.class;
        log.info("URI - " + url);
        ResponseEntity<Basket> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Добавить товар в корзину покупателя.
     * @param id - id товара.
     * @param userName - имя пользователя.
     */
    @Override
    public Message addToBasketById(Integer id, String userName) {
        String url = basicConfig.getSERVER_API() + "/basket/add_to_basket/" + id + "/" + userName;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);
        ResponseEntity<Message> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Вернуть товар из корзины покупателя на полку.
     * @param id - id товара.
     */
    @Override
    public void deleteFromBasketById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/basket/return_to_sale/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * Оплатить товар в корзине.
     * @param userName - имя пользователя.
     */
    @Override
    public Message basketPay(String userName) {
        String url = basicConfig.getSERVER_API() + "/basket/pay/" + userName;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);
        ResponseEntity<Message> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

}
