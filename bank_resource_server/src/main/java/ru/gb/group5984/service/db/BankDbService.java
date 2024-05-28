package ru.gb.group5984.service.db;


import ru.gb.group5984.model.clients.Client;
import ru.gb.group5984.model.clients.ClientsList;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.model.visitors.CharacterResult;
import ru.gb.group5984.model.visitors.Characters;

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
    Characters getPageBankCandidates(Integer page);

    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета
     * @param id Id кандидата
     */
    Message deleteVisitorById(Integer id);

    /**
     * Открыть счет клиенту
     * @param id - id клиента
     * Баланс счета устанавливается в 50 у.е.
     */
    Message saveOneClientById(Integer id);

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
    ClientsList getPageBankClients(Integer page);

    /**
     * Удалить клиента банка - закрыть счет
     * @param id - id клиента
     */
    Message deleteClientById(Long id);

    /**
     * Сохранить данные о клиенте банка
     * @param client клиент
     */
    Message saveClient(Client client);

    /**
     * Поиск клиента по id / номеру счета
     * @param id номер счета
     * @return - клиент
     */
    Client findClientById(Long id);

    /**
     * Поиск клиента по имени.
     * @param name имя клиента.
     * @return - клиент.
     */
    Client findClientByName(String name);

    /**
     * Проведение транзакции между счетами клиентов банка
     * @param transaction Данные транзакции
     */
    void transaction(Transaction transaction);

}
