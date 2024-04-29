package group5984.model.basket;

import lombok.Data;

import java.util.List;

@Data
public class Basket {
    BasketInfo info;
    List<CardInBasket> cardInBasketList;
}
