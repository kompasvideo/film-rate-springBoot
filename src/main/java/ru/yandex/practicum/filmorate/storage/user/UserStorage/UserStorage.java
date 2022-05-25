package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserResult;

import java.util.List;

public interface UserStorage {
    List<User> findAll();
    User create(User user) throws ValidationException;
    UserResult put(User user) throws ValidationException;
    User userId(int id);
}
