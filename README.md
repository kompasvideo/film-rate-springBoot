# java-filmorate
![Схема БД](/Схема_БД.png)
```
Получение всех пользователей
SELECT login,
        name,
        email,
        birthday
FROM user;

Получение пользователя с id = 2
SELECT login,
        name,
        email,
        birthday
FROM user   
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
