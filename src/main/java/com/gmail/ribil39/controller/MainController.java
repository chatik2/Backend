package com.gmail.ribil39.controller;

import com.gmail.ribil39.model.Message;
import com.gmail.ribil39.model.User;
import com.gmail.ribil39.repository.MessageRepository;
import com.gmail.ribil39.repository.UserRepository;
import com.gmail.ribil39.model.MessageDTO;
import com.gmail.ribil39.service.MessageService;
import com.gmail.ribil39.model.UserDTO;
import com.gmail.ribil39.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    public static final long HOUR = 3600 * 1000;

    @RequestMapping(value = "/new_user", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<User> getNewUser(
    ) {

        User user = userService.createNewUser();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/new_name", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<User> setUserName(
            @RequestHeader(value = "uid", required = false) long userId,
            @RequestBody String userName
    ) {

       boolean userExists = userService.ifUserExists(userId);
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userService.changeUserName(userId, userName);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @RequestMapping(value = "/users", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<List<UserDTO>> getUsers(
            @RequestHeader(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        boolean userExists = userService.ifUserExists(userId);
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long uid = userId;
        User user = userService.findUserById(uid);

        List<UserDTO> userDTOResult = userService.getDTOUsers();

        user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        userService.saveUser(user);

        return new ResponseEntity<>(userDTOResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/msgs", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<List<MessageDTO>> getMessages(
            @RequestHeader(value = "uid", required = false) Long userId,
            @RequestHeader(value = "type", required = false) String type
    ) {

        boolean userExists = userService.ifUserExists(userId);
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long uid = userId;
        User user = userService.findUserById(uid);

        List<MessageDTO> messageDTOList = messageService.getDTOMessages();

        user.setLastRequestDate(new Date(new Date().getTime() + 3 * HOUR));
        userService.saveUser(user);

        return new ResponseEntity<>(messageDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/new_msg", produces = {"application/json; charset=UTF-8"})
    ResponseEntity<Message> saveMessage(
            @RequestHeader(value = "uid", required = false) long userId,
            @RequestBody String text

    ) {

        boolean userExists = userService.ifUserExists(userId);
        if (!userExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Message message = messageService.createMessage(userId, text);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
