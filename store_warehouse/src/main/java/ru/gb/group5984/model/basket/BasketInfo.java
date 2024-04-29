package ru.gb.group5984.model.basket;

import lombok.Data;


@Data
public class BasketInfo {
    private Long count;
    private Integer pages;
    private Integer next;
    private Integer current;
    private Integer prev;
}
