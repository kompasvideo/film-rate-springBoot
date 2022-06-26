package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(Film film) {
        String sql =
            "INSERT INTO film (name, description, releaseDate, duration, rate, mpa_id) VALUES (?, ?, ?, ?, ?, ?)";
        if (jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId()) > 0) {
            if (film.getGenres() != null) {
                for (Genre genre : film.getGenres()) {
                    insertGenre(film.getId(), genre.getId());
                }
            }
            return true;
        } else return false;
    }

    @Override
    public boolean put(Film film) {
        String sql =
            "UPDATE film SET name=?, description=?, releaseDate=?, duration=?, mpa_id=? WHERE film_id=?";
        if (jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()) > 0) {
            deleteGenre(film.getId());
            if (film.getGenres() != null) {
                for (Genre genre : film.getGenres()) {
                    insertGenre(film.getId(), genre.getId());
                }
            }
            return true;
        } else return false;
    }

    @Override
    public Optional<Film> getFilm(int filmId) {
        try {
            String sql =
                "SELECT film_id, name, description, releaseDate, duration, rate, mpa_id FROM film WHERE film_id=?";
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> buildFilm(rs, rowNum), filmId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM film";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildFilm(rs, rowNum));
    }

    public List<Film> listLakes() {
        String sql = "SELECT film_id, name, description, releaseDate, duration, rate, mpa_id FROM film WHERE rate>0";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildFilm(rs, rowNum));
    }

    public List<Film> getPopularList(int count) {
        String sql =
            "SELECT f.film_id, f.name, f.description, f.releaseDate, f.duration, f.rate, f.mpa_id, COUNT(fl.film_id) cnt " +
                "FROM film f LEFT JOIN film_likes fl ON f.film_id=fl.film_id " +
                "GROUP BY f.film_id ORDER BY cnt DESC LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildFilm(rs, rowNum), count == 0 ? 10 : count);
    }

    private Film buildFilm(ResultSet row, int rowNum) throws SQLException {
        return Film.builder()
                .id(row.getInt("film_id"))
                .name(row.getString("name"))
                .description(row.getString("description"))
                .releaseDate(row.getDate("releaseDate").toLocalDate())
                .duration(row.getInt("duration"))
                .rate(row.getInt("rate"))
                .mpa(getMpaById(row.getInt("mpa_id")).get())
                .genres(getListGenre(row.getInt("film_id")))
                .build();
    }

    public Optional<Mpa> getMpaById(int mpaId) {
        try {
            String sql = "SELECT * FROM mpa WHERE mpa_id=?";
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> buildMpa(rs, rowNum), mpaId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildMpa(rs, rowNum));
    }

    private Mpa buildMpa(ResultSet row, int rowNum) throws SQLException {
        return Mpa.builder()
            .id(row.getInt("mpa_id"))
            .name(row.getString("name"))
            .build();
    }

    public boolean insertGenre(int filmId, int genreId) {
        try {
            String sql = "MERGE INTO film_genres KEY (film_id, genre_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, filmId, genreId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteGenre(int filmId) {
        try {
            String sql = "DELETE FROM film_genres WHERE film_id=?";
            jdbcTemplate.update(sql, filmId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<Genre> getListGenre(int filmId) {
        String sql = "SELECT g.id, g.name FROM film_genres fg LEFT JOIN genres g on fg.genre_id = g.id " +
            "WHERE fg.film_id=? ORDER BY g.id";
        List<Genre> genreList = jdbcTemplate.query(sql, (rs, rowNum) -> buildGenre(rs, rowNum), filmId);
        return genreList;
    }

    private Genre buildGenre(ResultSet row, int rowNum) throws SQLException {
        return Genre.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .build();
    }

    public boolean insertLike(int filmId, int userId) {
        try {
            String sql = "INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, filmId, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteLike(int filmId, int userId) {
        try {
            String sql = "DELETE FROM film_likes WHERE film_id=? AND user_id=?";
            jdbcTemplate.update(sql, filmId, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Genre> getGenreToId(int genreId) {
        try {
            String sql = "SELECT * FROM genres WHERE id=?";
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> buildGenre(rs, rowNum), genreId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Genre> getListGenres() {
        String sql = "SELECT * FROM genres";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildGenre(rs, rowNum));
    }
}