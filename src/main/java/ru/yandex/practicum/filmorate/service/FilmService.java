package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserDbStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private int maxId;
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;

    @Autowired
    public FilmService( FilmDbStorage filmStorage, UserDbStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    /**
     * пользователь ставит лайк фильму
     * @param filmId
     * @param userId
     */
    public void addLike(int filmId, int userId) {
        getFilmById(filmId);
        getUserById(userId);

        filmStorage.insertLike(filmId, userId);
    }

    /**
     * пользователь удаляет лайк
     * @param id
     * @param userId
     */
    public void deleteLike(int id, int userId) {
        getFilmById(id);
        getUserById(userId);

        filmStorage.deleteLike(id, userId);
    }

    public List<Film> listLikes() {
        return filmStorage.listLakes();
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        validateFilm(film);

        film.setId(++maxId);
        if (!filmStorage.create(film)) {
            throw new ValidationException("Фильм с таким id уже существует:"  + film.getName());
        }
        return getFilmById(film.getId());
    }

    public Film put(Film film) {
        validateFilm(film);

        if (!filmStorage.put(film)) {
            throw new NotFoundException("Нет такого id: " + film.getName());
        }
        return film.getGenres() != null && film.getGenres().size() == 0 ? film : getFilmById(film.getId());
    }

    public Film film(int id) {
        return  getFilmById(id);
    }


    public List<Film> getPopularList(int count) {
        return filmStorage.getPopularList(count);
    }




    public List<Mpa> getAllMpa() {
        return filmStorage.getAllMpa();
    }

    public Mpa getMpa(int mpaId) {
        Optional<Mpa> mpa = filmStorage.getMpaById(mpaId);
        if (mpa.isEmpty()) {
            throw new NotFoundException("Нет такого id mpa:" + mpaId);
        }
        return mpa.get();
    }

    public List<Genre> getAllGenres() {
        return filmStorage.getListGenres();
    }

    public Genre getGenre(int genreId) {
        Optional<Genre> genre = filmStorage.getGenreToId(genreId);
        if (genre.isEmpty()) {
            throw new NotFoundException(String.format("Жанр с id: %s не найден!", genreId));
        }
        return genre.get();
    }

    public void validateFilm(Film film) {
        if (film == null) {
            throw new ValidationException("Пустой объект");
        }
        if (film.getName().isEmpty()) {
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().isEmpty() || film.getDescription().length() >= 200) {
            throw new ValidationException("Описание не может быть пустым, максимальная длина - 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Длина фильма должна быть > 0");
        }
    }

    public Film getFilmById(int filmId) {
        Optional<Film> film = filmStorage.getFilm(filmId);
        return film.orElseThrow(() -> new NotFoundException("Фильм не найден с id " + filmId));
    }

    public User getUserById(int userId) {
        Optional<User> user = userStorage.userById(userId);
        return user.orElseThrow(() ->  new NotFoundException("Нет usera с id:" + userId));
    }
}