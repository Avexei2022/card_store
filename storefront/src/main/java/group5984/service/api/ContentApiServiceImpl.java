package group5984.service.api;

import group5984.configuration.BasicConfig;
import group5984.model.basket.Basket;
import group5984.model.storage.Cards;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

/**
 * Сервис витрины / торгового зала магазина
 * Готовит запросы на склад и получает от него соответствующие ответы
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
     * Получить со склада список товаров, выставленных на продажу
     * @param way путь соответствующего запроса на склад
     * @return Список товаров на полке
     */
    @Override
    public Cards getAllFromSale(String way) {
        String url = basicConfig.getSTORAGE_API() + way;
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<Cards> responseType = Cards.class;
        log.info("URI - " + url);
        ResponseEntity<Cards> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Получить список товаров в корзине покупателя
     * @param way путь соответствующего запроса на склад
     * @return список товаров в корзине
     */
    @Override
    public Basket getAllFromBasket(String way) {
        String url = basicConfig.getSTORAGE_API() + way;
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<Basket> responseType = Basket.class;
        log.info("URI - " + url);
        ResponseEntity<Basket> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Добавить товар в корзину покупателя
     * @param id - id товара
     */
    @Override
    public void addToBasketById(Integer id) {
        String url = basicConfig.getSTORAGE_API() + "/basket/add_to_basket/" + id;
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

    /**
     * вернуть товар из корзины покупателя на полку
     * @param id - id товара
     */
    @Override
    public void deleteFromBasketById(Integer id) {
        String url = basicConfig.getSTORAGE_API() + "/basket/delete_from_basket/" + id;
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }
}
