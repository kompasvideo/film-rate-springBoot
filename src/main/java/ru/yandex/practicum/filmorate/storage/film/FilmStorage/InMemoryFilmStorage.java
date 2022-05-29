package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmResult;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage  implements FilmStorage {
    private List<Film> films = new ArrayList<>();

    public List<Film> findAll() {
        return films;
    }

    public Film create( Film film) throws ValidationException {
        film.validate();
        films.add(film);
        film.setId();
        log.info("Добавлена запись о фильме: {}", film);
        return film;
    }

    public FilmResult put(Film film) throws ValidationException {
        boolean isFound = false;
            for (Film lFilm: films) {
                if(lFilm.getId() == film.getId()) {
                    film.validate();
                    lFilm.setName(film.getName());
                    lFilm.setDescription(film.getDescription());
                    lFilm.setReleaseDate(film.getReleaseDate());
                    lFilm.setDuration(film.getDuration());
                    log.info("Обновлена запись о фильме: {}", film);
                    isFound = true;
                }
            }
            FilmResult filmResult = new FilmResult(film, isFound);
        return filmResult;
    }

    public Film getFilm(int filmId){
        for (Film film: films) {
            if (film.getId() == filmId){
                return film;
            }
        }
        return null;
    }

    /**
     * возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано,
     * верните первые 10
     * @param count
     * @return
     */
    public Collection<Film> listLikes(int count){
        if (count <= 0) count = 10;
        Integer[] mas  = new Integer[films.size()];
        int i = 0;
        for (Film film: films) {
            mas[i++] = film.countLikes();
        }
        Arrays.sort(mas, Collections.reverseOrder());
        Set<Film> filmsL = new HashSet<>();
        for (int k = 0; k < count; k++){
            for (Film film: films) {
                if ((k+1) <= mas.length) {
                    if (film.countLikes() == mas[k]) {
                        filmsL.add(film);
                    }
                }
            }
        }
        return filmsL;
    }

    public Film film(int id) {
        for (Film film: films) {
            if (film.getId() == id) {
                return film;
            }
        }
        return null;
    }
}
