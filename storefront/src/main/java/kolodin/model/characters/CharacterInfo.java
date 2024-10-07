package kolodin.model.characters;

import lombok.Data;

/**
 * Информационная часть страницы о посетителях магазина
 */
@Data
public class CharacterInfo {
    /**
     * Общее количество граждан
     * готовых зарегистрироваться.
     */
    private Integer count;

    /**
     * Количество страниц в списке граждан.
     */
    private Integer pages;

    /**
     * Номер следующей страницы.
     */
    private String next;

    /**
     * Номер предыдущей страницы.
     */
    private String prev;
}
