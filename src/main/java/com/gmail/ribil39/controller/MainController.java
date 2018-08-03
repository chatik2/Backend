package com.gmail.ribil39.controller;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/")
    ResponseEntity<List<Message>> getMessages(
            @RequestParam(value = "new_user", required = false) String newUser,
            @RequestParam(value = "uid", required = false) Long userId,
            @RequestParam(value = "name", required = false) String userName
    ){
        Long uid;
        HttpHeaders responseHeaders = new HttpHeaders();

        if(newUser == "true"){
            User user = new User();
            uid = user.getId();
            userRepository.save(user);

            responseHeaders.set("uid", uid.toString());
        }

        List<Message> result = messageRepository.findAll();

        //uid
        return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
    }
}
