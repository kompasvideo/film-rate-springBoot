package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private int maxId;
    private final UserDbStorage userStorage;

    @Autowired
    public UserService(UserDbStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User postUser(User user) {
        validateUser(user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        user.setId(++maxId);
        if (!userStorage.create(user)) {
            throw new ValidationException(String.format("Нет usera: %s", user.getName()));
        }
        return getUserById(user.getId());
    }

    public User putUser(User user) {
        validateUser(user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        if (!userStorage.put(user)) {
            throw new NotFoundException(String.format("Нет usera: %s", user.getName()));
        }
        return getUserById(user.getId());
    }

    public User getUser(int id) {
        return getUserById(id);
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public void addFriend(int userId, int friendId) {
        getUserById(userId);
        getUserById(friendId);

        userStorage.insertFriends(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        getUserById(userId);
        getUserById(friendId);

        userStorage.deleteFriends(userId, friendId);
    }

    public List<User> listFriends(int userId) {
        return userStorage.getAllFriends(userId);
    }

    public List<User> listOtherFriends(int userId, int otherId) {
        return userStorage.getFriends(userId, otherId);
    }

    public void validateUser(User user) {
        if (user == null) {
            throw new ValidationException("Пустой объект");
        }
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробелы");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не оджна быть в будущем");
        }
    }

    public User getUserById(int userId) {
        Optional<User> user = userStorage.userById(userId);
        return user.orElseThrow(() ->  new NotFoundException("Нет usera с id:" + userId));
    }
}

