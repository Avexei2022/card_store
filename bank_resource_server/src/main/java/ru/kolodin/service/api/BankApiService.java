package ru.kolodin.service.api;


import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.visitors.Characters;

/**
 * Интерфейс REST сервиса банка.
 */
public interface BankApiService {

    /**
     * Получить с сайта Rick and Morty страницу со списком персонажей
     * - они же потенциальные клиенты банка
     * @param page номер страницы из списка персонажей.
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    Characters getAllCharacters(String page);

    /**
     * Зачислить визитера банка в кандидаты на открытие счета и сохранить его данные в базе данных.
     * @param id уникальный номер персонажа.
     * @return Сообщение о результатах работы метода.
     */
    Message saveOneCharacterById(Integer id);


}
