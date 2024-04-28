package group5984.service.api;

import group5984.model.storage.CardsStorage;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Интерфейс сервиса взаимодействия с сайтом Rick and Morty
 */
public interface ContentApiService {
    /**
     * Получить с сайта Rick and Morty страницу со списком героев
     * @param url ссылка на сайт Rick and Morty в соответствии с документацией
     * @return Страница со списком героев
     */
    Page<CardsStorage> getAllCardsStorage(String url);

    /**
     * Метод сохранения выбранной прльзователем карточки в базе данных
     * @param url ссылка
     */
    void saveOneCardsStorageById(String url);

    void deleteOneCardsStorageById(String url);


}
