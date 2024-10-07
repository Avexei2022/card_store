package ru.kolodin.model.users;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Покупатель - пользователь веб-сервиса магазина.
 */
@Entity
@Table(name="buyers")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Buyer implements UserDetails {
    /**
     * Уникальный номер покупателя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    /**
     * Имя/логин покупателя.
     */
    @Column(name = "username")
    private String username;
    /**
     * Пароль покупателя.
     */
    @Column(name="password")
    private String password;
    /**
     * Полномочия покупателя.
     */
    @Column(name="role")
    private Role role;
    /**
     * Действительна учетная запись или нет.
     */
    @Column(name = "enabled")
    private Boolean enabled;
    /**
     * Адрес электронной почты.
     */
    @Column(name = "email")
    private String email;
    /**
     * Является подписчиком или нет.
     */
    @Column(name = "subscribe")
    private Boolean isSubscribe;
    /**
     * Получить список полномочий.
     * @return список полномочий.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    /**
     * Проверить срок действия учетной записи.
     * @return да/нет.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Проверить отсутствие блокировки учетной записи.
     * @return да/нет.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Проверить срок действия данных учетной записи.
     * @return да/нет.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Проверить действительность учетной записи
     * @return да/нет.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
