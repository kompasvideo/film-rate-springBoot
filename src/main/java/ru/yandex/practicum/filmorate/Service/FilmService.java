package ru.yandex.practicum.filmorate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

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
    public void deleteLike(int filmId, int userId){
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.userId(userId);
        film.deleteLike(userId);
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
}
