package com.gmail.ribil39.repository;

import com.gmail.ribil39.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    Message findByDate(Date date);
}
