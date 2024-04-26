package ru.gb.group5984.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.service.api.CharacterApiService;


/**
 * Контроллер API
 * Проверка свагером http://localhost:8080/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
@Log
public class CharacterRestController {
    private final CharacterApiService service;
    private final BasicConfig rickAndMortyApiConfig;

    /**
     * Принудительная пересылка со страницы /rest на загрузку первой страницы героев с рессурса
     * @return страница героев №1 с рессура Rick and Morty
     */
    @GetMapping("/")
    public String redirectToFirstPage() {
        return "redirect:/characters/page/1";
    }

    /**
     *Метод подготовки информации о героях с загрузкой соответсвующе страницы с рессурса Rick and Morty
     * @param page номер страницы из списка героев
     * @return Список героев и статус ответа
     */
    @GetMapping("/characters/page/{page}")
    public ResponseEntity<Characters> getCharacters(@PathVariable("page") String page) {
        String url = rickAndMortyApiConfig.getCHARACTER_API() + "/?page=" + page;
        Characters allCharacters = service.getAllCharacters(url);
        return new ResponseEntity<>(allCharacters, HttpStatus.OK);
    }
}
