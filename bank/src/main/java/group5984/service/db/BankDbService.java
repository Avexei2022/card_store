package group5984.service.db;



import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
import group5984.model.transactions.Transaction;
import group5984.model.visitors.CharacterResult;

import java.util.List;

/**
 * Интерфейс сервиса базы данных банка
 */
public interface BankDbService {

    /**
     * Сохранение данных посетителя банка - кандидата перед открытием счета
     * @param characterResult Данные посетителя
     */
    void saveOneVisitor(CharacterResult characterResult);

    /**
     * Получить полный список посетителей банка - кандидатов на открытие счета
     * @return список кандидатов
     */
    List<CharacterResult> getAllBankCandidates();

    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета
     * @param id Id кандидата
     */
    void deleteVisitorById(Integer id);

    /**
     * Открыть счет клиенту
     * @param id - id клиента
     * Баланс счета устанавливается в 50 у.е.
     */
    void saveOneClientById(Integer id);

    /**
     * Получить список клиентов банка
     * @return список клиентов
     */
    List<CharacterResult> getAllClients();

    /**
     * Получить список клиентов банка постранично.
     * @param page - запрашиваемая пользователем страница
     * @return список клиентов
     */
    ClientsList getAllBankClients(Integer page);

    /**
     * Удалить клиента банка - закрыть счет
     * @param id - id клиента
     */
    void deleteClientById(Integer id);

    /**
     * Сохранить данные о клиенте банка
     * @param client клиент
     */
    void saveClient(Client client);

    /**
     * Поиск клиента по id / номеру счета
     * @param id номер счета
     * @return - клиент
     */
    Client findClientById(Long id);

    /**
     * Проведение транзакции между счетами клиентов банка
     * @param transaction Данные транзакции
     */
    void transaction(Transaction transaction);

    /**
     * Откат транзакции
     * @param transaction Данные транзакции
     */
    void rollbackTransaction(Transaction transaction);
}
