package group5984.service.api;



import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
import group5984.model.messeges.Message;
import group5984.model.transactions.Transaction;
import group5984.model.users.User;
import group5984.model.visitors.CharacterResult;
import group5984.model.visitors.Characters;

import java.util.List;

/**
 * Интерфейс сервиса базы данных банка
 */
public interface BankApiService {

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
    Message deleteVisitorById(Integer id);

    Characters getPageCandidates(String page) throws RuntimeException;

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
    Message deleteClientById(Integer id);

    /**
     * Сохранить данные о клиенте банка
     * @param client клиент
     */
    Message saveClient(Client client);

}
