package ru.gb.group5984.service.db;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.group5984.model.clients.Client;
import ru.gb.group5984.model.exceptions.ExcessAmountException;
import ru.gb.group5984.model.transactions.Transaction;
import ru.gb.group5984.model.visitors.CharacterResult;
import ru.gb.group5984.repository.ClientsRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Тест транзакции.
 */
@ExtendWith(MockitoExtension.class)
public class BankDbServiceTransactionTest {
    @InjectMocks
    private BankDbServiceImpl bankDbService;
    @Mock
    private ClientsRepository clientsRepository;

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

        Client creditNew = credit.clone();
        creditNew.setBalance(BigDecimal.valueOf(30));
        Client debitNew = debit.clone();
        debitNew.setBalance(BigDecimal.valueOf(70));

        given(clientsRepository.findById(1L)).willReturn(Optional.of(credit));
        given(clientsRepository.findById(2L)).willReturn(Optional.of(debit));

        //Блок действия
        bankDbService.transaction(transaction);

        //Блок проверки
        verify(clientsRepository).save(creditNew);
        verify(clientsRepository).save(debitNew);

    }

    /**
     * Проверка исключения при отсутствии лицевого счета.
     */
    @Test
    public void transactionAccountNotFoundTest() {
        //Блок предусловия
        Client credit = createCreditAccount(BigDecimal.valueOf(50));

        Transaction transaction = new Transaction();
        transaction.setCreditAccount(1L);
        transaction.setDebitAccount(2L);
        transaction.setTransferAmount(BigDecimal.valueOf(20));

        Client creditNew = credit.clone();
        creditNew.setBalance(BigDecimal.valueOf(30));


        given(clientsRepository.findById(1L)).willReturn(Optional.of(credit));
        given(clientsRepository.findById(2L)).willReturn(Optional.empty());

        //Блок действия
        assertThrows(NoSuchElementException.class, () -> bankDbService.transaction(transaction));

        //Блок проверки
        verify(clientsRepository, never()).save(any());
    }

    /**
     * Проверка исключения при недостатке средств на счете.
     */
    @Test
    public void transactionAmountExceptionTest() {
        //Блок предусловия
        Client credit = createCreditAccount(BigDecimal.valueOf(50));
        Transaction transaction = new Transaction();
        transaction.setCreditAccount(1L);
        transaction.setDebitAccount(2L);
        transaction.setTransferAmount(BigDecimal.valueOf(100));

        given(clientsRepository.findById(1L)).willReturn(Optional.of(credit));

        //Блок действия
        assertThrows(ExcessAmountException.class, () -> bankDbService.transaction(transaction));

        //Блок проверки
        verify(clientsRepository, never()).save(any());
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
        creditDetail.setCreated(new Date());

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
        debitDetail.setCreated(new Date());

        Client debit = new Client();
        debit.setId(2L);
        debit.setClientDetail(debitDetail);
        debit.setBalance(amount);

        return debit;
    }
}
