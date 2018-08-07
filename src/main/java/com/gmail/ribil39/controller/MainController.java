package com.gmail.ribil39.controller;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import com.gmail.ribil39.service.MessageDTO;
import com.gmail.ribil39.service.MessageService;
import com.gmail.ribil39.service.UserDTO;
import com.gmail.ribil39.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
public class MainController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/new_user")
    ResponseEntity<User> getNewUser(
            /*@RequestHeader(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type,
            @RequestParam(value = "name", required = false) String userName*/
    ) {
        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");*/

        // List<Message> result = messageRepository.findAll();
        List<User> userResult = userRepository.findAll();

        User user = new User();

        Random random = new Random();
        boolean uniqueName = false;
        while (!uniqueName) {
            String randomName = "anonim" + String.valueOf(random.nextInt(89999) + 10000);
            for (User u : userResult) {
                if (u.getName().equals(randomName)) {
                    uniqueName = false;
                } else {
                    uniqueName = true;
                }
            }
            user.setName(randomName);
        }

        boolean uniqueSecondId = false;
        while (!uniqueSecondId) {
            Integer randomSecondId = random.nextInt(89999) + 10000;
            for (User u : userResult) {
                if (u.getSecondId().equals(randomSecondId)) {
                    uniqueSecondId = false;
                } else {
                    uniqueSecondId = true;
                }
            }
            user.setSecondId(new Long(randomSecondId));
        }

        userRepository.save(user);
        //responseHeaders.set("number_of_msgs", Integer.toString(result.size()));
        return new ResponseEntity<>(user, /*responseHeaders, */HttpStatus.OK);
    }

    @PostMapping("/new_name")
    ResponseEntity<User> setUserName(
            @RequestHeader(value = "uid", required = false) long userId,
            //@RequestParam(value = "uid", required = false) long userId,
            @RequestHeader(value = "type", required = false) String type,
            @RequestBody String userName
    ) {
        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");*/

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(/*responseHeaders,*/ HttpStatus.OK);
        }

        User user = userRepository.findById(userId);
        user.setName(userName);
        userRepository.save(user);

        return new ResponseEntity<>(user, /*responseHeaders,*/ HttpStatus.OK);

    }

    @RequestMapping(value = "/users")
    ResponseEntity<List<UserDTO>> getUsers(
            @RequestHeader(value = "uid", required = false) Long userId,
            //@RequestParam(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");*/

        List<User> userResult = userRepository.findAll();
        /*boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(*//*responseHeaders,*//* HttpStatus.OK);
        }*/

        List<UserDTO> userDTOResult = userService.getDTOUsers();


        return new ResponseEntity<>(userDTOResult, /*responseHeaders,*/ HttpStatus.OK);
    }

    @RequestMapping(value = "/msgs")
    ResponseEntity<List<MessageDTO>> getMessages(
            @RequestHeader(value = "uid", required = false) Long userId,
            //@RequestParam(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        /*HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");*/

        //List<Message> messageResult = messageRepository.findAll();
        List<User> userResult = userRepository.findAll();
        /*boolean userExists = false;
        for (User u : userResult) {
            if (u.getId().equals(userId)) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(*//*responseHeaders,*//* HttpStatus.OK);
        }*/

        List<MessageDTO> messageDTOList = messageService.getDTOMessages();

        return new ResponseEntity<>(messageDTOList, /*responseHeaders,*/ HttpStatus.OK);
    }

    /*@RequestMapping("/new_msg")
    ResponseEntity<Message> saveMessage(
            //@RequestHeader(value = "uid", required = false) long userId,
           // @RequestHeader(value = "type", required = false) String type,
            @RequestParam(value = "text", required = false) String text
           ,@RequestParam(value = "uid", required = false) long userId
    ) {

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId() == userId) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        }
        Message message = new Message();
        message.setText(text);
        Date date = new Date();
        message.setDate(date);
        message.setAuthor(userRepository.findById(userId));

        messageRepository.save(message);

        //Message savedMessage = messageRepository.findByDate(date);
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }*/

    @RequestMapping("/new_msg")
    ResponseEntity<Message> saveMessage(
            @RequestHeader(value = "uid", required = false) long userId,
           // @RequestHeader(value = "type", required = false) String type,
            @RequestBody String text
           //,@RequestParam(value = "uid", required = false) long userId
    ) {

       /* HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");*/

        List<User> userResult = userRepository.findAll();
        boolean userExists = false;
        for (User u : userResult) {
            if (u.getId() == userId) {
                userExists = true;
            }
        }
        if (!userExists) {
            return new ResponseEntity<>(/*responseHeaders,*/ HttpStatus.OK);
        }
        Message message = new Message();
        message.setText(text);
        Date date = new Date();
        message.setDate(date);
        message.setAuthor(userRepository.findById(userId));

        messageRepository.save(message);

        //Message savedMessage = messageRepository.findByDate(date);
        return new ResponseEntity<>(message, /*responseHeaders,*/ HttpStatus.OK);
    }


}
