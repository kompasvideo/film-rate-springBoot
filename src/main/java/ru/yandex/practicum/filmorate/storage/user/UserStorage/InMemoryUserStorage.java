package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return users;
    }

    public User create(User user) {
        try {
            user.validate();
            users.add(user);
            user.setId();
            log.info("Новый пользователь: {}", user);
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return user;
    }

    public User put(User user) {
        try {
            boolean isFound = false;
            for (User lUser: users) {
                if(lUser.getId() == user.getId()) {
                    user.validate();
                    lUser.setEmail(user.getEmail());
                    lUser.setLogin(user.getLogin());
                    lUser.setName(user.getName());
                    lUser.setBirthday(user.getBirthday());
                    log.info("Обновлены данные пользователя: {}", user);
                    isFound = true;
                }
            }
            if (! isFound) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id < 0");
            }
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cause description here");
        }
        return user;
    }

    public User userId(int id) {
        for (User user: users) {
            if (user.getId() == id){
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found id");
    }

    public Film film(int id) {

        return null;
    }
}
