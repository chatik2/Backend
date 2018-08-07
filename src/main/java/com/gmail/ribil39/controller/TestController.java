package com.gmail.ribil39.controller;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class TestController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;


    @GetMapping(value = "/test")
    String test(){
        return "test";
    }

    @PostMapping("/savemessage")
    String saveMessage(
            @RequestParam(value = "uid", required = false) long userId,
            @RequestParam(value = "text", required = false) String text
    ){
        Message message = new Message();
        message.setText(text);
        message.setDate(new Date());
        message.setAuthor(userRepository.findById(userId));

        messageRepository.save(message);

        return "redirect:/";
    }
}
