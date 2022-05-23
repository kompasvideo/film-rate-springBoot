package ru.yandex.practicum.filmorate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    /**
     * добавление в друзья
     *
     * @param userId
     * @param friendId
     */
    public void addFriend(int userId, int friendId) {
        User user = userStorage.userId(userId);
        User friend = userStorage.userId(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
    }

    /**
     * удаление из друзей
     *
     * @param userId
     * @param friendId
     */
    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.userId(userId);
        user.deleteFriend(friendId);
        User friend = userStorage.userId(friendId);
        friend.deleteFriend(userId);
    }

    /**
     * возвращаем список пользователей, являющихся его друзьями
     *
     * @param userId
     * @return
     */
    public List<User> listFriends(int userId) {
        User user = userStorage.userId(userId);
        Collection<Integer> friendsId = user.listFriends();
        List<User> friends = new ArrayList<>();
        for (int friendId : friendsId) {
            friends.add(userStorage.userId(friendId));
        }
        return friends;
    }


    /**
     * список друзей, общих с другим пользователем
     *
     * @return
     */
    public List<User> listOtherFriends(int userId, int otherId) {
        List<User> friendsUser = listFriends(userId);
        List<User> friendsOther = listFriends(otherId);
        List<User> jointFriends = new ArrayList<>();
        for (User user : friendsUser) {
            for (User user2 : friendsOther) {
                if (user.getId() == user2.getId()) {
                    jointFriends.add(user);
                }
            }
        }
        return jointFriends;
    }
}
