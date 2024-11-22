package kolodin.service.api;

import kolodin.aspect.TrackUserAction;
import kolodin.auth.AuthenticationService;
import kolodin.configuration.BasicConfig;
import kolodin.model.clients.Client;
import kolodin.model.clients.ClientsList;
import kolodin.model.messeges.Message;
import kolodin.model.visitors.CharacterResult;
import kolodin.model.visitors.Characters;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Сервиса взаимодействия с API сервиса ресурсов банка.
 */
@Service
@RequiredArgsConstructor
@Log
public class BankApiServiceImpl implements BankApiService {

    /**
     * Конфигуратор базовых настроек.
     */
    private final BasicConfig basicConfig;

    /**
     * Сервис аутентификации.
     */
    private final AuthenticationService authenticationService;

    /**
     * Синхронный клиент REST.
     */
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
    @TrackUserAction
    @Override
    public Message deleteVisitorById(Integer id) {
        String url = basicConfig.getBANK_API() + "/candidates/" + id;
        HttpMethod method = HttpMethod.DELETE;
        HttpEntity<String> requestEntity = authenticationService.getRequestEntity();
        Class<Message> responseType = Message.class;
        log.info("BankApiServiceImpl.deleteVisitorById.URI - " + url);
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
        String url = basicConfig.getBANK_API() + "/candidates/" + id;
        HttpMethod method = HttpMethod.POST;
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
        String url = basicConfig.getBANK_API() + "/clients/" + id;
        HttpMethod method = HttpMethod.DELETE;
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

        String url = basicConfig.getBANK_API() + "/clients";
        HttpMethod method = HttpMethod.PUT;
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
