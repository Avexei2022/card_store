package ru.kolodin.model.transactions;

import lombok.Data;

import java.math.BigDecimal;


/**
 * Данные о транзакции оплаты товаров:
 */
@Data
public class Transaction {

    /**
     * Уникальное имя/логи покупателя.
     */
    private String creditName;

    /**
     * Уникальное имя/логин продавца.
     */
    private String debitName;

    /**
     * Сумма перевода.
     */
    private BigDecimal transferAmount;
}
