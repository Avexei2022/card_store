package ru.gb.group5984.model.users;

import lombok.*;

import java.util.*;

/**
 * Класс пользователя.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    /**
     * Уникальный номер.
     */
    private Long id;

    /**
     * Имя/логин.
     */
    private String username;

    /**
     * Пароль.
     */
    private String password;

    /**
     * Полномочия - роль.
     */
    private String role;

    /**
     * Действующий или нет.
     */
    private boolean enabled;

    /**
     * Истек срок действия данных или нет.
     */
    private boolean credentialsNonExpired;

    /**
     * Истек срок действия учетной записи или нет.
     */
    private boolean accountNonExpired;

    /**
     * Заблокирована учетная запись или нет.
     */
    private boolean accountNonLocked;

    /**
     * Список ролей.
     */
    private ArrayList<Map<String, String>> authorities;


}
