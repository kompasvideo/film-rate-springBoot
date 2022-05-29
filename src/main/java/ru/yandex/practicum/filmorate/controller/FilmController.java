package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.Service.FilmService;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmResult;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
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

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        Film lFilm;
        try {
            lFilm = filmService.create(film);
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cause description here");
        }
        return lFilm;
    }

    @PutMapping(value = "/films")
    public Film put(@Valid @RequestBody Film film) {
        FilmResult lFilm;
        try {
            Boolean isFound = false;
            lFilm = filmService.put(film);
            if (! lFilm.isResult()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id < 0");
            }
        }
        catch (ValidationException ex) {
            log.debug("Ошибка при валидации: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cause description here");
        }
        return lFilm.getFilm();
    }

    @GetMapping("/films/{id}")
    public Film film(@PathVariable int id) {
        Film film = filmService.film(id);
        if (film == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "mot found");
        }
        return film;
    }

    /**
     * пользователь ставит лайк фильму
     * @param id
     * @param userId
     */
    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id,userId);
    }

    /**
     * пользователь удаляет лайк
     * @param id
     * @param userId
     */
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        if (! filmService.deleteLike(id,userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "mot found");
        }
    }

    @GetMapping(value = "/films/popular")
    public List<Film> listLikes(@RequestParam(required = false) String count){
        if (count == null){
            count = "10";
        }
        return filmService.listLikes(Integer.parseInt(count));
    }

}
