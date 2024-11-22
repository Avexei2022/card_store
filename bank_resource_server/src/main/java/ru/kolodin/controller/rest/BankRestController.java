package ru.kolodin.controller.rest;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolodin.aspect.TrackUserAction;
import ru.kolodin.model.clients.Client;
import ru.kolodin.model.clients.ClientsList;
import ru.kolodin.model.exceptions.ExcessAmountException;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.transactions.Transaction;
import ru.kolodin.model.users.User;
import ru.kolodin.model.visitors.Characters;
import ru.kolodin.service.api.BankApiService;
import ru.kolodin.service.db.BankDbService;
import ru.kolodin.service.db.UserDbService;

import java.util.NoSuchElementException;


/**
 * REST Контроллер банка.
 * Проверка свагером http://localhost:8085/swagger-ui/index.html
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank_server")
@Log
public class BankRestController {

    /**
     * Сервис взаимодействия с API сторонних ресурсов.
     */
    private final BankApiService bankApiService;

    /**
     * Сервис взаимодействия с базой данных.
     */
    private final BankDbService bankDbService;

    /**
     * Сервис взаимодействия с базой данных банка.
     */
    private final UserDbService userDbService;

    /**
     * Метрика - счетчик транзакций.
     */
    private final Counter transactionGoodCounter = Metrics.counter("transactionGoodCounter");

    /**
     * Запрос списка "посетителей банка" -
     * в тестовом варианте страницы из списка персонажей с ресурса Rick and Morty.
     * @param page номер страницы в списке "посетителей банка".
     * @return страница из списка "посетителей банка".
     */
    @GetMapping("/visitors/page/{page}")
    public ResponseEntity<Characters> getVisitors(@PathVariable("page") String page) {
        Characters allCharacters = bankApiService.getAllCharacters(page);
        return new ResponseEntity<>(allCharacters, HttpStatus.OK);
    }

    /**
     * Добавить посетителя банка в базу данных кандидатов на открытие счета.
     * @param id уникальный номер посетителя банка.
     * @return сообщение о результатах.
     */
    @PostMapping("/visitors/{id}")
    public ResponseEntity<Message> addToBank(@PathVariable("id") Integer id) {
        Message message = bankApiService.saveOneCharacterById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить кандидата на открытие счета из базы данных.
     * @param id уникальный номер кандидата.
     * @return сообщение о результатах.
     */
    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<Message> deleteCandidateFromBank(@PathVariable("id") Integer id) {
        Message message = bankDbService.deleteVisitorById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить список кандидатов на открытие счета
     * @param page номер страницы из списка кандидатов
     * @return страница со списком кандидатов, включая информационную часть страницы
     */
    @GetMapping("/candidates/page/{page}")
    public ResponseEntity<Characters> getPageCandidates(@PathVariable("page") String page) {
        Characters characters = bankDbService.getPageBankCandidates(Integer.valueOf(page));
        return new ResponseEntity<>(characters, HttpStatus.OK);

    }

    /**
     * Добавить кандидата в базу данных клиентов банка - открыть счет.
     * @param id уникальный номер кандидата.
     * @return сообщение о результатах.
     */
    @PostMapping("/candidates/{id}")
    public ResponseEntity<Message> addCandidateToClient(@PathVariable("id") Integer id) {
        Message message = bankDbService.saveOneClientById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить клиента из базы данных банка - закрыть счет.
     * @param id уникальный номер клиента
     * @return сообщение о результатах.
     */
    @TrackUserAction
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Message> deleteClient(@PathVariable("id") Long id) {
        Message message = bankDbService.deleteClientById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить список клиентов банка.
     * @param page номер запрашиваемой пользователем страницы из списка клиентов.
     * @return страница из списка клиентов.
     */
    @GetMapping("/clients/page/{page}")
    public ResponseEntity<ClientsList> getPageCardsInSale(@PathVariable("page") Integer page) {
        ClientsList clientsList = bankDbService.getPageBankClients(page);
        return new ResponseEntity<>(clientsList, HttpStatus.OK);

    }

    /**
     * Обновить данные о клиенте банка.
     * @param client клиент банка.
     * @return сообщение о результатах.
     */
    @PutMapping("/clients")
    public ResponseEntity<Message> updateClient(Client client) {
        Message message = bankDbService.saveClient(client);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Проведение транзакции.
     * @param transaction транзакция с данными.
     * @return ответ с подтверждением.
     */
    @TrackUserAction
    @PostMapping("/transaction")
    public ResponseEntity<Message> transaction(@RequestBody Transaction transaction){
        log.info("ТЕСТ ТРАНЗАКЦИИ " + transaction);
        Message message = new Message();
        try {
            bankDbService.transaction(transaction);
            message.setMessage("OK");
        } catch (ExcessAmountException | NoSuchElementException e) {
            message.setMessage(e.getMessage());
        }
        transactionGoodCounter.increment();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Поиск пользователя по имени.
     * @param name имя пользователя.
     * @return пользователь.
     */
    @GetMapping("/user/{name}")
    public ResponseEntity<User> findUserByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(userDbService.findUserByUsername(name), HttpStatus.OK);
    }

}
