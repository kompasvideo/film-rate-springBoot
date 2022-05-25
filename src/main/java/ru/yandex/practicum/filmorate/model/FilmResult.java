package ru.yandex.practicum.filmorate.model;

public class FilmResult {
    private Film film;
    private boolean result;

    public FilmResult(Film film, boolean result) {
        this.film = film;
        this.result = result;
    }

    public Film getFilm() {
        return film;
    }

    public boolean isResult() {
        return result;
    }
}
