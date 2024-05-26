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


/**
 * Сервиса взаимодействия с Rest-сервисом ресурсов банка.
 */
@Service
@RequiredArgsConstructor
@Log
public class BankApiServiceImpl implements BankApiService {
    private final BasicConfig basicConfig;
    private final AuthenticationService authenticationService;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Сохранить посетителя банка, как кандидата в клиенты банка.
     * @param characterResult данные посетителя.
     */
    @Override
    public void saveOneVisitor(CharacterResult characterResult) {
    }

    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета.
     * @param id уникальный номер кандидата в клиенты.
     * @return сообщение о результатах удаления кандидата из базы данных.
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
     * Получить страницу списка кандидатов в клиенты банка.
     * @param page номер страницы.
     * @return страница списка кандидатов в клиенты банка, желающих открыть счет.
     * @throws RuntimeException Исключение в случае недоступности ресурса.
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
     * Открыть счет клиенту банка.
     * @param id - уникальный номер клиента банка.
     * @return - сообщение о результате.
     */
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

    /**
     * Получить список клиентов банка постранично.
     * @param page - запрашиваемая пользователем страница.
     * @return страница из списка клиентов банка.
     * @throws RuntimeException исключение в случае недоступности ресурса.
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
     * Удалить клиента банка - закрыть счет.
     * @param id - уникальный номер клиента банка.
     * @return - сообщение о результате удаления.
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
     * Сохранить данные о клиенте банка.
     * @param client клиент банка.
     * @return сообщение о результате сохранения.
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
