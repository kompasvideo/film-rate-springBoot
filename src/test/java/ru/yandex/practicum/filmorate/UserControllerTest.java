package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTests {

    /**
     * UserController проверка на POST и GET запросы
     */
    @Test
    public void testUserController_GetAll() {
        UserController userController = new UserController();
        User user1 = new User(1,"name1@mail.ru","name1","name1", LocalDateTime.of(2000,1,15,0,0));
        userController.create(user1);
        User user2 = new User(2,"name2@yandex.ru","name2","name2", LocalDateTime.of(1999,2,2,0,0));
        userController.create(user2);
        List<User> users = userController.findAll();
        assertEquals(2, users.size());
        for (User user : users) {
            if (user.getId() == user1.getId()) {
                assertAll(
                        () -> assertEquals(user.getEmail(), user1.getEmail()),
                        () -> assertEquals(user.getLogin(), user1.getLogin()),
                        () -> assertEquals(user.getName(), user1.getName()),
                        () -> assertEquals(user.getBirthday(), user1.getBirthday())
                );
            } else if (user.getId() == user2.getId()) {
                assertAll(
                        () -> assertEquals(user.getEmail(), user2.getEmail()),
                        () -> assertEquals(user.getLogin(), user2.getLogin()),
                        () -> assertEquals(user.getName(), user2.getName()),
                        () -> assertEquals(user.getBirthday(), user2.getBirthday())
                );
            }
        }
    }

    /**
     * UserController проверка на PATCH запрос
     */
    @Test
    public void testUserController_Patch() {
        UserController userController = new UserController();
        User user1 = new User(1, "name1@mail.ru", "name1", "name1", LocalDateTime.of(2000, 1, 15, 0, 0));
        userController.create(user1);
        User user2 = new User(1, "name2@yandex.ru", "name2", "name2", LocalDateTime.of(1999, 2, 2, 0, 0));
        userController.patch(user2);
        List<User> users = userController.findAll();
        assertEquals(1, users.size());
        for (User user : users) {
            if (user.getId() == user1.getId()) {
                assertAll(
                        () -> assertEquals(user.getEmail(), user2.getEmail()),
                        () -> assertEquals(user.getLogin(), user2.getLogin()),
                        () -> assertEquals(user.getName(), user2.getName()),
                        () -> assertEquals(user.getBirthday(), user2.getBirthday())
                );
            }
        }
    }

    @Test
    public void shouldThrowExceptionEmailNoEmpty() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    User user = new User(1,"","name1","name1", LocalDateTime.of(2000,1,15,0,0));
                    user.validation();
                });
        assertEquals("электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionEmailMustHaveSymbol() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    User user = new User(1,"name1yandex.ru","name1","name1", LocalDateTime.of(2000,1,15,0,0));
                    user.validation();
                });
        assertEquals("электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionLoginNotEmpty() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    User user = new User(1,"name1@yandex.ru","","name1", LocalDateTime.of(2000,1,15,0,0));
                    user.validation();
                });
        assertEquals("логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionLoginMustHaveSpace() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    User user = new User(1,"name1@yandex.ru","n ame1","name1", LocalDateTime.of(2000,1,15,0,0));
                    user.validation();
                });
        assertEquals("логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionNameEmpty() {
        try {
            User user = new User(1,"name1@yandex.ru","name1","", LocalDateTime.of(2000,1,15,0,0));
            user.validation();
            assertEquals( user.getName(), user.getLogin());
        } catch (ValidationException ex) {}
    }

    @Test
    public void shouldThrowExceptionDateAbove() {
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    User user = new User(1,"name1@yandex.ru","name1","name1", LocalDateTime.of(2023,1,15,0,0));
                    user.validation();
                });
        assertEquals("дата рождения birthday не может быть в будущем", exception.getMessage());
    }
}
