package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

@Data
public class Film {
    private static int  index = 1;
    private int id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    private String releaseDate;
    private int duration;

    public Film(@NotEmpty String name, String description, String releaseDate, int duration) {
        this.id = index++;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public boolean validation() throws ValidationException {
        if(name.equals("") || name.trim().equals("")) {
            throw new ValidationException("название не может быть пустым");
        }
        if (description.length() > 200) {
            throw new ValidationException("длина description больше 200 символов");
        }
        LocalDate localDate = LocalDate.of(1895, Month.DECEMBER,28);
        try {
            LocalDate reliaseDate1 =LocalDate.parse(releaseDate);
        if (reliaseDate1.isBefore(localDate)){
            throw new ValidationException("дата reliaseDate раньше 28 декабря 1895 г.");
        }
        } catch (DateTimeParseException ex){
            throw new ValidationException("ошибка при парсинге даты");
        }
        if (duration <= 0) {
            throw new ValidationException("продолжительность фильма duration должна быть положительной");
        }
        return true;
    }
}
