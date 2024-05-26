package group5984.service.api;

import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
import group5984.model.messeges.Message;
import group5984.model.visitors.CharacterResult;
import group5984.model.visitors.Characters;


/**
 * Интерфейс сервиса взаимодействия с Rest-сервисом ресурсов банка.
 */
public interface BankApiService {

    /**
     * Сохранение данных посетителя банка - кандидата перед открытием счета.
     * @param characterResult данные посетителя.
     */
    void saveOneVisitor(CharacterResult characterResult);


    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета.
     * @param id уникальный номер кандидата.
     */
    Message deleteVisitorById(Integer id);

    Characters getPageCandidates(String page) throws RuntimeException;

    /**
     * Открыть счет клиенту.
     * @param id - уникальный номер кандидата банка.
     */
    Message saveOneClientById(Integer id);


    /**
     * Получить список клиентов банка постранично.
     * @param page - запрашиваемая пользователем страница из списка клиентов банка.
     * @return страница списка клиентов.
     */
    ClientsList getPageBankClients(Integer page);

    /**
     * Удалить клиента банка - закрыть счет.
     * @param id - уникальный номер клиента.
     */
    Message deleteClientById(Integer id);

    /**
     * Сохранить данные о клиенте банка.
     * @param client клиент.
     */
    Message saveClient(Client client);

}
