package group5984.service.api;

import group5984.configuration.BasicConfig;
import group5984.model.messeges.Message;
import group5984.model.visitors.CharacterResult;
import group5984.model.visitors.Characters;
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
public class CharactersApiServiceImpl implements CharactersApiService {
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
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    /**
     * Получить страницу со списком персонажей с ресурса Rick and Morty
     * - они же потенциальные клиенты банка.
     * @param page номер страницы.
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    @Override
    public Characters getPageCharacters(String page) throws RuntimeException{
        String url = basicConfig.getBANK_API() + "/visitors/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Characters> responseType = Characters.class;
        log.info("URI - " + url);
        try {
            ResponseEntity<Characters> response = restTemplate
                    .exchange(url, method, requestEntity, responseType);
            return response.getBody();
        } catch (RuntimeException e) {
            throw new RuntimeException("Ресурс временно недоступен: " + e.getMessage());
        }
    }

    /**
     * Зачислить визитера банка в кандидаты на открытие счета и сохранить его данные в базе данных.
     * @param id уникальный номер посетителя банка.
     * Этапы:
     *  - осуществляется поиск карточки по её id нв сайте Rick and Morty;
     *  - если карточка найдена, то она передается в сервис работы с базой данных для её сохранения
     */
    @Override
    public Message saveOneCharacterById(Integer id) {
        String url = basicConfig.getBANK_API() + "/visitors/add_to_bank/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);
        try {
            ResponseEntity<Message> response = restTemplate
                    .exchange(url, method, requestEntity, responseType);
            return response.getBody();
        } catch (RuntimeException e) {
            Message message = new Message();
            message.setMessage("Ресурс временно недоступен: " + e.getMessage());
            return message;
        }
    }

}
