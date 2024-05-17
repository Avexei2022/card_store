package ru.gb.group5984.model.transactions;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


/**
 * Данные о транзакции оплаты товаров:
 *  - счет покупателя;
 *  - счет продавца;
 *  - сумма перевода.
 */
@Data
@Builder
public class Transaction {
    private Long creditAccount;
    private Long debitAccount;
    private BigDecimal transferAmount;


}
