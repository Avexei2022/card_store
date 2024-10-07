package ru.kolodin.model.users;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Пользователь сервиса ресурсов магазина.
 */
@Entity
@Table(name="users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    /**
     * Уникальный номер.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    /**
     * Имя/логин.
     */
    @Column(name = "username")
    private String username;
    /**
     * Пароль.
     */
    @Column(name="password")
    private String password;
    /**
     * Полномочия - роль.
     */
    @Column(name="role")
    private Role role;
    /**
     * Действующий или нет.
     */
    @Column(name = "enabled")
    private Boolean enabled;
    /**
     * Адрес электронной почты.
     */
    @Column(name = "email")
    private String email;
    /**
     * Подписчик или нет.
     */
    @Column(name = "subscribe")
    private Boolean isSubscribe;
    /**
     * Получить список полномочия пользователя.
     * @return список полномочий.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    /**
     * Истек срок действия аккаунта или нет.
     * @return true - не истек, false - истек.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Заблокирован аккаунт или нет.
     * @return true - не заблокирован, false - заблокирован.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Истек срок действия полномочий или нет.
     * @return true - не истек, false - истек.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Действующий пользователь или нет.
     * @return true - действующий, false - недействующий.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
