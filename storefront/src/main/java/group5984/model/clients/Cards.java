package group5984.model.clients;

import lombok.Data;

import java.util.List;

/**
 * Страница карточек с информацией о странице
 */
@Data
public class Cards {
    CardsInfo info;
    List<CardsStorage> cardsStorageList;
}
