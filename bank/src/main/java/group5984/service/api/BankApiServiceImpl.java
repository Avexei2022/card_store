package group5984.service.api;

import group5984.model.visitors.CharacterResult;
import group5984.model.visitors.Characters;
import group5984.service.db.BankDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

/**
 * Сервис генерации клиентов банка из состава персонажей Rick and Morty
 */
@Service
@RequiredArgsConstructor
@Log
public class BankApiServiceImpl implements BankApiService {
    private final BankDbService characterDbService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    /**
     * Получить с сайта Rick and Morty страницу со списком персонажей
     * - они же потенциальные клиенты банка
     * @param url ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
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
     * Зачислить визитера банка в кандидаты на открытие счета и сохранить его данные в базе данных.
     * @param url ссылка
     * Этапы:
     *  - осуществляется поиск карточки по её id нв сайте Rick and Morty;
     *  - если карточка найдена, то она передается в сервис работы с базой данных для её сохранения
     */
    @Override
    public void saveOneCharacterById(String url) {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        Class<CharacterResult> responseType = CharacterResult.class;
        log.info("URI - " + url);
        CharacterResult characterResult = restTemplate.exchange(url, method, requestEntity, responseType).getBody();
        if (characterResult != null) characterDbService.saveOneVisitor(characterResult);
    }

}
