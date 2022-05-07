package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmControllerTests {

	/**
	 * FilmController проверка на POST и GET запросы
	 */
	@Test
	public void shouldPostFilmAndGetAllFilms() {
		FilmController filmController = new FilmController();
		Film film1 = new Film("Name","duration", "2018-01-01", 90);
	    filmController.create(film1);
		Film film2 = new Film("Name2","duration2", "2020-02-02", 85);
		filmController.create(film2);
		List<Film> films = filmController.findAll();
		assertEquals(2, films.size());
		for (Film film : films) {
			if (film.getId() == film1.getId()) {
				assertAll(
						() -> assertEquals(film.getName(), film1.getName()),
						() -> assertEquals(film.getDescription(), film1.getDescription()),
						() -> assertEquals(film.getReleaseDate(), film1.getReleaseDate()),
						() -> assertEquals(film.getDuration(), film1.getDuration())
				);
			} else if (film.getId()== film2.getId()) {
				assertAll(
						() -> assertEquals(film.getName(), film2.getName()),
						() -> assertEquals(film.getDescription(), film2.getDescription()),
						() -> assertEquals(film.getReleaseDate(), film2.getReleaseDate()),
						() -> assertEquals(film.getDuration(), film2.getDuration())
				);
			}
		}
	}

	/**
	 * FilmController проверка на PATCH запрос
	 */
	@Test
	public void shouldPatchFilmAndReturnPatchedFilm() {
		FilmController filmController = new FilmController();
		Film film1 = new Film("Name","description", "2018-01-01", 90);
		filmController.create(film1);
		film1.setName("Name2");
		film1.setDescription("description2");
		film1.setReleaseDate( "2020-02-02");
		film1.setDuration(85);
		filmController.put(film1);
		List<Film> films = filmController.findAll();
		assertEquals(1, films.size());
		for (Film film : films) {
			if (film.getId() == film1.getId()) {
				assertAll(
						() -> assertEquals(film.getName(), film1.getName()),
						() -> assertEquals(film.getDescription(), film1.getDescription()),
						() -> assertEquals(film.getReleaseDate(), film1.getReleaseDate()),
						() -> assertEquals(film.getDuration(), film1.getDuration())
				);
			}
		}
	}

	@Test
	public void shouldThrowExceptionNameNoEmpty() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film("","duration", "2018-01-01", 90);
					film.validate();
				});
		assertEquals("название не может быть пустым", exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionDescriptionMax200() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film("name1",
							"durationaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
							"2018-01-01", 90);
					film.validate();
				});
		assertEquals("длина description больше 200 символов", exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionDateBefore1895m12d28() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film("name1","description",
							"1888-01-01", 90);
					film.validate();
				});
		assertEquals("дата reliaseDate раньше 28 декабря 1895 г.", exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionDurationAboveNull() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film("name1","description",
							"2018-01-01", -90);
					film.validate();
				});
		assertEquals("продолжительность фильма duration должна быть положительной", exception.getMessage());
	}
}
