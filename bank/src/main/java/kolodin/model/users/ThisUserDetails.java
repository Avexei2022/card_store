package kolodin.model.users;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * Аутентификационные данные пользователя веб-сервиса банка.
 * Реализация UserDetails.
 * DaoAuthenticationProvider проверяет UserDetails, а затем возвращает аутентификацию.
 */
@Data
@AllArgsConstructor
public class ThisUserDetails implements UserDetails{

    /**
     * Пользователь.
     */
    private User user;

    /**
     * Полномочия пользователя.
     * @return список полномочий.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return List.of(authority);
    }

    /**
     * Получить пароль пользователя.
     * @return пароль.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Получить имя/логин пользователя.
     * @return имя/логин пользователя.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Истек срок действия учетной записи или нет.
     * @return true - не истек, false - истек.
     */
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    /**
     * Заблокирована учетная запись или нет.
     * @return true - не заблокирован, false - заблокирован.
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    /**
     * Истек срок действия полномочий или нет.
     * @return true - не истек, false - истек.
     */
    @Override
    public boolean isCredentialsNonExpired() {
       return user.isCredentialsNonExpired();
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
