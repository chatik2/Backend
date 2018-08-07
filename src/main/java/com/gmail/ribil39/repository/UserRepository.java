package com.gmail.ribil39.repository;

import com.gmail.ribil39.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
}

    /*1. По переменной в хедере:
    type:mobile - те же запросы, что и сейчас

    type:web - Алексей напишет

    2. uid - передаётся в хедере

    3  по post-запросу передача нового сообщений,
    изменение имени пользователя:
    text для передачи сообщения и name для изменения
    имени передаются в теле post-запроса

    3.1 передача нового сообщения по url /new_msg

    4
    передача по запросу new_user - нового uid для нового пользователя

    по запросу users - выдача не всей таблицы, а колонки id и name, где id
    (для подстановки имени в сообщения, а не uid авторизации)

    5
    передача не всех, а только добавившисях с предыдущего запроса сообщений/пользователей
    + пользователей у которых изменилось имя

    */