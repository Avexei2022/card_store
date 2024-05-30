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
     * @return сообщение о результатах удаления кандидата на открытие счета.
     */
    Message deleteVisitorById(Integer id);

    /**
     * Получить страницу из списка кандидатов.
     * @param page номер страницы.
     * @return страница списка кандидатов.
     * @throws RuntimeException исключение в процессе получения списка.
     */
    Characters getPageCandidates(String page) throws RuntimeException;

    /**
     * Открыть счет клиенту.
     * @param id - уникальный номер кандидата банка.
     * @return сообщение о результате сохранения клиента.
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
     * @return сообщение о результате удаления клиента.
     */
    Message deleteClientById(Integer id);

    /**
     * Сохранить данные о клиенте банка.
     * @param client клиент.
     * @return сообщение о результате сохранения данных клиента.
     */
    Message saveClient(Client client);

}
