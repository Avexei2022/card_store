package ru.gb.group5984.service.db;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.model.clients.Client;
import ru.gb.group5984.model.clients.ClientsList;
import ru.gb.group5984.model.clients.ClientsListInfo;
import ru.gb.group5984.model.exceptions.ExcessAmountException;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.model.visitors.CharacterInfo;
import ru.gb.group5984.model.visitors.CharacterResult;
import ru.gb.group5984.model.visitors.Characters;
import ru.gb.group5984.repository.ClientsRepository;
import ru.gb.group5984.repository.VisitorRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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
     * Сохранение данных посетителя банка - кандидата перед открытием счета.
     * @param characterResult Данные посетителя.
     */
    @Override
    public void saveOneVisitor(CharacterResult characterResult) throws RuntimeException{
        try {
            visitorRepository.save(characterResult);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Получить полный список посетителей банка - кандидатов на открытие счета.
     * @return список кандидатов.
     */
    @Override
    public Characters getPageBankCandidates(Integer page) {
        page = page - 1;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, 20);
        Page<CharacterResult> characterResultPage = visitorRepository.findAll(pageable);
        Characters characters = new Characters();
        CharacterInfo characterInfo = new CharacterInfo();
        characterInfo.setCount((int) characterResultPage.getTotalElements());
        characterInfo.setPages(characterResultPage.getTotalPages());
        if (characterResultPage.hasPrevious())
            characterInfo.setPrev(String.valueOf(characterResultPage.getNumber()));
        else characterInfo.setPrev(String.valueOf(characterResultPage.getTotalPages()));
        if (characterResultPage.hasNext())
            characterInfo.setNext(String.valueOf(characterResultPage.getNumber() + 2));
        else characterInfo.setNext("1");
        characters.setResults(characterResultPage.toList());
        characters.setInfo(characterInfo);
        return characters;
    }

    /**
     * Удалить посетителя банка из списка кандидатов на открытие счета.
     * @param id уникальный номер кандидата.
     */
    @Override
    public Message deleteVisitorById(Integer id){
        boolean isNotClient = clientsRepository.findAll()
                .stream()
                .filter(client -> client.getClientDetail().getId().equals(id))
                .toList().isEmpty();
        Message message = new Message();
        if (isNotClient) {
            try {
                visitorRepository.deleteById(id);
                message.setMessage("OK");
            } catch (RuntimeException e) {
                message.setMessage("Ошибка при удалении данных: " + e.getMessage());
            }
        } else message.setMessage("Данная персона является клиентом банка." +
                " Удаление из данного списка невозможно.");

        return message;
    }

    /**
     * Открыть счет клиенту.
     * @param id - уникальный номер клиента.
     * Баланс счета устанавливается в 50 у.е.
     */
    //TODO доработать тему баланса счета
    @Override
    public Message saveOneClientById(Integer id) {
        Message message = new Message();
        message.setMessage("");
        CharacterResult characterResult = new CharacterResult();
        try {
            characterResult = visitorRepository.findById(id).orElse(null);
        } catch (RuntimeException e) {
            message.setMessage("Ошибка при поиске в базе данных: "
                    + e.getMessage());
        }
        if (characterResult != null) {
            Client client = new Client();
            client.setClientDetail(characterResult);
            client.setBalance(BigDecimal.valueOf(50000.00));
            try {
                clientsRepository.save(client);
            } catch (RuntimeException e) {
                message.setMessage(message.getMessage() + " Ошибка при сохранении данных клиента: "
                        + e.getMessage());
            }
        } else message.setMessage(message.getMessage() + " Клиент для сохранения не найден. ");
        if (message.getMessage().isEmpty()) message.setMessage("OK");
        return message;
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
    public ClientsList getPageBankClients(Integer page) {
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
     * Удалить клиента банка - закрыть счет.
     * @param id - уникальный номер клиента.
     * @return - сообщение о результатах удаления клиента.
     */
    @TrackUserAction
    @Override
    public Message deleteClientById(Long id) {
        Message message = new Message();
        message.setMessage("");
        try {
            clientsRepository.deleteById(id);
        } catch (RuntimeException e) {
            message.setMessage("Ошибка при удалении клиента: " + e.getMessage());
        }
        if (message.getMessage().isEmpty()) message.setMessage("OK");
        return message;
    }

    /**
     * Сохранить данные о клиенте банка
     * @param client клиент
     */
    @Override
    public Message saveClient(Client client) {
        Message message = new Message();
        try {
            clientsRepository.save(client);
        } catch (RuntimeException e) {
            message.setMessage("Ошибка при сохранении данных о клиенте");
        }
        if (message.getMessage().isEmpty()) message.setMessage("OK");
        return message;
    }

    /**
     * Поиск клиента по id / номеру счета.
     * @param id номер счета.
     * @return - клиент.
     */
    @Override
    public Client findClientById(Long id) {
        return clientsRepository.findById(id).orElseThrow();
    }

    /**
     * Поиск клиента по имени.
     * @param name имя клиента.
     * @return - клиент.
     */
    @Override
    public Client findClientByName(String name) {
        return clientsRepository.findAll()
                .stream()
                .filter(client -> client
                        .getClientDetail()
                        .getName()
                        .equals(name.replaceAll("_", " ")))
                .findFirst().orElseThrow();
    }

    /**
     * Проведение транзакции между счетами клиентов банка
     * @param transaction Данные транзакции
     */
    @Override
    @Transactional
    @TrackUserAction
    public void transaction(Transaction transaction) throws ExcessAmountException, NoSuchElementException {
        Client creditClient = new Client();
        Client debitClient = new Client();
        try {
            creditClient = findClientByName(transaction.getCreditName());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Клиент для списания средств не найден.");
        }
        if (creditClient.getBalance().compareTo(transaction.getTransferAmount()) < 0) {
            throw  new ExcessAmountException("Средств на счете недостаточно");
        }
        try {
            debitClient = findClientByName(transaction.getDebitName());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Клиент для пополнения средств не найден.");
        }
        creditClient.setBalance(creditClient.getBalance().subtract(transaction.getTransferAmount()));
        debitClient.setBalance(debitClient.getBalance().add(transaction.getTransferAmount()));
        try {
            clientsRepository.save(creditClient);
            clientsRepository.save(debitClient);
        } catch (ExcessAmountException e) {
            throw new ExcessAmountException("Ошибка при обновлении данных клиентов.");
        }
    }

}
