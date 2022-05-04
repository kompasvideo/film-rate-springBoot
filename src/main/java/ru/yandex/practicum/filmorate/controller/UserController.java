package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@Slf4j
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public List<User> findAll() {
        return users;
    }

    @PostMapping(value = "/user")
    public User create(@Valid @RequestBody User user) {
        try {
            user.validation();
            users.add(user);
            log.info("Новый пользователь: {}", user);
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
        }
        return user;
    }

    @PatchMapping(value = "/user")
    public User patch(@Valid @RequestBody User user) {
        try {
            for (User lUser: users) {
                if(lUser.getId() == user.getId()) {
                    user.validation();
                    lUser.setEmail(user.getEmail());
                    lUser.setLogin(user.getLogin());
                    lUser.setName(user.getName());
                    lUser.setBirthday(user.getBirthday());
                    log.info("Обновлены данные пользователя: {}", user);
                }
            }
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
        }
        return user;
    }
}
