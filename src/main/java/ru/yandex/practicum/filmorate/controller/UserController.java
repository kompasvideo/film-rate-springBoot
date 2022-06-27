package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        return userService.postUser(user);
    }

    @PutMapping("/users")
    public User put(@Valid @RequestBody User user) {
        return userService.putUser(user);
    }

    /**
     * Получение user по id
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    public User userById(@PathVariable int id) {
        return userService.getUser(id);
    }

    /**
     * добавление в друзья
     */
    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    /**
     * удаление из друзей
     * @param id
     * @param friendId
     */
    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * возвращаем список пользователей, являющихся его друзьями
     *
     * @return
     */
    @GetMapping("/users/{id}/friends")
    public List<User> listFriends(@PathVariable int id) {
        return userService.listFriends(id);
    }

    /**
     * список друзей, общих с другим пользователем
     * @param id
     * @param otherId
     * @return
     */
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> listOtherFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.listOtherFriends(id, otherId);
    }
}
