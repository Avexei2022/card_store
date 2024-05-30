package group5984.service.api;


import group5984.model.messeges.Message;
import group5984.model.visitors.Characters;

/**
 * Интерфейс сервиса взаимодействия с Rest-сервисом ресурсов банка
 * в части посетителей банка.
 */
public interface CharactersApiService {

    /**
     * Получить страницу со списком посетителей банка.
     * @param page номер страницы в списке.
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    Characters getPageCharacters(String page);

    /**
     * Зачислить посетителя банка в кандидаты на открытие счета.
     * @param id уникальный номер персонажа - посетителя банка.
     * @return сообщение о результате сохранения кандидата в базе данных.
     */
    Message saveOneCharacterById(Integer id);


}
