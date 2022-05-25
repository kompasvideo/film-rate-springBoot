package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film film) throws ValidationException;
    List<Film> findAll();
    FilmResult put(Film film) throws ValidationException;
    Film getFilm(int filmId);
    Collection<Film> listLikes(int count);
    Film film(int id);
}
