package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.Service.UserService;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@Slf4j
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping(value = "/users")
    public User put(@Valid @RequestBody User user) {
        return userStorage.put(user);
    }

    /**
     * Получение user по id
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    public User userId(@PathVariable int id) {
        return userStorage.userId(id);
    }

    /**
     * добавление в друзья
     * @param id
     * @param friendId
     * @return
     */
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend (id, friendId);
    }

    /**
     * удаление из друзей
     * @param id
     * @param friendId
     */
    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * возвращаем список пользователей, являющихся его друзьями
     * @return
     */
    @GetMapping("/users/{id}/friends")
    public List<User> listFriends(@PathVariable int id) {
        return userService.listFriends(id);
    }

    /**
     * список друзей, общих с другим пользователем
     * @return
     */
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> listOtherFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.listOtherFriends(id, otherId);
    }
}
