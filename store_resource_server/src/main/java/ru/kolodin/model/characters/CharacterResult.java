package ru.kolodin.model.characters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Товар, находящийся на складе
 * , но не выставленный на продажу,
 * информация о котором хранится в базе данных.
 */
@Entity
@Data
@Table(name = "characters")
@Setter
@Getter
public class CharacterResult {
    /**
     * Уникальный номер товара.
     */
    @Id
    @Column(name = "id")
    private Integer id;
    /**
     * Наименование товара.
     */
    @Column(name = "name")
    private String name;
    /**
     * Статус товара.
     */
    @Column(name = "status")
    private String status;
    /**
     * Особенности товара.
     */
    @Column(name="species")
    private String species;
    /**
     * Тип товара.
     */
    @Column(name="type")
    private String type;
    /**
     * Принадлежность товара.
     */
    @Column(name="gender")
    private String gender;
    /**
     * Изображение товара.
     */
    @Column(name="image")
    private String image;
    /**
     * Ссылка на товар.
     */
    @Column(name="url")
    private String url;
    /**
     * Дата производство товара.
     */
    @Column(name="created")
    private Date created;
}
