package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public boolean create(User user) {
        if (users.containsKey(user.getId())) {
            return false;
        }
        users.put(user.getId(), user);
        log.info("Пользователь {} успешно добавлен", user);
        return true;
    }

    @Override
    public boolean put(User user) {
        if (!users.containsKey(user.getId())) {
            return false;
        }
        users.put(user.getId(), user);
        log.info("Пользователь {} успешно обновлен", user);
        return true;
    }

    @Override
    public Optional<User> userById(int id) {
        return Optional.of(users.get(id));
    }
}
