package ru.gb.group5984.service.db;

import ru.gb.group5984.model.users.User;

public interface UserDbService {
    User findUserByUsername(String username);
}
