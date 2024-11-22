package kolodin.service.api;

import kolodin.configuration.BasicConfig;
import kolodin.model.messeges.Message;
import kolodin.model.visitors.Characters;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

/**
 * Сервис взаимодействия с Rest-сервисом ресурсов банка
 * в части посетителей банка.
 */
@Service
@RequiredArgsConstructor
@Log
public class CharactersApiServiceImpl implements CharactersApiService {

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
     * @return основа запроса с заголовком.
     */
    private HttpEntity<String> getRequestEntity() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    /**
     * Получить страницу со списком посетителей банка.
     * @param page номер страницы.
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     * @throws RuntimeException - исключение в случае недоступности ресурса.
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
     * Зачислить посетителя банка в кандидаты на открытие счета.
     * @param id уникальный номер посетителя банка.
     * @return сообщение о результатах сохранения.
     */
    @Override
    public Message saveOneCharacterById(Integer id) {
        String url = basicConfig.getBANK_API() + "/visitors/" + id;
        HttpMethod method = HttpMethod.POST;
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
