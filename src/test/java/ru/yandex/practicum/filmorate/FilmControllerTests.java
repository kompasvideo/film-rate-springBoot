package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// Александр Вальтер  16:43
// Вчера на вебинаре когда обсуждали 8ФЗ говорили что тесты в этот раз писать не нужно. Значит этот пункт ТЗ пропускать?
// Артур Домбровский  6 мин назад
// Да, все верно. Если будут вопросы со сдачей - можете ссылаться на меня, к контенту схожу.
// https://yandex-students.slack.com/archives/C02RF5NKJE9/p1651672023496709?thread_ts=1651671789.206829&cid=C02RF5NKJE9

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmControllerTests {

	/**
	 * FilmController проверка на POST и GET запросы
	 */
	@Test
	public void testFilmController_GetAll() {
		FilmController filmController = new FilmController();
		Film film1 = new Film(1,"Name","duration", LocalDateTime.of(2018,1,1,0,0), Duration.ofMinutes(90));
	    filmController.create(film1);
		Film film2 = new Film(2,"Name2","duration2", LocalDateTime.of(2020,2,2,0,0), Duration.ofMinutes(85));
		filmController.create(film2);
		List<Film> films = filmController.findAll();
		assertEquals(2, films.size());
		for (Film film : films) {
			if (film.getId() == film1.getId()) {
				assertAll(
						() -> assertEquals(film.getName(), film1.getName()),
						() -> assertEquals(film.getDescription(), film1.getDescription()),
						() -> assertEquals(film.getReliaseDate(), film1.getReliaseDate()),
						() -> assertEquals(film.getDuration(), film1.getDuration())
				);
			} else if (film.getId()== film2.getId()) {
				assertAll(
						() -> assertEquals(film.getName(), film2.getName()),
						() -> assertEquals(film.getDescription(), film2.getDescription()),
						() -> assertEquals(film.getReliaseDate(), film2.getReliaseDate()),
						() -> assertEquals(film.getDuration(), film2.getDuration())
				);
			}
		}
	}

	/**
	 * FilmController проверка на PATCH запрос
	 */
	@Test
	public void testFilmController_Patch() {
		FilmController filmController = new FilmController();
		Film film1 = new Film(1,"Name","duration", LocalDateTime.of(2018,1,1,0,0), Duration.ofMinutes(90));
		filmController.create(film1);
		Film film2 = new Film(1,"Name2","duration2", LocalDateTime.of(2020,2,2,0,0), Duration.ofMinutes(85));
		filmController.patch(film2);
		List<Film> films = filmController.findAll();
		assertEquals(1, films.size());
		for (Film film : films) {
			if (film.getId() == film2.getId()) {
				assertAll(
						() -> assertEquals(film.getName(), film2.getName()),
						() -> assertEquals(film.getDescription(), film2.getDescription()),
						() -> assertEquals(film.getReliaseDate(), film2.getReliaseDate()),
						() -> assertEquals(film.getDuration(), film2.getDuration())
				);
			}
		}
	}

	@Test
	public void shouldThrowExceptionNameNoEmpty() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film(1,"","duration", LocalDateTime.of(2018,1,1,0,0), Duration.ofMinutes(90));
					film.validation();
				});
		assertEquals("название не может быть пустым", exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionDescriptionMax200() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film(1,"name1",
							"durationaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
							LocalDateTime.of(2018,1,1,0,0), Duration.ofMinutes(90));
					film.validation();
				});
		assertEquals("длина description больше 200 символов", exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionDateBefore1895m12d28() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film(1,"name1","description",
							LocalDateTime.of(1895,11,27,0,0), Duration.ofMinutes(90));
					film.validation();
				});
		assertEquals("дата reliaseDate раньше 28 декабря 1895 г.", exception.getMessage());
	}

	@Test
	public void shouldThrowExceptionDurationAboveNull() {
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> {
					Film film = new Film(1,"name1","description",
							LocalDateTime.of(2018,1,1,0,0), Duration.ZERO);
					film.validation();
				});
		assertEquals("продолжительность фильма duration должна быть положительной", exception.getMessage());
	}
}
