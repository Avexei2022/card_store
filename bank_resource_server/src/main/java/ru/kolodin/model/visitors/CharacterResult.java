package ru.kolodin.model.visitors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Посетители, желающие открыть счет в банке
 * Добавлены аннотации для базы данных
 */
@Entity
@Data
@Table(name = "bankVisitor")
@Setter
@Getter
public class CharacterResult {
    /**
     * Уникальный номер кандидата.
     */
    @Id
    @Column(name = "id")
    private Integer id;
    /**
     * Имя кандидата.
     */
    @Column(name = "name")
    private String name;
    /**
     * Статус кандидата.
     */
    @Column(name = "status")
    private String status;
    /**
     * Особенности кандидата.
     */
    @Column(name="species")
    private String species;
    /**
     * Тип кандидата.
     */
    @Column(name="type")
    private String type;
    /**
     * Пол кандидата.
     */
    @Column(name="gender")
    private String gender;
    /**
     * Фото кандидата.
     */
    @Column(name="image")
    private String image;
    /**
     * Ссылка на страницу кандидата.
     */
    @Column(name="url")
    private String url;
    /**
     * Дата создания.
     */
    @Column(name="created")
    private Date created;
}
