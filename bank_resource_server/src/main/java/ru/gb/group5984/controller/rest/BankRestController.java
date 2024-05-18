package ru.gb.group5984.controller.rest;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.model.clients.Client;
import ru.gb.group5984.model.clients.ClientsList;
import ru.gb.group5984.model.exceptions.ExcessAmountException;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.model.visitors.Characters;
import ru.gb.group5984.service.api.BankApiService;
import ru.gb.group5984.service.db.BankDbService;
import ru.gb.group5984.service.db.UserDbService;


/**
 * REST Контроллер банка
 * Проверка свагером http://localhost:8085/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank_server")
@Log
public class BankRestController {

    private final BankApiService serviceApi;
    private final BankDbService serviceDb;
    private final UserDbService userDbService;

    /**
     * Запрос списка "посетителей банка" -
     * в тестовом варианте страницы из списка персонажей с ресурса Rick and Morty.
     * @param page номер страницы в списке "посетителей банка".
     * @return страница из списка "посетителей банка".
     */
    @GetMapping("/visitors/page/{page}")
    public ResponseEntity<Characters> getVisitors(@PathVariable("page") String page) {
        Characters allCharacters = serviceApi.getAllCharacters(page);
        return new ResponseEntity<>(allCharacters, HttpStatus.OK);
    }

    /**
     * Добавить посетителя банка в базу данных кандидатов на открытие счета.
     * @param id уникальный номер посетителя банка.
     * @return Сообщение о результатах.
     */
    @GetMapping("/visitors/add_to_bank/{id}")
    public ResponseEntity<Message> addToBank(@PathVariable("id") Integer id) {
        Message message = serviceApi.saveOneCharacterById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удалить кандидата на открытие счета из базы данных.
     * @param id уникальный номер кандидата.
     * @return Сообщение о результатах.
     */
    @GetMapping("/candidates/delete_from_bank/{id}")
    public ResponseEntity<Message> deleteCandidateFromBank(@PathVariable("id") Integer id) {
        Message message = serviceDb.deleteVisitorById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить список кандидатов на открытие счета
     * @param page номер страницы из списка кандидатов
     * @return страница со списком кандидатов, включая информационную часть страницы
     */
    @GetMapping("/candidates/page/{page}")
    public ResponseEntity<Characters> getPageCandidates(@PathVariable("page") String page) {
        Characters characters = serviceDb.getPageBankCandidates(Integer.valueOf(page));
        return new ResponseEntity<>(characters, HttpStatus.OK);

    }

    /**
     * Добавить кандидата в базу данных клиентов банка - открыть счет.
     * @param id уникальный номер кандидата.
     * @return Сообщение о результатах.
     */
    @GetMapping("/candidates/add_to_client/{id}")
    public ResponseEntity<Message> addCandidateToClient(@PathVariable("id") Integer id) {
        Message message = serviceDb.saveOneClientById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Удаление клиента из базы данных банка - закрыть счет
     * @param id id клиента
     * @return Сообщение о результатах.
     */
    @TrackUserAction
    @GetMapping("/clients/delete_client/{id}")
    public ResponseEntity<Message> deleteClient(@PathVariable("id") Long id) {
        Message message = serviceDb.deleteClientById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить список клиентов банка.
     * @param page номер запрашиваемой пользователем страницы из списка клиентов.
     * @return страница из списка клиентов.
     */
    @GetMapping("/clients/page/{page}")
    public ResponseEntity<ClientsList> getPageCardsInSale(@PathVariable("page") Integer page) {
        ClientsList clientsList = serviceDb.getPageBankClients(page);
        return new ResponseEntity<>(clientsList, HttpStatus.OK);

    }

    /**
     * Обновить данные о клиенте банка.
     * @param client клиент банка.
     * @return Сообщение о результатах.
     */
    @PostMapping("/clients/update")
    public ResponseEntity<Message> updateClient(Client client) {
        Message message = serviceDb.saveClient(client);
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
            serviceDb.transaction(transaction);
            message.setMessage("OK");
        } catch (ExcessAmountException e) {
            message.setMessage(e.getMessage());
        }
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
