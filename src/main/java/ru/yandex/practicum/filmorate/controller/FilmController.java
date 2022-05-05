package ru.yandex.practicum.filmorate.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@Slf4j
public class FilmController {
    private List<Film> films = new ArrayList<>();

    @GetMapping("/films")
    public List<Film> findAll() {
        return films;
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        try {
            film.validation();
            films.add(film);
            log.info("Добавлена запись о фильме: {}", film);
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
        }
        return film;
    }

    @PutMapping(value = "/films")
    public Film put(@Valid @RequestBody Film film) {
        try {
            for (Film lFilm: films) {
                if(lFilm.getId() == film.getId()) {
                    film.validation();
                    lFilm.setName(film.getName());
                    lFilm.setDescription(film.getDescription());
                    lFilm.setReleaseDate(film.getReleaseDate());
                    lFilm.setDuration(film.getDuration());
                    log.info("Обновлена запись о фильме: {}", film);
                }
            }
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
        }
        return film;
    }
}
