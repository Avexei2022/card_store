package ru.gb.group5984.model.characters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Товар, находящийся на складе, но не выставленный на продажу,
 * информация о котором хранится в базе данных.
 */

@Entity
@Data
@Table(name = "characters")
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
