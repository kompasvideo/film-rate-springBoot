package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public boolean create(Film film) {
        if (films.containsKey(film.getId())) {
            return false;
        }
        films.put(film.getId(), film);
        log.info(String.format("Фильм %s успешно добавлен", film));
        return true;
    }

    @Override
    public boolean put(Film film) {
        if (!films.containsKey(film.getId())) {
            return false;
        }
        films.put(film.getId(), film);
        log.info(String.format("Фильм %s успешно обновлен", film));
        return true;
    }

    @Override
    public Optional<Film> getFilm(int filmId) {
        return Optional.of(films.get(filmId));
    }

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }
}
