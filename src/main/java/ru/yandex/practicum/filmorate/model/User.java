package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String login;
    private String name;
    @Past
    private LocalDateTime birthday;

    public User(int id, @NotEmpty String email, @NotEmpty String login, String name, LocalDateTime birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public boolean validation() throws ValidationException {
        if(email.equals("") || email.trim().equals("") || !email.contains("@")) {
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if(login.equals("") || login.trim().equals("") || login.contains(" ")) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if(name.equals("") || name.trim().equals("") ) {
            name = login;
        }
        if (birthday.isAfter(LocalDateTime.now())) {
            throw new ValidationException("дата рождения birthday не может быть в будущем");
        }
        return true;
    }
}
