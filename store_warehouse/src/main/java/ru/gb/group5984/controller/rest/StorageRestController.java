package ru.gb.group5984.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsStorage;
import ru.gb.group5984.service.api.CharacterApiService;
import ru.gb.group5984.service.db.CharacterDbService;

import java.util.List;


/**
 * REST Контроллер склада магазина
 * Проверка свагером http://localhost:8082/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
@Log
public class StorageRestController {
    private final CharacterDbService service;

    /**
     * Принудительная пересылка на загрузку первой страницы карточек
     * @return пересылка на
     */
    @GetMapping("/")
    public String redirectToFirstPage() {
        return "redirect:/cards";
    }

    /**
     *Метод подготовки информации о героях с загрузкой соответсвующе страницы с рессурса Rick and Morty
     * @return Список героев и статус ответа
     */
    @GetMapping("/cards/page/{page}")
    public ResponseEntity<Cards> getAllCards(@PathVariable("page") Integer page) {
        return new ResponseEntity<>(service.getAllCardsStorageFromSale(page), HttpStatus.OK);
    }
}
