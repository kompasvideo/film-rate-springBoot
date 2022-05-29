package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

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

    private Set<Integer> likes = new HashSet<>();

    public Film(@NotEmpty String name, String description, String releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public boolean validate() throws ValidationException {
        if(!StringUtils.hasText(name)) {
            throw new ValidationException("название не может быть пустым");
        }
        if (description.length() > 200) {
            throw new ValidationException("длина description больше 200 символов");
        }
        if(!StringUtils.hasText(description)) {
            throw new ValidationException("description не может быть пустым");
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

    /**
     * пользователь ставит лайк фильму
     * @param userId
     */
    public void addLike(int userId){
        likes.add(userId);
    }

    /**
     * пользователь удаляет лайк
     * @param userId
     */
    public void deleteLike(int userId){
        likes.remove(userId);
    }

    /**
     * возвраящяет количество лайков
     * @return
     */
    public int countLikes(){
        return likes.size();
    }

    public void setId(){
        id = index++;
    }
}