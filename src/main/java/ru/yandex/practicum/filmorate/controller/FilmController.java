package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/films")
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping("/films")
    public Film put(@Valid @RequestBody Film film) {
        return filmService.put(film);
    }

    @GetMapping("/films/{id}")
    public Film film(@PathVariable int id) {
        return filmService.film(id);
    }

    /**
     * пользователь ставит лайк фильму
     * @param id
     * @param userId
     */
    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
    }

    /**
     * пользователь удаляет лайк
     * @param id
     * @param userId
     */
    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> listLikes(@RequestParam(required = false) Integer count) {
        if (count == null) {
            return filmService.listLikes();
        }
        return filmService.getPopularList(count);
    }

    @GetMapping("/mpa")
    public List<Mpa> getAllMpa() {
        return filmService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return filmService.getMpa(id);
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return filmService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenresById(@PathVariable int id) {
        return filmService.getGenre(id);
    }
}