package group5984.service.api;

import group5984.auth.AuthenticationService;
import group5984.configuration.BasicConfig;
import group5984.model.basket.Basket;
import group5984.model.clients.Cards;
import group5984.model.messeges.Message;
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
     * @param page запрашиваемая страница товаров.
     * @return список товаров в корзине.
     */
    @Override
    public Basket getAllFromBasket(String page) {
        String url = basicConfig.getSERVER_API() + "/basket/page/" + page;
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
     */
    @Override
    public void addToBasketById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/basket/add_to_basket/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
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
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * Оплатить товар в корзине.
     */
    @Override
    public Message basketPay() {
        String url = basicConfig.getSERVER_API() + "/basket/pay";
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);
        ResponseEntity<Message> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

}
