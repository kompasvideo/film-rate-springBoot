package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

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

    Set<Integer> friends = new HashSet<>();

    public User(@NotEmpty String email, @NotEmpty String login, String name, String birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public boolean validate() throws ValidationException {
        if(!StringUtils.hasText(name)) {
            name = login;
        }
        if(!StringUtils.hasText(email) || !email.contains("@")) {
            throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
        }
        if(!StringUtils.hasText(login)) {
            throw new ValidationException("логин не может быть пустым и содержать пробелы");
        }
        if(login.contains(" ")) {
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

    /**
     * Добавить друга по id
     * @param friendId
     */
    public void addFriend(int friendId){
        friends.add(friendId);
    }

    /**
     * удаление друга по id
     * @param friendId
     */
    public void deleteFriend(int friendId){
        friends.remove(friendId);
    }

    /**
     * возвращаем список пользователей, являющихся его друзьями
     * @return
     */
    public Collection<Integer> listFriends(){
        return friends;
    }
    public void setId(){
        id = index++;
    }
}