package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Data
public class User {
    private static int  index = 1;
    private int id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String login;
    private String name;
    private String birthday;

    public User(@NotEmpty String email, @NotEmpty String login, String name, String birthday) {
        id = index++;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public boolean validation() throws ValidationException {
        if(name.equals("") || name.trim().equals("") ) {
            name = login;
        }
        if(email.equals("") || email.trim().equals("") || !email.contains("@")) {
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if(login.equals("") || login.trim().equals("") || login.contains(" ")) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        try {
            LocalDate localDate = LocalDate.parse(birthday);
            if (localDate.isAfter(LocalDate.now())) {
                throw new ValidationException("дата рождения birthday не может быть в будущем");
            }
        } catch (DateTimeParseException ex) {
            throw new ValidationException("");
        }
        return true;
    }
}
