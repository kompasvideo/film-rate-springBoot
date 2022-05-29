package ru.yandex.practicum.filmorate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmResult;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage =userStorage;
    }

    /**
     * пользователь ставит лайк фильму
     * @param filmId
     * @param userId
     */
    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId);
        film.addLike(userId);
    }

    /**
     * пользователь удаляет лайк
     * @param filmId
     * @param userId
     */
    public boolean deleteLike(int filmId, int userId){
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.userId(userId);
        if (user == null) return false;
        film.deleteLike(userId);
        return true;
    }

    /**
     * возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано,
     * верните первые 10
     * @param count
     * @return
     */
    public List<Film> listLikes(int count){
        Collection<Film> col = filmStorage.listLikes(count);
        List<Film> films = new ArrayList<>(col);
        return films;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    public FilmResult put( Film film) throws ValidationException {
        return filmStorage.put(film);
    }

    public Film film( int id) {
        return filmStorage.film(id);
    }
}
