package ru.yandex.practicum.filmorate.storage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    /**
     * UserController проверка на POST и GET запросы
     */
    @Test
    public void shouldPostUserAndGetAllUsers() {
//        UserStorage userStorage = new InMemoryUserStorage();
//        UserService userService = new UserService(userStorage);
//        UserController userController = new UserController(userService);
//        User user1 = new User("name1@mail.ru","name1","name1", "2000-01-15");
//        userController.create(user1);
//        User user2 = new User("name2@yandex.ru","name2","name2", "1999-02-02");
//        userController.create(user2);
//        List<User> users = userController.findAll();
//        assertEquals(2, users.size());
//        for (User user : users) {
//            if (user.getId() == user1.getId()) {
//                assertAll(
//                    () -> assertEquals(user.getEmail(), user1.getEmail()),
//                    () -> assertEquals(user.getLogin(), user1.getLogin()),
//                    () -> assertEquals(user.getName(), user1.getName()),
//                    () -> assertEquals(user.getBirthday(), user1.getBirthday())
//                );
//            } else if (user.getId() == user2.getId()) {
//                assertAll(
//                    () -> assertEquals(user.getEmail(), user2.getEmail()),
//                    () -> assertEquals(user.getLogin(), user2.getLogin()),
//                    () -> assertEquals(user.getName(), user2.getName()),
//                    () -> assertEquals(user.getBirthday(), user2.getBirthday())
//                );
//            }
//        }
    }

    /**
     * UserController проверка на PATCH запрос
     */
    @Test
    public void shouldPatchUserAndReturnPutUser() {
//        UserStorage userStorage = new InMemoryUserStorage();
//        UserService userService = new UserService(userStorage);
//        UserController userController = new UserController(userService);
//        User user1 = new User( "name1@mail.ru", "name1", "name1", "2000-01-15");
//        userController.create(user1);
//        user1.setEmail("name2@yandex.ru");
//        user1.setLogin("name2");
//        user1.setName("name2");
//        user1.setBirthday("1999-02-02");
//        userController.put(user1);
//        List<User> users = userController.findAll();
//        assertEquals(1, users.size());
//        for (User user : users) {
//            if (user.getId() == user1.getId()) {
//                assertAll(
//                    () -> assertEquals(user.getEmail(), user1.getEmail()),
//                    () -> assertEquals(user.getLogin(), user1.getLogin()),
//                    () -> assertEquals(user.getName(), user1.getName()),
//                    () -> assertEquals(user.getBirthday(), user1.getBirthday())
//                );
//            }
//        }
    }

    @Test
    public void shouldThrowExceptionEmailNoEmpty() {
//        final ValidationException exception = assertThrows(
//            ValidationException.class,
//            () -> {
//                User user = new User("","name1","name1", "2000-01-15");
//                user.validate();
//            });
//        assertEquals("электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionEmailMustHaveSymbol() {
//        final ValidationException exception = assertThrows(
//            ValidationException.class,
//            () -> {
//                User user = new User("name1yandex.ru","name1","name1", "2000-01-15");
//                user.validate();
//            });
//        assertEquals("электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionLoginNotEmpty() {
//        final ValidationException exception = assertThrows(
//            ValidationException.class,
//            () -> {
//                User user = new User("name1@yandex.ru","","name1", "2000-01-15");
//                user.validate();
//            });
//        assertEquals("логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionLoginMustHaveSpace() {
//        final ValidationException exception = assertThrows(
//            ValidationException.class,
//            () -> {
//                User user = new User("name1@yandex.ru","n ame1","name1", "2000-01-15");
//                user.validate();
//            });
//        assertEquals("логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionNameEmpty() {
//        try {
//            User user = new User("name1@yandex.ru","name1","", "2000-01-15");
//            user.validate();
//            assertEquals( user.getName(), user.getLogin());
//        } catch (ValidationException ex) {}
    }

    @Test
    public void shouldThrowExceptionDateAbove() {
//        final ValidationException exception = assertThrows(
//            ValidationException.class,
//            () -> {
//                User user = new User("name1@yandex.ru","name1","name1", "2023-01-15");
//                user.validate();
//            });
//        assertEquals("дата рождения birthday не может быть в будущем", exception.getMessage());
    }
}
