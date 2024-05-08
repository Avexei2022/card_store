package ru.gb.group5984.service.api;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.service.db.CharacterDbService;

import java.util.List;


/**
 * Сервис получения данных с сайта Rick and Morty
 */
@Service
@RequiredArgsConstructor
@Log
public class CharacterApiServiceImpl  implements CharacterApiService{
    private final CharacterDbService characterDbService;
    private final BasicConfig basicConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param url ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком героев
     */
    @Override
    public Characters getAllCharacters(String url) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<Characters> responseType = Characters.class;
        log.info("URI - " + url);
        ResponseEntity<Characters> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * "Закупка" единицы товара на сервисе Rick and Morty и сохранение в базе данных склада
     * @param url ссылка на сервис Rick and Morty
     */
    @Override
    public void saveOneCharacterById(String url) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<CharacterResult> responseType = CharacterResult.class;
        log.info("URI - " + url);
        CharacterResult characterResult = restTemplate.exchange(url, method, requestEntity, responseType).getBody();
        if (characterResult != null) characterDbService.saveOneCharacter(characterResult);
    }

    //TODO Доработать
    /**
     * Оплата товара из корзины покупателя через банк.
     */
    @Override
    @Transactional
    public void basketPay() {
        Double totalAmount = characterDbService.getTotalPriceFromBasket();
        String url = basicConfig.getBANK_API();
        Transaction transaction = new Transaction();
        transaction.setCreditAccount(2L); // Пока тест
        transaction.setDebitAccount(1L); // Пока тест
        transaction.setTransferAmount(totalAmount);
        restTemplate.postForEntity(url, transaction, Transaction.class);
        characterDbService.deleteAllFromBasket();
    }
}
