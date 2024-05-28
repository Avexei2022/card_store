package ru.gb.group5984.model.transactions;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


/**
 * Данные о транзакции оплаты товаров:
 *  - уникальное имя покупателя;
 *  - уникальное имя продавца;
 *  - сумма перевода.
 */
@Data
@Builder
public class Transaction {
    private String creditName;
    private String debitName;
    private BigDecimal transferAmount;


}
