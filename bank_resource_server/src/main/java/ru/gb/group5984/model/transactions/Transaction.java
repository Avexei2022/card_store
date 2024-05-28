package ru.gb.group5984.model.transactions;

import lombok.Data;

import java.math.BigDecimal;


/**
 * Данные о транзакции оплаты товаров:
 *  - Уникальное имя покупателя;
 *  - Уникальное имя продавца;
 *  - сумма перевода.
 */
@Data
public class Transaction {
    private String creditName;
    private String debitName;
    private BigDecimal transferAmount;
}
