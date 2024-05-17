package group5984.model.users;

import lombok.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Класс пользователя
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private ArrayList<Map<String, String>> authorities;


}
