package group5984.service.api;


import group5984.model.visitors.Characters;

/**
 * Интерфейс REST сервиса банка
 */
public interface BankApiService {

    /**
     * Получить с сайта Rick and Morty страницу со списком персонажей
     * - они же потенциальные клиенты банка
     * @param url ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком персонажей - посетителей банка, желающих открыть счет.
     */
    Characters getAllCharacters(String url);

    /**
     * Зачислить визитера банка в кандидаты на открытие счета и сохранить его данные в базе данных.
     * @param url ссылка
     */
    void saveOneCharacterById(String url);


}
