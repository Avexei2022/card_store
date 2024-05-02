package group5984.service.db;

import group5984.model.clients.Client;
import group5984.model.clients.ClientsList;
import group5984.model.clients.ClientsListInfo;
import group5984.model.exceptoins.ExcessAmountException;
import group5984.model.transactions.Transaction;
import group5984.model.visitors.CharacterResult;
import group5984.repository.ClientsRepository;
import group5984.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Сервис базы данных банка
 * - посетители банка;
 * - клиенты банка, открывшие счет;
 */
@Service
@RequiredArgsConstructor
@Log
public class BankDbServiceImpl implements BankDbService {
    private final VisitorRepository visitorRepository;
    private final ClientsRepository clientsRepository;

    /**
     * Сохранение данных посетителя банка - кандидата перед открытием счета
     * @param characterResult Данные посетителя
     */
    @Override
    public void saveOneVisitor(CharacterResult characterResult) {
        if (characterResult != null) visitorRepository.save(characterResult);
    }

    /**
     * Получить полный список посетителей банка - кандидатов на открытие счета
     * @return список кандидатов
     */
    @Override
    public List<CharacterResult> getAllBankCandidates() {
        return visitorRepository.findAll();
    }

    /**
     * Удалить поетителя банка из списка кандидатов на открытие счета
     * @param id Id кандидата
     */
    @Override
    public void deleteVisitorById(Integer id) {
        visitorRepository.deleteById(id);
    }

    /**
     * Открыть счет клиенту
     * @param id - id клиента
     * Баланс счета устанавливается в 50 у.е.
     */
    //TODO доработать тему баланса счета
    @Override
    public void saveOneClientById(Integer id) {
        CharacterResult characterResult = visitorRepository.findById(id).orElse(null);
        if (characterResult != null) {
            Client client = new Client();
            client.setClientDetail(characterResult);
            client.setBalance(50.00);
            clientsRepository.save(client);
        }
    }

    /**
     * Получить список клиентов банка
     * @return список клиентов
     */
    @Override
    public List<CharacterResult> getAllClients() {
        List<Client> clientList = clientsRepository.findAll();
        return clientList.stream().map(Client::getClientDetail).toList();
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
    public ClientsList getAllBankClients(Integer page) {
        page = page - 1;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        Page<Client> cardsStoragePage = clientsRepository.findAll(pageable);
        ClientsList clientsList = new ClientsList();
        ClientsListInfo clientsListInfo = new ClientsListInfo();
        clientsListInfo.setCount(cardsStoragePage.getTotalElements());
        clientsListInfo.setPages(cardsStoragePage.getTotalPages());
        if (cardsStoragePage.hasPrevious())
            clientsListInfo.setPrev(cardsStoragePage.getNumber());
        else clientsListInfo.setPrev(cardsStoragePage.getTotalPages());
        if (cardsStoragePage.hasNext())
            clientsListInfo.setNext(cardsStoragePage.getNumber() + 2);
        else clientsListInfo.setNext(1);
        clientsListInfo.setCurrent(cardsStoragePage.getNumber() + 1);
        clientsList.setClientList(cardsStoragePage.toList());
        clientsList.setInfo(clientsListInfo);
        return clientsList;
    }

    /**
     * Удалить клиента банка - закрыть счет
     * @param id - id клиента
     */
    @Override
    public void deleteClientById(Integer id) {
        List<Client> clientList = clientsRepository.findAll();
        Long clientId = clientList.stream()
                .filter(client -> Objects.equals(client.getClientDetail().getId(), id))
                .toList().getFirst().getId();
        clientsRepository.deleteById(clientId);
    }

    /**
     * Сохранить данные о клиенте банка
     * @param client клиент
     */
    @Override
    public void saveClient(Client client) {
        clientsRepository.save(client);
    }

    /**
     * Поиск клиента по id / номеру счета
     * @param id номер счета
     * @return - клиент
     */
    @Override
    public Client findClientById(Long id) {
        return clientsRepository.findById(id).orElseThrow();
    }

    /**
     * Проведение транзакции между счетами клиентов банка
     * @param transaction Данные транзакции
     */
    @Override
    @Transactional
    public void transaction(Transaction transaction) {
        Client creditClient = findClientById(transaction.getCreditAccount());
        if (creditClient.getBalance().compareTo(transaction.getTransferAmount()) < 0) {
            throw  new ExcessAmountException("Средств на счете недостаточно");
        }
        Client debitClient = findClientById(transaction.getDebitAccount());
        creditClient.setBalance(creditClient.getBalance() - transaction.getTransferAmount());
        debitClient.setBalance(debitClient.getBalance() + transaction.getTransferAmount());
        clientsRepository.save(creditClient);
        clientsRepository.save(debitClient);
    }

    /**
     * Откат транзакции
     * @param transaction Данные транзакции
     */
    @Override
    @Transactional
    public void rollbackTransaction(Transaction transaction) {
        Client creditClient = findClientById(transaction.getCreditAccount());
        Client debitClient = findClientById(transaction.getDebitAccount());
        creditClient.setBalance(creditClient.getBalance() + transaction.getTransferAmount());
        debitClient.setBalance(debitClient.getBalance() - transaction.getTransferAmount());
        clientsRepository.save(creditClient);
        clientsRepository.save(debitClient);
    }


}
