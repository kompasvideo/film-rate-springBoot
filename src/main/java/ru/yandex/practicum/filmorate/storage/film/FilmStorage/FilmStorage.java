package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film film);
    List<Film> findAll();
    Film put(Film film);
    Film getFilm(int filmId);
    Collection<Film> listLikes(int count);
    Film film(int id);
}
