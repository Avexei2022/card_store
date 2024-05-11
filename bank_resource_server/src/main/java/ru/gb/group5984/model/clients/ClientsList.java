package ru.gb.group5984.model.clients;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком клиентов банка.
 * Содержит информационную часть о странице и список клиентов.
 */
@Data
public class ClientsList {
    ClientsListInfo info;
    List<Client> clientList;
}
