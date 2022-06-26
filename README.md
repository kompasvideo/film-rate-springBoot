# java-filmorate
![Схема БД](/Схема_БД.png)
```
Получение всех пользователей
SELECT login,
        name,
        email,
        birthDay
FROM users;

Получение пользователя с id = 2
SELECT login,
        name,
        email,
        birthDay
FROM users   
WHERE user_id = 2;

Получение названий всех фильмов
SELECT  name
FROM film;

Получение фильма с id = 1 
SELECT  name,
        description,
        reliaseDate,
        duration,
        mpa
FROM film
WHERE film_id = 1;
```
