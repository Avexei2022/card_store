package ru.kolodin.service.db;

import ru.kolodin.model.clients.Client;
import ru.kolodin.model.clients.ClientsList;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.transactions.Transaction;
import ru.kolodin.model.visitors.CharacterResult;
import ru.kolodin.model.visitors.Characters;

import java.util.List;

/**
 * Интерфейс сервиса базы данных банка.
 */
public interface BankDbService {

    /**
     * Сохранение данных посетителя банка - кандидата перед открытием счета
     * @param characterResult данные посетителя
     */
    void saveOneVisitor(CharacterResult characterResult);

    /**
     * Получить полный список посетителей банка - кандидатов на открытие счета.
     * @param page номер страницы в списке кандидатов.
     * @return список кандидатов.
     */
    Characters getPageBankCandidates(Integer page);

    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета.
     * @param id уникальный номер кандидата.
     * @return сообщение о результате удаления.
     */
    Message deleteVisitorById(Integer id);

    /**
     * Открыть счет клиенту.
     * @param id уникальный номер кандидата.
     * @return сообщение о результате сохранения.
     */
    Message saveOneClientById(Integer id);

    /**
     * Получить список клиентов банка.
     * @return список клиентов.
     */
    List<CharacterResult> getAllClients();

    /**
     * Получить список клиентов банка постранично.
     * @param page - запрашиваемая пользователем страница.
     * @return список клиентов.
     */
    ClientsList getPageBankClients(Integer page);

    /**
     * Удалить клиента банка - закрыть счет.
     * @param id уникальный номер кандидата.
     * @return сообщение о результате сохранения.
     */
    Message deleteClientById(Long id);

    /**
     * Сохранить данные о клиенте банка.
     * @param client клиент.
     * @return сообщение о результате сохранения.
     */
    Message saveClient(Client client);

    /**
     * Поиск клиента по id / номеру счета.
     * @param id номер счета.
     * @return - клиент.
     */
    Client findClientById(Long id);

    /**
     * Поиск клиента по имени.
     * @param name имя клиента.
     * @return - клиент.
     */
    Client findClientByName(String name);

    /**
     * Проведение транзакции между счетами клиентов банка.
     * @param transaction Данные транзакции.
     */
    void transaction(Transaction transaction);

}
