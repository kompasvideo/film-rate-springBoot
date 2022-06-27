package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    boolean create(Film film);
    List<Film> findAll();
    boolean put(Film film);
    Optional<Film> getFilm(int filmId);
}
