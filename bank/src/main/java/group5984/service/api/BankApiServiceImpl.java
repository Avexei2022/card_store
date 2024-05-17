package group5984.service.api;

import group5984.auth.AuthenticationService;
import group5984.configuration.BasicConfig;
import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
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
 * Сервис базы данных банка
 * - посетители банка;
 * - клиенты банка, открывшие счет;
 */
@Service
@RequiredArgsConstructor
@Log
public class BankApiServiceImpl implements BankApiService {
    private final BasicConfig basicConfig;
    private final AuthenticationService authenticationService;
    @Autowired
    private RestTemplate restTemplate;



    @Override
    public void saveOneVisitor(CharacterResult characterResult) {

    }

    @Override
    public List<CharacterResult> getAllBankCandidates() {
        return List.of();
    }

    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета
     * @param id Id кандидата
     */
    @Override
    public Message deleteVisitorById(Integer id) {
        String url = basicConfig.getBANK_API() + "/candidates/delete_from_bank/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
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

    /**
     * Получить страницу со списком персонажей с ресурса Rick and Morty
     * - они же потенциальные клиенты банка.
     * @param page номер страницы.
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    @Override
    public Characters getPageCandidates(String page) throws RuntimeException{
        String url = basicConfig.getBANK_API() + "/candidates/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
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
     * Открыть счет клиенту
     * @param id - id клиента
     * Баланс счета устанавливается в 50 у.е.
     */
    //TODO доработать тему баланса счета
    @Override
    public Message saveOneClientById(Integer id) {
        String url = basicConfig.getBANK_API() + "/candidates/add_to_client/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
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

    @Override
    public List<CharacterResult> getAllClients() {
        return List.of();
    }


    /**
     * Получить список клиентов банка постранично.
     * @param page - запрашиваемая пользователем страница
     * @return список клиентов
     * По умолчанию страница содержит информацию о 20 клиентах
     * Список клиентов дополнен следующей информацией о нем:
     * - общее количество клиентов банка;
     * - количество страниц;
     * - номера текущей, предыдущей и следующей страниц.
     * Если предыдущей страницы нет, то проставляется номер последней страницы.
     * Если следующей страницы нет, то проставляется номер первой страницы
     */

    @Override
    public ClientsList getPageBankClients(Integer page) throws RuntimeException {
        String url = basicConfig.getBANK_API() + "/clients/page/" + page;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<ClientsList> responseType = ClientsList.class;
        log.info("URI - " + url);
        try {
            ResponseEntity<ClientsList> response = restTemplate
                    .exchange(url, method, requestEntity, responseType);
            return response.getBody();
        } catch (RuntimeException e) {
            throw new RuntimeException("Ресурс временно недоступен: " + e.getMessage());
        }
    }

    /**
     * Удалить клиента банка - закрыть счет
     * @param id - id клиента
     */
    @Override
    public Message deleteClientById(Integer id) {
        String url = basicConfig.getBANK_API() + "/clients/delete_client/" + id;
        HttpMethod method = HttpMethod.GET;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
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

    /**
     * Сохранить данные о клиенте банка
     * @param client клиент
     */
    @Override
    public Message saveClient(Client client) {

        String url = basicConfig.getBANK_API() + "/clients/update";
        HttpMethod method = HttpMethod.POST;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("URI - " + url);

        try {
            ResponseEntity<Message> response = restTemplate.postForEntity(url, client, Message.class);
            return response.getBody();
        } catch (RuntimeException e) {
            Message message = new Message();
            message.setMessage("Ресурс временно недоступен: " + e.getMessage());
            return message;
        }
    }



}
