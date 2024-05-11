package group5984.service.api;


import group5984.model.messeges.Message;
import group5984.model.visitors.Characters;

/**
 * Интерфейс REST сервиса банка
 */
public interface CharactersApiService {

    /**
     * Получить страницу со списком персонажей.
     * - они же потенциальные клиенты банка.
     * @param page номер страницы в списке.
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    Characters getPageCharacters(String page);

    /**
     * Зачислить визитера банка в кандидаты на открытие счета и сохранить его данные в базе данных.
     * @param id уникальный номер персонажа - посетителя банка.
     */
    Message saveOneCharacterById(Integer id);


}
