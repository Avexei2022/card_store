package ru.gb.group5984.model.transactions;

import lombok.Data;

/**
 * Данные о транзакции оплаты товаров:
 *  - счет покупателя;
 *  - счет продавца;
 *  - сумма перевода.
 */
@Data
public class Transaction {
    private Long creditAccount;
    private Long debitAccount;
    private Double transferAmount;
}
