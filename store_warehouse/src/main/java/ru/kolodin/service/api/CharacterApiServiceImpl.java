package ru.kolodin.service.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kolodin.aspect.TrackUserAction;
import ru.kolodin.configuration.BasicConfig;
import ru.kolodin.model.characters.Characters;


import java.util.List;


/**
 * Сервис получения данных с сайта Rick and Morty.
 */
@Service
@RequiredArgsConstructor
@Log
public class CharacterApiServiceImpl  implements CharacterApiService{

    /**
     * Конфигуратор базовых настроек.
     */
    private final BasicConfig basicConfig;

    /**
     * Синхронный клиент REST.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Структура данных, представляющая заголовки HTTP-запросов или ответов.
     */
    @Autowired
    private HttpHeaders headers;

    /**
     * Подготовка объекта HTTP-запроса.
     * @return тело запроса.
     */
    private HttpEntity<String> getRequestEntity() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param page запрашиваемый номер страницы
     * @return Страница со списком героев
     */
    @Override
    public Characters getAllCharacters(String page) {
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
     * @param id номер товара.
     */
    @TrackUserAction
    @Override
    public void saveOneCharacterById(Integer id) {
        String url = basicConfig.getSERVER_API() + "/characters/add_to_storage/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<HttpStatusCode> responseType = HttpStatusCode.class;
        log.info("URI - " + url);
        ResponseEntity<HttpStatusCode> response = restTemplate.exchange(url, method, requestEntity, responseType);
        response.getStatusCode();
    }

}
