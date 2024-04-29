package group5984.model.storage;

import lombok.Data;


@Data
public class CardsInfo {
    private Long count;
    private Integer pages;
    private Integer next;
    private Integer current;
    private Integer prev;
}
