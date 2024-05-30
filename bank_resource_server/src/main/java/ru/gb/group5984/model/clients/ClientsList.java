package ru.gb.group5984.model.clients;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком клиентов банка.
 */
@Data
public class ClientsList {

    /**
     * Информационная часть страницы клиентов банка.
     */
    ClientsListInfo info;

    /**
     * Список клиентов банка.
     */
    List<Client> clientList;
}
