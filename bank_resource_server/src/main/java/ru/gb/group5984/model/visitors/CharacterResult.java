package ru.gb.group5984.model.visitors;

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
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name="species")
    private String species;
    @Column(name="type")
    private String type;
    @Column(name="gender")
    private String gender;
    @Column(name="image")
    private String image;
    @Column(name="url")
    private String url;
    @Column(name="created")
    private Date created;
}
