package ru.gb.group5984.service.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.visitors.CharacterInfo;
import ru.gb.group5984.model.visitors.CharacterResult;
import ru.gb.group5984.model.visitors.Characters;
import ru.gb.group5984.service.db.BankDbService;

import java.util.List;

/**
 * Сервис генерации клиентов банка из состава персонажей Rick and Morty
 */
@Service
@RequiredArgsConstructor
@Log
public class BankApiServiceImpl implements BankApiService {
    private final BankDbService characterDbService;
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
     * Получить с сайта Rick and Morty страницу со списком персонажей
     * - они же потенциальные клиенты банка
     * @param page номер страницы из списка персонажей
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    @Override
    public Characters getAllCharacters(String page) {
        String url = basicConfig.getCLIENT_API() + "/?page=" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<Characters> responseType = Characters.class;
        log.info("URI - " + url);
        ResponseEntity<Characters> response = restTemplate
                .exchange(url, method, requestEntity, responseType);
        Characters characters = response.getBody();
        assert characters != null;
        CharacterInfo characterInfo = getCharacterInfo(characters);
        characters.setInfo(characterInfo);
        return characters;
    }


    /**
     * Модификация информационной части о странице посетителей банка
     * перед её добавлением в модель веб-стрвницы
     * @param allCharacters Страница со списком посетителей банка
     * В данном варианте - Информация полученная с рессурса Rick and Morty
     * @return Модифицированная информационная часть о странице
     * Пояснение: В информационной части приходят ссылки на предыдущую
     * и следующую странцы,
     * но для загрузки в модель ссылки не нужны, но нужны номера страниц.
     * Поэтому ссылки меняются на номера страниц.
     * Если со ссылкой проблема, то она меняется на страницу 1.
     */
    private CharacterInfo getCharacterInfo(Characters allCharacters) {
        CharacterInfo characterInfo = allCharacters.getInfo();
        if (characterInfo.getPrev() == null
                || characterInfo.getPrev().isEmpty()
                || !characterInfo.getPrev()
                .contains("https://rickandmortyapi.com/api/character/?page=")) {
            characterInfo.setPrev("1");
        } else {
            characterInfo.setPrev(characterInfo
                    .getPrev()
                    .replace("https://rickandmortyapi.com/api/character/?page="
                            , ""));
        }
        if (characterInfo.getNext() == null
                || characterInfo.getNext().isEmpty()
                || !characterInfo.getNext()
                .contains("https://rickandmortyapi.com/api/character/?page=")) {
            characterInfo.setNext("1");
        } else {
            characterInfo.setNext(characterInfo
                    .getNext()
                    .replace("https://rickandmortyapi.com/api/character/?page="
                            , ""));
        }
        return characterInfo;
    }


    /**
     * Зачислить визитера банка в кандидаты на открытие счета и сохранить его данные в базе данных.
     * @param id уникальный номер персонажа - посетителя банка.
     * Этапы:
     *  - осуществляется поиск карточки по её id нв сайте Rick and Morty;
     *  - если карточка найдена,
     *  то она передается в сервис работы с базой данных для её сохранения.
     * @return - сообщение о результате сохранения визитера в базе данных.
     */
    @Override
    public Message saveOneCharacterById(Integer id) {
        String url = basicConfig.getCLIENT_API() + "/" + id;
        Message message = new Message();
        message.setMessage("");
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = getRequestEntity();
        Class<CharacterResult> responseType = CharacterResult.class;
        log.info("URI - " + url);
        CharacterResult characterResult = new CharacterResult();
        try {
            characterResult = restTemplate
                    .exchange(url, method, requestEntity, responseType).getBody();
        } catch (RuntimeException e) {
            message.setMessage("Невозможно получить данные с ресурса Rick and Morty: "
                    + e.getMessage());
        }
        try {
            characterDbService.saveOneVisitor(characterResult);
        } catch (RuntimeException e) {
            message.setMessage(message.getMessage()
                    + " Ошибка при сохранении данных: " + e);
        }
        if (message.getMessage().isEmpty()) message.setMessage("OK");
        return message;
    }
}
