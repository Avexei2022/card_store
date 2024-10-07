package ru.kolodin.service.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kolodin.model.clients.Client;
import ru.kolodin.model.transactions.Transaction;
import ru.kolodin.model.visitors.CharacterResult;
import ru.kolodin.repository.ClientsRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Интеграционный тест перевода денег.
 */
@SpringBootTest
public class TransactionIntegrationTest {
    @Autowired
    private BankDbServiceImpl bankDbService;
    @MockBean
    private ClientsRepository clientsRepository;
    private final Date date = new Date();


    /**
     * Проверка списания и начисления.
     */
    @Test
    public void transactionCorrectFlow() {
        //Блок предусловия
        Client credit = createCreditAccount(BigDecimal.valueOf(50));
        Client debit = createDebitAccount(BigDecimal.valueOf(50));
        List<Client> clientList = new ArrayList<>();
        clientList.add(credit);
        clientList.add(debit);

        Transaction transaction = new Transaction();
        transaction.setCreditName("credit");
        transaction.setDebitName("debit");
        transaction.setTransferAmount(BigDecimal.valueOf(20));

        Client creditNew = createCreditAccount(BigDecimal.valueOf(30));
        Client debitNew = createDebitAccount(BigDecimal.valueOf(70));

        when(clientsRepository.findAll()).thenReturn(clientList);


        //Блок действия
        bankDbService.transaction(transaction);

        //Блок проверки
        verify(clientsRepository).save(creditNew);
        verify(clientsRepository).save(debitNew);

    }

    /**
     * Создание тестового аккаунта кредитора.
     * @param amount баланс средств.
     * @return кредитор.
     */
    private Client createCreditAccount(BigDecimal amount) {
        CharacterResult creditDetail = new CharacterResult();
        creditDetail.setId(1);
        creditDetail.setName("credit");
        creditDetail.setStatus("credit_status");
        creditDetail.setSpecies("credit_species");
        creditDetail.setType("credit_type");
        creditDetail.setGender("credit_mail");
        creditDetail.setImage("credit_img");
        creditDetail.setUrl("credit_url");
        creditDetail.setCreated(date);

        Client credit = new Client();
        credit.setId(1L);
        credit.setClientDetail(creditDetail);
        credit.setBalance(amount);

        return credit;
    }

    /**
     * Создание тестового аккаунта дебитора.
     * @param amount баланс средств.
     * @return дебитор.
     */
    private Client createDebitAccount(BigDecimal amount) {
        CharacterResult debitDetail = new CharacterResult();
        debitDetail.setId(2);
        debitDetail.setName("debit");
        debitDetail.setStatus("debit_status");
        debitDetail.setSpecies("debit_species");
        debitDetail.setType("debit_type");
        debitDetail.setGender("debit_mail");
        debitDetail.setImage("debit_img");
        debitDetail.setUrl("debit_url");
        debitDetail.setCreated(date);

        Client debit = new Client();
        debit.setId(2L);
        debit.setClientDetail(debitDetail);
        debit.setBalance(amount);

        return debit;
    }
}
