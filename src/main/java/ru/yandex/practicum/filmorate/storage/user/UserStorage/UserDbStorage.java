package ru.yandex.practicum.filmorate.storage.user.UserStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean create(User user) {
        String sql = "INSERT INTO users (email, login, name, birthDay) VALUES (?, ?, ?, ?)";
        if (jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday()) > 0) {
            return true;
        } else return false;
    }

    @Override
    public boolean put(User user) {
        String sql = "UPDATE users SET email=?, login=?, name=?, birthDay=? WHERE user_id=?";
        if (jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(),
                user.getId()) > 0) {
            return true;
        } else return false;
    }

    @Override
    public Optional<User> userId(int userId) {
        try {
            String sql = "SELECT user_id, email, login, name, birthDay FROM users WHERE user_id=?";
            return Optional.of(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> buildUser(rs, rowNum), userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT user_id, email, login, name, birthDay FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildUser(rs, rowNum));
    }

    private User buildUser(ResultSet row, int rowNum) throws SQLException {
        return User.builder()
                .id(row.getInt("user_id"))
                .email(row.getString("email"))
                .login(row.getString("login"))
                .name(row.getString("name"))
                .birthday(row.getDate("birthDay").toLocalDate())
                .build();
    }

    public boolean insertFriends(int userId, int friendId) {
        try {
            String sql =
                "INSERT INTO friends (user_id, friend_id, is_confirmed) VALUES (?, ?, 0)";
            jdbcTemplate.update(sql, userId, friendId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteFriends(int userId, int friendId) {
        try {
            String sql =
                "DELETE FROM friends WHERE user_id=? AND friend_id=?";
            jdbcTemplate.update(sql, userId, friendId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<User> getAllFriends(int userId) {
        String sql = "SELECT u.user_id, u.email, u.login, u.name, u.birthday FROM friends f " +
            "LEFT JOIN users u ON f.friend_id=u.user_id WHERE f.user_id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildUser(rs, rowNum), userId);
    }

    public List<User> getFriends(int userId, int otherId) {
        String sql ="SELECT ud.user_id, ud.email, ud.login, ud.name, ud.birthday FROM friends u1 " +
            "LEFT JOIN friends u2 ON u1.friend_id = u2.friend_id " +
            "LEFT JOIN users ud ON u1.friend_id = ud.user_id " +
            "WHERE u1.user_id=? AND u2.user_id=? AND u1.friend_id=u2.friend_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> buildUser(rs, rowNum), userId, otherId);
    }

}