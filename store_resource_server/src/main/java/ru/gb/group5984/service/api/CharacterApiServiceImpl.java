package ru.gb.group5984.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.auth.AuthenticationService;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.basket.CardInBasket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.model.users.Buyer;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.service.db.ServerDbService;
import ru.gb.group5984.service.db.UserDbService;
import ru.gb.group5984.service.integration.FileGateway;

import java.math.BigDecimal;
import java.util.List;


/**
 * Сервис получения данных с сайта Rick and Morty
 */
@Service
@RequiredArgsConstructor
@Log
public class CharacterApiServiceImpl  implements CharacterApiService{
    private final ServerDbService serverDbService;
    private final BasicConfig basicConfig;
    private final AuthenticationService authenticationService;
    private final FileGateway fileGateway;
    private final UserDbService userDbService;


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
     * Получить с сайта Rick and Morty страницу со списком героев.
     * @param page номер страницы.
     * @return Страница со списком героев.
     */
    @Override
    public Characters getPageCharacters(String page) {
        String url = basicConfig.getCHARACTER_API() + "/?page=" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Characters> responseType = Characters.class;
        log.info("URI - " + url);
        ResponseEntity<Characters> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * "Закупка" единицы товара на сервисе Rick and Morty и сохранение в базе данных склада
     * @param id номер карточки.
     */
    @Override
    public void saveOneCharacterById(Integer id) {
        String url = basicConfig.getCHARACTER_API() + "/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<CharacterResult> responseType = CharacterResult.class;
        log.info("URI - " + url);
        CharacterResult characterResult = restTemplate.exchange(url, method, requestEntity, responseType).getBody();
        if (characterResult != null) serverDbService.saveOneCharacter(characterResult);
    }

    /**
     * Оплата товара из корзины покупателя через банк.
     * По легенде Номер счета продавца - 1.
     * @param userName имя/логин покупателя.
     */
    @Override
    @Transactional
    @TrackUserAction
    public Message basketPay(String userName) {
        Buyer creditUser = userDbService.findBuyerByUsername(userName);
        BigDecimal totalAmount = serverDbService.getTotalPriceFromBasket(creditUser.getId());
        Message message = new Message();
        if (totalAmount.compareTo(BigDecimal.valueOf(0)) > 0) {
            String url = basicConfig.getBANK_API() + "/transaction";
            HttpMethod method = HttpMethod.POST;
            Class<Message> responseType = Message.class;
            var transaction = Transaction.builder()
                    .creditName(userName)
                    .debitName(basicConfig.getDEBIT_USER())
                    .transferAmount(totalAmount)
                    .build();
            String jsonTransaction = "";
            try {
                jsonTransaction = new ObjectMapper().writeValueAsString(transaction);
            } catch (JsonProcessingException ignored){
            }

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonTransaction, authenticationService.getHeaders());

            try {
                ResponseEntity<Message> response = restTemplate
                        .exchange(url, method, requestEntity, responseType);
                message = response.getBody();
                assert message != null;
                if (message.getMessage().equals("OK")) {
                    integrationMessage();
                    serverDbService.deleteAllFromBasket(creditUser.getId());
                    message.setMessage("Оплата товара прошла успешно. Поздравляем с покупкой!");
                }
            } catch (RuntimeException e) {
                message.setMessage("Ресурс банка временно недоступен: " + e.getMessage());
            }
        } else message.setMessage("Оплачивать нечего, корзина пуста.");

        return message;
    }

    /**
     * Интеграционное сообщение о проданном товаре после его оплаты.
     * Вызывается из метода basketPay.
     */
    private void integrationMessage() {
        List<CardInBasket> cardInBasketList = serverDbService.getAllFromBasket();
        for (CardInBasket cardInBasket: cardInBasketList) {
            String fileName = cardInBasket.getCard().getName().concat(".txt");
            fileGateway.writeToFile(fileName, cardInBasket);
        }
    }

    /**
     * Регистрация нового пользователя из списка героев сервиса Rick and Morty.
     * @param id уникальный номер героя.
     */
    @Override
    public Message registerNewUser(Integer id) {
        String url = basicConfig.getCHARACTER_API() + "/" + id;
        Message message = new Message();
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<CharacterResult> responseType = CharacterResult.class;
        log.info("URI - " + url);
        CharacterResult characterResult = new CharacterResult();
        try {
            characterResult = restTemplate.exchange(url, method, requestEntity, responseType).getBody();
        } catch (RuntimeException e) {
            message.setMessage("Ресурс Rick and Morty временно недоступен. ");
            return message;
        }

        if (characterResult != null) {
            message = userDbService.registerNewBuyer(characterResult);
        } else {
            message.setMessage("Персонажа с таким уникальным номером не существует");
        }
        return message;
    }
}
