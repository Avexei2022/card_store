package group5984.service.api;

import group5984.model.characters.Characters;
import group5984.model.storage.CardsStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

/**
 * Сервис героев
 * Работает с рессурсом Rick and Morty и с базой данных
 */
@Service
@RequiredArgsConstructor
@Log
public class ContentApiServiceImpl implements ContentApiService {

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
    public Page<CardsStorage> getAllCardsStorage(String url) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<Page<CardsStorage>> responseType = Page<CardsStorage>.class;
        log.info("URI - " + url);
        ResponseEntity<Page<CardsStorage>> response = restTemplate.exchange(url, method, requestEntity, responseType);
        return response.getBody();
    }

    /**
     * Метод сохранения выбранной пользователем карточки в базе данных
     * @param url ссылка
     * Этапы:
     *  - осуществляется поиск карточки по её id нв сайте Rick and Morty;
     *  - если карточка найдена, то она передается в сервис работы с базой данных для её сохранения
     */
    @Override
    public void saveOneCardsStorageById(String url) {

    }

    @Override
    public void deleteOneCardsStorageById(String url) {

    }
}
