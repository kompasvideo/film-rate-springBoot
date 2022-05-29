package ru.yandex.practicum.filmorate.model;

public class UserResult {
    private User user;
    private boolean result;

    public UserResult(User user, boolean result) {
        this.user = user;
        this.result = result;
    }


    public User getUser() {
        return user;
    }


    public boolean isResult() {
        return result;
    }
}
