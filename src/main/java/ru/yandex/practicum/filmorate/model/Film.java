package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

@Data
public class Film {
    private int id;
    @NotEmpty
    private String name;
    @Size(max = 200)
    private String description;
    @Past
    private LocalDateTime reliaseDate;
    @Min(0)
    private Duration duration;

    public Film(int id, @NotEmpty String name, String description, LocalDateTime reliaseDate, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reliaseDate = reliaseDate;
        this.duration = duration;
    }

    public boolean validation() throws ValidationException {
        if(name.equals("") || name.trim().equals("")) {
            throw new ValidationException("название не может быть пустым");
        }
        if (description.length() > 200) {
            throw new ValidationException("длина description больше 200 символов");
        }
        LocalDateTime localDateTime = LocalDateTime.of(1895, Month.DECEMBER,28, 0,0);
        if (reliaseDate.isBefore(localDateTime)){
            throw new ValidationException("дата reliaseDate раньше 28 декабря 1895 г.");
        }
        if(duration.compareTo(Duration.ZERO) != 1){
            throw new ValidationException("продолжительность фильма duration должна быть положительной");
        }
        return true;
    }
}
