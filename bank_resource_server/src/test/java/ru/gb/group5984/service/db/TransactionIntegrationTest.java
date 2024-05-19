package ru.gb.group5984.service.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.group5984.model.clients.Client;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.model.visitors.CharacterResult;
import ru.gb.group5984.repository.ClientsRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

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

        Transaction transaction = new Transaction();
        transaction.setCreditAccount(1L);
        transaction.setDebitAccount(2L);
        transaction.setTransferAmount(BigDecimal.valueOf(20));

        Client creditNew = createCreditAccount(BigDecimal.valueOf(30));
        Client debitNew = createDebitAccount(BigDecimal.valueOf(70));

        when(clientsRepository.findById(1L)).thenReturn(Optional.of(credit));
        when(clientsRepository.findById(2L)).thenReturn(Optional.of(debit));

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
        creditDetail.setGender("mail");
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
        debitDetail.setName("credit");
        debitDetail.setStatus("credit_status");
        debitDetail.setSpecies("credit_species");
        debitDetail.setType("credit_type");
        debitDetail.setGender("mail");
        debitDetail.setImage("credit_img");
        debitDetail.setUrl("credit_url");
        debitDetail.setCreated(date);

        Client debit = new Client();
        debit.setId(2L);
        debit.setClientDetail(debitDetail);
        debit.setBalance(amount);

        return debit;
    }
}
